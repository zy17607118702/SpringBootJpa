package com.cn.springbootjpa.mq.base;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.jms.BytesMessage;
import javax.jms.TextMessage;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.springbootjpa.base.common.code.CodeTypeConstants;
import com.cn.springbootjpa.base.exception.ApplicationException;
import com.cn.springbootjpa.mq.message.MessageHeader;
import com.cn.springbootjpa.mq.msgbo.TiIfsConfigBo;
import com.cn.springbootjpa.mq.msgbo.TiIfsMqsInMsgBo;
import com.cn.springbootjpa.mq.msgentity.TiIfsConfig;
import com.cn.springbootjpa.mq.msgentity.TiIfsMqsInMsg;
import com.cn.springbootjpa.util.StringUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.tools.json.JSONUtil;
import com.saicmotor.imes.messageservice.MessageCommonUtil;
import com.saicmotor.imes.messageservice.constants.MessageConstant;
import com.saicmotor.imes.messageservice.exception.MessageStoreException;
import com.saicmotor.imes.messageservice.util.IMessageStore;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 *mq监听基础类 监听mq信息并且将文件存入本地
 * @author zhangyang
 *
 * @param <T>
 */
@Slf4j
public abstract class BaseAmqpMessageListener<T> {

    @Autowired
    private TiIfsConfigBo ifsConfigBo;

    @Autowired
    private TiIfsMqsInMsgBo tiIfsMqsInMsgBo;
    /**
     * IMessageStore 对消息内容进行存储
     */
    @Autowired
    private IMessageStore messageStore;

    @Autowired
    public MessageCommonUtil messageCommonUtil;

    private void createMessageLog(TiIfsConfig tiIfsConfig, MessageHeader headers, Message message, boolean hasError,
            Object result) {
        // 把收到的消息发送到接收日志表中
        @NonNull
        String messageType = headers.getMessageType();
        try {
            TiIfsMqsInMsg tiIfsMqsInMsg = new TiIfsMqsInMsg();
            tiIfsMqsInMsg.setCorrelationId(headers.getCorrelationId());
            tiIfsMqsInMsg.setMessageFormat(headers.getMessageFormat());
            tiIfsMqsInMsg.setMessageId(headers.getMessageId());
            tiIfsMqsInMsg.setMessageType(messageType);
            Date currentTime = Calendar.getInstance().getTime();
            tiIfsMqsInMsg.setReceiveTime(currentTime);
            String requestID = headers.getSequenceId();
            if (null != requestID) {
                // 合法性检查 如果采用dummy接口或者msg.message sequence” & 接口配置表中的
                // “Last_Request_ID消息序号”不连续 报警，更新TM_SYS_ALERT
                boolean bool = messageCommonUtil.checkSeqIDAndLastReqID(requestID, tiIfsConfig.getLastRequestId());
                if (!bool) {
                    // 写入相应的Alert表 MessageType没有找到
                    messageCommonUtil.publishAlert(CodeTypeConstants.SYS_ALERT_APPLICATION_INTERFACE,
                            CodeTypeConstants.SYS_ALERT_MODULE_MQS, messageType, "Check Sequence Error. LastReq is "
                                    + tiIfsConfig.getLastRequestId() + " while SequenceID is" + requestID);
                }

                tiIfsMqsInMsg.setSequenceId(Long.parseLong(requestID));
                tiIfsConfig.setLastRequestId(Long.parseLong(requestID));
            }
            tiIfsMqsInMsg.setOperatorId(headers.getOperatorId());
            tiIfsMqsInMsg.setOperatorName(headers.getOperatorName());
            tiIfsMqsInMsg.setRemoteMacAddr(headers.getRemoteMacAddr());
            tiIfsMqsInMsg.setTiIfsConfig(tiIfsConfig);
            tiIfsMqsInMsg.setReceiveOk(!hasError);
            String returnMsg = null;
            if (result != null && result instanceof String) {
                returnMsg = (String) result;
            } else if (result != null) {
                returnMsg = JSONUtil.getJsonHandler().toJson(result);
            }
            tiIfsMqsInMsg.setReturnMsg(StringUtil.limitLength(returnMsg, 512));
            // 根据收到的Message更新ti_ifs_config
            tiIfsConfig.setLastRunningTime(currentTime);
            ifsConfigBo.update(tiIfsConfig);
            tiIfsMqsInMsg = tiIfsMqsInMsgBo.create(tiIfsMqsInMsg);
//            tiIfsMqsInMsgBo.getDao().flush();
            // 保存收到的消息到本地文件
            // 存储接收到的消息到文件
            String saveFileName = "";
            try {
                if (message instanceof TextMessage) {
                    saveFileName = messageStore.ReceiveMessage2File(((TextMessage) message).getText(), messageType,
                            tiIfsMqsInMsg.getId());
                } else if (message instanceof BytesMessage) {
                    Long len = ((BytesMessage) message).getBodyLength();
                    byte[] bytes = new byte[len.intValue()];
                    ((BytesMessage) message).readBytes(bytes);
                    // System.out.println(str);
                }
                saveFileName = messageStore.ReceiveMessage2File(message.getBody(), messageType, tiIfsMqsInMsg.getId());

                saveFileName = MessageConstant.RECEIVE_MSG_STORE_URL + "/" + saveFileName;
            } catch (MessageStoreException e) {
                log.error("Save Received Message to File Failed. " + e.getMessage()
                        + " ID in Table TI_IFS_MQS_IN_MSG : " + tiIfsMqsInMsg.getId());
            }
            tiIfsMqsInMsg.setReceiveMsg(saveFileName);
            tiIfsMqsInMsgBo.update(tiIfsMqsInMsg);
        } catch (Exception e) {
            throw new ApplicationException("", null, e);
        }

    }

    /**
     * 处理接收到得消息内容，并且返回需要回复的消息内容，回复queue请设置message
     * header的replyTo，或者在RabbitListener地方添加@SendTo标注
     * 
     * @param headers
     * @param object
     * @return
     */
    protected abstract Object onInternalMessage(MessageHeader headers, T object);

    /**
     * 消息响应方法，子类应当重写本方法，并添加@RabbitListener等标注，并方法体重调用super.onMessage，消息公用逻辑由父类同意处理，子类通过实现onInternalMessage进行自己消息处理.
     * 
     * @param channel
     * @param rawMessage
     * @param body
     */
    public Object onMessage(Channel channel, Message rawMessage, T body) {
        MessageProperties messageProperties = rawMessage.getMessageProperties();
        long deliveryTag = messageProperties.getDeliveryTag();
        Map<String, Object> hds = messageProperties.getHeaders();
        String messageType = (String) hds.get(MessageHeader.MESSAGE_TYPE);

        TiIfsConfig tiIfsConfig = ifsConfigBo.findByInterfaceId(messageType);
        if (tiIfsConfig == null) {
            log.warn("Ifs config data {} can not be found! ", messageType);
        }
        MessageHeader headers = MessageHeader.from(hds);
        boolean hasError = false;
        Object result = null;
        try {
            result = onInternalMessage(headers, body);
            // 确认信息处理成功
            try {
                channel.basicAck(deliveryTag, false);
            } catch (Exception e) {
                e.printStackTrace();
                Thread.sleep(1000);
                // try again
                try {
                    channel.basicAck(deliveryTag, false);
                } catch (Exception e1) {
                    e1.printStackTrace();
                    log.error("### Message listener basic ack still error tried twice.");
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("### message handle exception.", e);
            try {
                // 消息处理异常，要求mq重新发送消息
                channel.basicNack(deliveryTag, false, true);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
            if (tiIfsConfig != null && tiIfsConfig.getLogEnabled()) {
                createMessageLog(tiIfsConfig, headers, rawMessage, hasError, result);
            }
        }
        return result;
    }
}
