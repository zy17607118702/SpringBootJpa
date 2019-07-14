package com.cn.springbootjpa.system.entity;

// Generated 2008-6-18 15:50:09 by Hibernate Tools 3.2.0.CR1

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cn.springbootjpa.base.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "ti_ifs_mqs_out_msg")
@org.hibernate.annotations.Table(appliesTo = "ti_ifs_mqs_out_msg", comment = "MQ发送信息表")
@EqualsAndHashCode(callSuper = true)
public class TiIfsMqsOutMsg extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6655214625053645998L;

	public static final String PO_TIIFSMQSOUTMSG = "TiIfsMqsOutMsg";

	public static final String FIELD_ID = "id";

	public static final String FIELD_TIIFSCONFIGID = "tiIfsConfigId";

	public static final String FIELD_TIIFSCONFIG = "tiIfsConfig";

	public static final String FIELD_MESSAGETYPE = "messageType";

	public static final String FIELD_MESSAGEFORMAT = "messageFormat";

	public static final String FIELD_SEQUENCEID = "sequenceId";

	public static final String FIELD_SENDMSG = "sendMsg";

	public static final String FIELD_SENDTIME = "sendTime";

	public static final String FIELD_SENDOK = "sendOk";

	public static final String FIELD_RETURNMSG = "returnMsg";

	public static final String FIELD_MESSAGEID = "messageId";

	public static final String FIELD_CORRELATIONID = "correlationId";

	public static final String FIELD_RESENDCOUNTER = "resendCounter";

	public static final String FIELD_NEEDRESEND = "needResend";

	public static final String FIELD_OPERATORID = "operatorId";

	public static final String FIELD_OPERATORNAME = "operatorName";

	public static final String FIELD_REMOTEMACADDR = "remoteMacAddr";

	public static final String FIELD_ALERT_SENT = "alertSent";

	public static final String FIELD_MSGHEADER = "msgHeader";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TI_IFS_MQS_OUT_MSG_ID", columnDefinition = "bigint(11) comment 'id'", unique = true, nullable = false)
	private Integer id;

	@Column(name = "TI_IFS_CONFIG_ID", columnDefinition = "bigint(11) comment '接口配置id'", nullable = false)
	private Integer tiIfsConfigId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TI_IFS_CONFIG_ID", insertable = false, updatable = false)
	private TiIfsConfig tiIfsConfig;

	@Column(name = "MESSAGE_TYPE", length = 30)
	private String messageType;

	@Column(name = "MESSAGE_FORMAT", length = 2)
	private String messageFormat;

	@Column(name = "SEQUENCE_ID", precision = 10, scale = 0)
	private Integer sequenceId;

	@Column(name = "SEND_MSG", length = 256)
	private String sendMsg;

	@Column(name = "SEND_TIME", length = 11)
	private Date sendTime;

	@Column(name = "SEND_OK", length = 1)
	private Boolean sendOk;

	@Column(name = "RETURN_MSG", length = 128)
	private String returnMsg;

	@Column(name = "MESSAGE_ID", length = 60)
	private String messageId;

	@Column(name = "CORRELATION_ID", length = 60)
	private String correlationId;

	@Column(name = "RESEND_COUNTER", precision = 10, scale = 0)
	private Integer resendCounter;

	@Column(name = "NEED_RESEND", length = 1)
	private Boolean needResend;

	@Column(name = "OPERATOR_ID", length = 20)
	private String operatorId;

	@Column(name = "OPERATOR_NAME", length = 30)
	private String operatorName;

	@Column(name = "REMOTE_MAC_ADDR", length = 40)
	private String remoteMacAddr;

	@Column(name = "ALERT_SENT", length = 1)
	private Character alertSent;

	@Column(name = "MSG_HEADER", length = 50)
	private String msgHeader;

	public TiIfsMqsOutMsg() {
	}

	public TiIfsMqsOutMsg(TiIfsConfig tiIfsConfig, String messageType, String messageFormat, Integer sequenceId,
			String sendMsg, Date sendTime, Boolean sendOk, String returnMsg, String messageId, String correlationId,
			Integer resendCounter, Boolean needResend, String operatorId, String operatorName, String remoteMacAddr,
			Character alertSent) {
		this.tiIfsConfig = tiIfsConfig;
		this.messageType = messageType;
		this.messageFormat = messageFormat;
		this.sequenceId = sequenceId;
		this.sendMsg = sendMsg;
		this.sendTime = sendTime;
		this.sendOk = sendOk;
		this.returnMsg = returnMsg;
		this.messageId = messageId;
		this.correlationId = correlationId;
		this.resendCounter = resendCounter;
		this.needResend = needResend;
		this.operatorId = operatorId;
		this.operatorName = operatorName;
		this.remoteMacAddr = remoteMacAddr;
		this.alertSent = alertSent;
	}

}
