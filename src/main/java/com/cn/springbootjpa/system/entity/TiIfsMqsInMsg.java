package com.cn.springbootjpa.system.entity;

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
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cn.springbootjpa.base.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "ti_ifs_mqs_in_msg")
@org.hibernate.annotations.Table(appliesTo = "ti_ifs_mqs_in_msg", comment = "MQ接收信息表")
@EqualsAndHashCode(callSuper = true)
public class TiIfsMqsInMsg extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3058663124745765928L;

	public static final String PO_TIIFSMQSINMSG = "TiIfsMqsInMsg";

	public static final String FIELD_TIIFSCONFIG = "tiIfsConfig";

	public static final String FIELD_ID = "id";

	public static final String FIELD_TIIFSCONFIGID = "tiIfsConfigId";

	public static final String FIELD_MESSAGETYPE = "messageType";

	public static final String FIELD_MESSAGEFORMAT = "messageFormat";

	public static final String FIELD_SEQUENCEID = "sequenceId";

	public static final String FIELD_RECEIVEMSG = "receiveMsg";

	public static final String FIELD_RECEIVETIME = "receiveTime";

	public static final String FIELD_RECEIVEOK = "receiveOk";

	public static final String FIELD_RETURNMSG = "returnMsg";

	public static final String FIELD_MESSAGEID = "messageId";

	public static final String FIELD_CORRELATIONID = "correlationId";

	public static final String FIELD_OPERATORID = "operatorId";

	public static final String FIELD_OPERATORNAME = "operatorName";

	public static final String FIELD_REMOTEMACADDR = "remoteMacAddr";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TI_IFS_MQS_IN_MSG_ID", columnDefinition = "bigint(11) comment 'id'", unique = true, nullable = false)
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

	@Column(name = "RECEIVE_MSG", length = 256)
	private String receiveMsg;

	@Column(name = "RECEIVE_TIME", length = 11)
	private Date receiveTime;

	@Column(name = "RECEIVE_OK", length = 1)
	private Boolean receiveOk;

	@Column(name = "RETURN_MSG", length = 512)
	private String returnMsg;

	@Column(name = "MESSAGE_ID", length = 60)
	private String messageId;

	@Column(name = "CORRELATION_ID", length = 60)
	private String correlationId;

	@Column(name = "OPERATOR_ID", length = 20)
	private String operatorId;

	@Column(name = "OPERATOR_NAME", length = 30)
	private String operatorName;

	@Column(name = "REMOTE_MAC_ADDR", length = 40)
	private String remoteMacAddr;

	public TiIfsMqsInMsg(TiIfsConfig tiIfsConfig, Integer id) {
		this.tiIfsConfig = tiIfsConfig;
		this.id = id;
	}

	public TiIfsMqsInMsg(TiIfsConfig tiIfsConfig, Integer id, String messageType, String messageFormat,
			Integer sequenceId, String receiveMsg, Date receiveTime, Boolean receiveOk, String returnMsg,
			String messageId, String correlationId, String operatorId, String operatorName, String remoteMacAddr) {
		this.tiIfsConfig = tiIfsConfig;
		this.id = id;
		this.messageType = messageType;
		this.messageFormat = messageFormat;
		this.sequenceId = sequenceId;
		this.receiveMsg = receiveMsg;
		this.receiveTime = receiveTime;
		this.receiveOk = receiveOk;
		this.returnMsg = returnMsg;
		this.messageId = messageId;
		this.correlationId = correlationId;
		this.operatorId = operatorId;
		this.operatorName = operatorName;
		this.remoteMacAddr = remoteMacAddr;
	}

}
