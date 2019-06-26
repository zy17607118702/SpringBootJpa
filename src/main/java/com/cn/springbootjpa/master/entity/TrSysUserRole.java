package com.cn.springbootjpa.master.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cn.springbootjpa.base.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "tr_sys_user_role")
@EqualsAndHashCode(callSuper = true)
public class TrSysUserRole extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
	@Column(name = "tr_sys_user_role_id", unique = true, nullable = false, precision = 11, scale = 0)
	private Integer id;

	@Column(name="tm_sys_user_id",precision=11,scale=0, nullable = false)
	private Integer tmSysUserId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="tm_sys_user_id",insertable=false,updatable=false)
	private TmSysUser tmSysUser;

	@Column(name="tm_sys_role_id",precision=11,scale=0, nullable = false)
	private Integer  tmSysRoleId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="tm_sys_role_id",insertable=false,updatable=false)
	private TmSysRole tmSysRole;
	
	@Column(name="mark_status",length=1,nullable=false)
	private Boolean markStatus;

}
