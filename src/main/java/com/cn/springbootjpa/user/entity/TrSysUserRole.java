package com.cn.springbootjpa.user.entity;

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

	public static final String TAB_TRSYSUSERROLE = "tr_sys_user_role";
	public static final String FIELD_ID = "id";
	public static final String FIELD_TMSYSUSERID = "tmSysUserId";
	public static final String FIELD_TMSYSUSER = "tmSysUser";
	public static final String FIELD_TMSYSROLEID = "tmSysRoleId";
	public static final String FIELD_TMSYSROLE = "tmSysRole";
	public static final String FIELD_MARKSTATUS = "markStatus";
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
