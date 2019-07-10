package com.cn.springbootjpa.mq.base;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.springbootjpa.mq.exception.MessageFormatException;
import com.cn.springbootjpa.mq.message.MessageHeader;
import com.cn.springbootjpa.util.StringUtil;
import com.rabbitmq.tools.json.JSONUtil;
import com.saicmotor.framework.util.SystemParameterUtil;
import com.saicmotor.imes.bo.TiIfsConfigBO;
import com.saicmotor.imes.bo.TiIfsMqsOutMsgBO;
import com.saicmotor.imes.messageservice.constants.MessageConstant;
import com.saicmotor.imes.messageservice.constants.MessageTypeConstant;
import com.saicmotor.imes.messageservice.exception.MessageStoreException;
import com.saicmotor.imes.messageservice.util.IMessageStore;
import com.saicmotor.imes.model.TiIfsConfig;
import com.saicmotor.imes.model.TiIfsMqsOutMsg;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Primary
//@Component
public class BaseAmqpMessageSender implements AmqpMessageSender {

    private AmqpTemplate amqpTemplate;

    @Autowired
    private TiIfsConfigBO ifsConfigBo;

    @Autowired
    private TiIfsMqsOutMsgBO tiIfsMqsOutMsgBo;

    /**
     * IMessageStore 对消息内容进行存储
     */
    @Autowired
    private IMessageStore messageStore;

    public BaseAmqpMessageSender(AmqpTemplate amqpTemplate) {
    	this.amqpTemplate = amqpTemplate;
    }

    private void createMessageLog(TiIfsConfig tiIfsConfig, String messageType, String messageFormat, String message,
            Long requestId, String messageId, String correlationId, String operatorId, String operatorName,
            boolean error) {
        // 记录到表TI_IFS_MQS_OUT_MSG中
        TiIfsMqsOutMsg tiIfsMqsOutMsg = new TiIfsMqsOutMsg();
        tiIfsMqsOutMsg.setMessageFormat(messageFormat);
        tiIfsMqsOutMsg.setMessageType(messageType); // 接口类型，用于区分接口信息
        tiIfsMqsOutMsg.setMsgHeader(messageType);// 消息头类型，用于区分多个设备系统（与下游系统约定的唯一信息）
        tiIfsMqsOutMsg.setSendMsg(null); // 文件名以
        // messageType_ID方式命名，无需单独命名
        if (error) {
            tiIfsMqsOutMsg.setSendOk(false);
            tiIfsMqsOutMsg.setNeedResend(true);
        } else {
            tiIfsMqsOutMsg.setSendOk(true);
            tiIfsMqsOutMsg.setNeedResend(false);
        }
        Date currentTime = Calendar.getInstance().getTime();
        tiIfsMqsOutMsg.setSequenceId(requestId);
        tiIfsMqsOutMsg.setSendTime(currentTime);
        tiIfsMqsOutMsg.setTiIfsConfig(tiIfsConfig);
        tiIfsMqsOutMsg.setMessageId(messageId);
        tiIfsMqsOutMsg.setCorrelationId(correlationId);
        tiIfsMqsOutMsg.setAlertSent(MessageConstant.ALERT_SENT_NONE);
        tiIfsMqsOutMsg.setOperatorId(operatorId);
        tiIfsMqsOutMsg.setOperatorName(operatorName);
        // 并更新表TI_IFS_CONFIG中的Last_Request_ID和Last_Running_Time
        tiIfsMqsOutMsgBo.create(tiIfsMqsOutMsg);
        if (tiIfsConfig != null) {
            tiIfsConfig.setLastRequestId(requestId);
            tiIfsConfig.setLastRunningTime(currentTime);
            ifsConfigBo.update(tiIfsConfig);
        }
        String saveFileName = "";
        // 存储文件
        try {
            saveFileName = messageStore.SendMessage2File(message, messageType, tiIfsMqsOutMsg.getId());
            saveFileName = MessageConstant.SEND_MSG_STORE_URL + File.separator + saveFileName;
            tiIfsMqsOutMsg.setSendMsg(saveFileName);
            tiIfsMqsOutMsgBo.update(tiIfsMqsOutMsg);
        } catch (MessageStoreException e) {
            e.printStackTrace();
            System.out.println("Save Send Message to File Failed. " + e.getMessage()
                    + " ID in Table TI_IFS_MQS_OUT_MSG : " + tiIfsMqsOutMsg.getId());
        }
    }
    
    private void createMessageLogForTopic(TiIfsConfig tiIfsConfig, String messageType, String messageFormat, String message,
            Long requestId, String messageId, String correlationId, String operatorId, String operatorName,
            boolean error, String routingKey) {
        // 记录到表TI_IFS_MQS_OUT_MSG中
        TiIfsMqsOutMsg tiIfsMqsOutMsg = new TiIfsMqsOutMsg();
        tiIfsMqsOutMsg.setMessageFormat(messageFormat);
        tiIfsMqsOutMsg.setMessageType(messageType); // 接口类型，用于区分接口信息
        tiIfsMqsOutMsg.setMsgHeader(routingKey);// 消息头类型，用于区分多个设备系统（与下游系统约定的唯一信息）
        tiIfsMqsOutMsg.setSendMsg(null); // 文件名以
        // messageType_ID方式命名，无需单独命名
        if (error) {
            tiIfsMqsOutMsg.setSendOk(false);
            tiIfsMqsOutMsg.setNeedResend(true);
        } else {
            tiIfsMqsOutMsg.setSendOk(true);
            tiIfsMqsOutMsg.setNeedResend(false);
        }
        Date currentTime = Calendar.getInstance().getTime();
        tiIfsMqsOutMsg.setSequenceId(requestId);
        tiIfsMqsOutMsg.setSendTime(currentTime);
        tiIfsMqsOutMsg.setTiIfsConfig(tiIfsConfig);
        tiIfsMqsOutMsg.setMessageId(messageId);
        tiIfsMqsOutMsg.setCorrelationId(correlationId);
        tiIfsMqsOutMsg.setAlertSent(MessageConstant.ALERT_SENT_NONE);
        tiIfsMqsOutMsg.setOperatorId(operatorId);
        tiIfsMqsOutMsg.setOperatorName(operatorName);
        // 并更新表TI_IFS_CONFIG中的Last_Request_ID和Last_Running_Time
        tiIfsMqsOutMsgBo.create(tiIfsMqsOutMsg);
        if (tiIfsConfig != null) {
            tiIfsConfig.setLastRequestId(requestId);
            tiIfsConfig.setLastRunningTime(currentTime);
            ifsConfigBo.update(tiIfsConfig);
        }
        String saveFileName = "";
        // 存储文件
        try {
            saveFileName = messageStore.SendMessage2File(message, messageType, tiIfsMqsOutMsg.getId());
            saveFileName = MessageConstant.SEND_MSG_STORE_URL + File.separator + saveFileName;
            tiIfsMqsOutMsg.setSendMsg(saveFileName);
            tiIfsMqsOutMsgBo.update(tiIfsMqsOutMsg);
        } catch (MessageStoreException e) {
            e.printStackTrace();
            System.out.println("Save Send Message to File Failed. " + e.getMessage()
                    + " ID in Table TI_IFS_MQS_OUT_MSG : " + tiIfsMqsOutMsg.getId());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.saicmotor.imes.mq.AmqpMessageSender#send(java.lang.String,
     * com.saicmotor.imes.mq.MessageHeader, T)
     */
    @Override
    public <T> void send(final String routingKey, final MessageHeader headers, T message)
            throws MessageFormatException {
        this.send("", routingKey, headers, message);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.saicmotor.imes.mq.AmqpMessageSender#send(java.lang.String,
     * java.lang.String, com.saicmotor.imes.mq.MessageHeader, T)
     */
    @Override
    public <T> void send(final String exchange, final String routingKey, final MessageHeader headers, T message)
            throws MessageFormatException {
        String messageType = headers.getMessageType();
        TiIfsConfig tiIfsConfig = ifsConfigBo.findByInterfaceId(messageType);
        if (tiIfsConfig == null) {
            log.warn("Ifs config data {} can not be found! ", messageType);
            throw new MessageFormatException("Parameters can't be NULL.");
        }
        String outQueueName = null;
        if (tiIfsConfig != null) {
            outQueueName = tiIfsConfig.getOutQueue();
            if (outQueueName != null && outQueueName.startsWith(MessageConstant.QUEUENAME_PREFIX)) {
                outQueueName.substring(outQueueName.indexOf(MessageConstant.QUEUENAME_PREFIX) + 1);
            }
        }
        // boolean bi_direction = (tiIfsConfig.getInterfaceDirection()
        // .equalsIgnoreCase(MessageConstant.DIRECTION_MSG_SEND_RECEIVE));

        // MQQueue responseQueue = null;
        // String replyTo = null;
        // if (bi_direction) {
        // replyTo = tiIfsConfig.getInQueue();
        // if (replyTo.startsWith(MessageConstant.QUEUENAME_PREFIX)) {
        // replyTo.substring(replyTo.indexOf(MessageConstant.QUEUENAME_PREFIX) + 1);
        // }
        // }
        // headers.setReplyTo(replyTo);
        Long sequenceId = Long.parseLong(SystemParameterUtil.getInstance().getSysParameter(messageType, "", 0, 1));
        headers.setSequenceId("" + sequenceId);
        // 发送消息
        boolean hasError = false;
        String messageId = headers.getMessageId();

        if (StringUtil.isNullOrBlank(messageId)) {
            // 没有messageid情况下，创建messageid
            messageId = UUID.randomUUID().toString();
            headers.setMessageId(messageId);
        }
        try {
            amqpTemplate.convertAndSend(exchange, StringUtil.isNullOrBlank(outQueueName) ? routingKey : outQueueName,
                    message, m -> {
                        MessageProperties mp = m.getMessageProperties();
                        Map<String, Object> hds = mp.getHeaders();
                        headers.toMap(hds); // copy to message header map;
                        if (StringUtils.isAnyBlank(mp.getMessageId())) {
                            mp.setMessageId(headers.getMessageId());
                        } else {
                            headers.setMessageId(mp.getMessageId());
                        }
                        String srcSystem = headers.getSrcSystem();
                        String desSystem = headers.getDesSystem();
                        if (tiIfsConfig != null && !StringUtil.isNullOrBlank(tiIfsConfig.getSendSystem())) {
                            srcSystem = tiIfsConfig.getSendSystem();
                        }
                        if (tiIfsConfig != null && !StringUtil.isNullOrBlank(tiIfsConfig.getReceiveSystem())) {
                            desSystem = tiIfsConfig.getReceiveSystem();
                        }
                        // reset content type with message format
                        String format = (String) mp.getHeaders().get(MessageHeader.MESSAGE_FORMAT);
                        if (!StringUtils.isAnyBlank(format)) {
                            if (format.toLowerCase().contains(MessageHeader.MESSAGE_FORMAT_XML)) {
                                mp.setContentType(MessageProperties.CONTENT_TYPE_XML);
                            } else if (format.toLowerCase().contains(MessageHeader.MESSAGE_FORMAT_JSON)) {
                                mp.setContentType(MessageProperties.CONTENT_TYPE_JSON);
                            } else {
                                mp.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
                            }
                        }
                        mp.setHeader(MessageHeader.SRC_SYSTEM, srcSystem);
                        mp.setHeader(MessageHeader.DES_SYSTEM, desSystem);
                        return m;
                    });
        } catch (Exception e) {
            hasError = true;
            e.printStackTrace();
            log.error("Error while sending amqp message " + messageType, e);
        } finally {
            if (tiIfsConfig != null) {
                // 有配置情况下才记录
                String operatorId = headers.getOperatorId();
                String operatorName = headers.getOperatorName();
                messageId = headers.getMessageId();
                String correlationId = headers.getCorrelationId();
                if (messageType != null && MessageTypeConstant.IMES_PLATE_VHC_NAMEPLATE.equals(messageType)) {
               	 createMessageLogForTopic(tiIfsConfig, messageType, headers.getMessageFormat(),
                         message.toString(), sequenceId, messageId, correlationId, operatorId,
                         operatorName, hasError, routingKey);
                } else {
                	 createMessageLog(tiIfsConfig, messageType, headers.getMessageFormat(),
                             JSONUtil.getJsonHandler().toJson(message), sequenceId, messageId, correlationId, operatorId,
                             operatorName, hasError);
                }
               
            }
        }
    }

    public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }
}
