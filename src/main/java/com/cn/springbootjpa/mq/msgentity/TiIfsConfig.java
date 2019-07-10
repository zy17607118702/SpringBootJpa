package com.cn.springbootjpa.mq.msgentity;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cn.springbootjpa.base.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author zhangyang
 *
 */
@Data
@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "TI_IFS_CONFIG", uniqueConstraints = @UniqueConstraint(columnNames = "INTERFACE_ID"))
@org.hibernate.annotations.Table(appliesTo = "TI_IFS_CONFIG", comment = "接口信息表")
@EqualsAndHashCode(callSuper = true)
public class TiIfsConfig extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String PO_TIIFSCONFIG = "TiIfsConfig";

	public static final String FIELD_ID = "id";

	public static final String FIELD_INTERFACEID = "interfaceId";

	public static final String FIELD_INTERFACEDESC = "interfaceDesc";

	public static final String FIELD_INTERFACEDIRECTION = "interfaceDirection";

	public static final String FIELD_INTERFACECOMM = "interfaceComm";

	public static final String FIELD_SENDSYSTEM = "sendSystem";

	public static final String FIELD_RECEIVESYSTEM = "receiveSystem";

	public static final String FIELD_OUTQUEUEMANAGER = "outQueueManager";

	public static final String FIELD_OUTQUEUE = "outQueue";

	public static final String FIELD_INQUEUEMANAGER = "inQueueManager";

	public static final String FIELD_INQUEUE = "inQueue";

	public static final String FIELD_LASTREQUESTID = "lastRequestId";

	public static final String FIELD_LASTRUNNINGTIME = "lastRunningTime";

	public static final String FIELD_RECEIVECLASS = "receiveClass";

	public static final String FIELD_TIIFSMQSOUTMSGS = "tiIfsMqsOutMsgs";

	public static final String FIELD_TIIFSMQSINMSGS = "tiIfsMqsInMsgs";

	public static final String FIELD_LOGENABLED = "logEnabled";

	public static final String FIELD_RESENDENABLED = "resendEnabled";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TI_IFS_CONFIG_ID", columnDefinition = "bigint(11) comment 'id'", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "INTERFACE_ID", unique = true, nullable = false)
	private String interfaceId;

	@Column(name = "INTERFACE_DESC", columnDefinition = "varchar(256) comment '接口描述'")
	private String interfaceDesc;

	@Column(name = "INTERFACE_DIRECTION", columnDefinition = "varchar(16) comment '接口接受类别'")
	private String interfaceDirection;
	
	@Column(name = "INTERFACE_COMM", length = 16)
	private String interfaceComm;

	@Column(name = "SEND_SYSTEM", length = 64)
	private String sendSystem;

	@Column(name = "RECEIVE_SYSTEM", length = 64)
	private String receiveSystem;

	@Column(name = "OUT_QUEUE_MANAGER", length = 128)
	private String outQueueManager;

	@Column(name = "OUT_QUEUE", length = 128)
	private String outQueue;

	@Column(name = "IN_QUEUE_MANAGER", length = 128)
	private String inQueueManager;

	@Column(name = "IN_QUEUE", length = 128)
	private String inQueue;

	@Column(name = "LAST_REQUEST_ID", precision = 10, scale = 0)
	private Integer lastRequestId;

	@Column(name = "LAST_RUNNING_TIME", length = 11)
	private Date lastRunningTime;

	@Column(name = "RECEIVE_CLASS", length = 256)
	private String receiveClass;

	@Column(name = "LOG_ENABLED", length = 1)
	private Boolean logEnabled;

	@Column(name = "RESEND_ENABLED", length = 1)
	private Boolean resendEnabled;
	
	@Column(name = "SOCKET_TIMEOUT", length = 20)
	private String socketTimeOut;
}
