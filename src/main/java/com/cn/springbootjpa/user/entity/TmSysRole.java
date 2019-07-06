package com.cn.springbootjpa.user.entity;

import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cn.springbootjpa.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,region="RoleCache")
@Table(name = "tm_sys_role", uniqueConstraints = @UniqueConstraint(columnNames = "role_code"))
@EqualsAndHashCode(callSuper = true)
public class TmSysRole extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String TAB_TMSYSROLE = "tm_sys_role";
	public static final String FIELD_ID = "id";
	public static final String FIELD_ROLECODE = "roleCode";
	public static final String FIELD_ROLENAME = "roleName";
	public static final String FIELD_MARKSTATUS = "markStatus";
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "tm_sys_role_id", unique = true, nullable = false, precision = 11, scale = 0)
	private Integer id;

	@Column(name = "role_code", length = 30, unique = true)
	private String roleCode;

	@Column(name = "role_name", length = 50)
	private String roleName;

	@Column(name = "mark_status", length = 1, nullable = false)
	private Boolean markStatus;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tmSysRole")
	private Set<TrSysUserRole> trSysUserRole;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tmSysRole")
	private Set<TrSysRoleResource> trSysRoleResource;
}
