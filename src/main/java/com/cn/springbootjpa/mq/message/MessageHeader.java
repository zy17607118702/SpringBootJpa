package com.cn.springbootjpa.mq.message;

import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageHeader {
    public final static String SYSTEM_ID = "SystemId";
    public final static String MESSAGE_ID = "msgId";
    public final static String MESSAGE_TYPE = "msgType";
    public final static String CORRELATION_ID = "CorrelationId";
    public static final String SRC_SYSTEM = "srcSystem";
    public static final String DES_SYSTEM = "desSystem";
    public final static String SEQUENCE_ID = "SequenceId";
    public final static String OPERATOR_ID = "OperatorId"; // optional 可选
    public final static String OPERATOR_NAME = "OperatorName"; // optional 可选
    public final static String RMOTE_MAC_ADDRESS = "RemoteMacAddr";
    public final static String MESSAGE_FORMAT = "MessageFormat";
    public final static String REPLY_TO = "ReplyTo";

    public final static String MESSAGE_FORMAT_XML = "xml";
    public final static String MESSAGE_FORMAT_JSON = "json";
    public final static String MESSAGE_FORMAT_CSV = "csv";

    public static MessageHeader from(Map<String, Object> hds) {
        MessageHeader headers = new MessageHeader();
        headers.setCorrelationId((String) hds.get(CORRELATION_ID));
        headers.setDesSystem((String) hds.get(DES_SYSTEM));
        headers.setMessageFormat((String) hds.get(MESSAGE_FORMAT));
        headers.setMessageId((String) hds.get(MESSAGE_ID));
        headers.setMessageType((String) hds.get(MESSAGE_TYPE));
        headers.setOperatorId((String) hds.get(OPERATOR_ID));
        headers.setOperatorName((String) hds.get(OPERATOR_NAME));
        headers.setRemoteMacAddr((String) hds.get(RMOTE_MAC_ADDRESS));
        headers.setReplyTo((String) hds.get(REPLY_TO));
        headers.setSequenceId((String) hds.get(SEQUENCE_ID));
        headers.setSrcSystem((String) hds.get(SRC_SYSTEM));
        return headers;
    }

    public static MessageHeader withType(String msgType) {
        return new MessageHeader(msgType);
    }

    private String messageType; // 消息类型，
    private String srcSystem; // 源系统
    private String desSystem; // 目标系统
    private String operatorId;// 操作员id
    private String sequenceId;// 操作员id
    private String operatorName; // 操作员用户名
    private String remoteMacAddr; // 发送远程ip
    private String messageFormat; // 消息格式，如json, xml, csv等，由message header的content type决定
    private String replyTo; // 回复queue名称
    private String messageId;
    private String correlationId;

    public MessageHeader(String msgType) {
        this.messageType = msgType;
    }

    public MessageHeader(String msgType, String srcSystem, String desSystem) {
        this.messageType = msgType;
        this.srcSystem = srcSystem;
        this.desSystem = desSystem;
    }

    public MessageHeader format(String format) {
        this.setMessageFormat(format);
        return this;
    }

    public void toMap(Map<String, Object> hds) {
        hds.put(CORRELATION_ID, this.getCorrelationId());
        hds.put(DES_SYSTEM, this.getDesSystem());
        hds.put(MESSAGE_ID, this.getMessageId());
        hds.put(MESSAGE_TYPE, this.getMessageType());
        hds.put(OPERATOR_ID, this.getOperatorId());
        hds.put(OPERATOR_NAME, this.getOperatorName());
        hds.put(RMOTE_MAC_ADDRESS, this.getRemoteMacAddr());
        hds.put(REPLY_TO, this.getReplyTo());
        hds.put(SEQUENCE_ID, this.getSequenceId());
        hds.put(SRC_SYSTEM, this.getSrcSystem());
    }

    public MessageHeader(String msgType, String srcSystem, String desSystem, String messageId) {
        this.messageType = msgType;
        this.srcSystem = srcSystem;
        this.desSystem = desSystem;
        this.messageId = messageId;
    }
}
