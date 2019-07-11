package com.cn.springbootjpa.system.entity.user;

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
@Table(name = "tr_sys_role_resource")
@org.hibernate.annotations.Table(appliesTo = "tr_sys_role_resource",comment="角色资源信息表")
@EqualsAndHashCode(callSuper = true)
public class TrSysRoleResource extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String TAB_TRSYSROLERESOURCE = "tr_sys_role_resource";
	public static final String FIELD_ID = "id";
	public static final String FIELD_TMSYSRESOURCEID = "tmSysResourceId";
	public static final String FIELD_TMSYSRESOURCE = "tmSysResource";
	public static final String FIELD_TMSYSROLEID = "tmSysRoleId";
	public static final String FIELD_TMSYSROLE = "tmSysRole";
	public static final String FIELD_MARKSTATUS = "markStatus";
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tr_sys_role_resource_id",columnDefinition = "bigint(11) comment 'id'", unique = true, nullable = false)
	private Integer id;

	@Column(name = "tm_sys_resource_id",columnDefinition = "bigint(11) comment '资源信息id'", nullable = false)
	private Integer tmSysResourceId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tm_sys_resource_id", insertable = false, updatable = false)
	private TmSysResource tmSysResource;

	@Column(name = "tm_sys_role_id",columnDefinition = "bigint(11) comment '角色信息id'", nullable = false)
	private Integer tmSysRoleId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tm_sys_role_id", insertable = false, updatable = false)
	private TmSysRole tmSysRole;
}
