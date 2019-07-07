package com.cn.springbootjpa.base.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.cn.springbootjpa.base.common.AuditingEntityListener;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass //该类不会映射到数据库中 但是该类的子类一定会映射到数据库中
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)//字段监听方法 自动填充值
public abstract class BaseEntity extends Entity  implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5152740859827126084L;
	
	public static final String FIELD_UPDATETIME="updateTime";
	
	public static final String FIELD_UPDATEBY="updateBy";

	@Column(name="create_time")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
	private Date createTime;
	
	@Column(name="create_by",length=50)
    private String createBy;
	
	@Column(name="update_time")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    private Date updateTime;
	
	@Column(name="update_by",length=50)
    private String updateBy;
    
	@Column(name = "mark_status", columnDefinition = "bit(1) comment '启用'")
	private Boolean markStatus;


   
}
