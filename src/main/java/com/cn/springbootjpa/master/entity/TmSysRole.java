package com.cn.springbootjpa.master.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cn.springbootjpa.base.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "tm_sys_role", uniqueConstraints = @UniqueConstraint(columnNames = "role_code"))
@EqualsAndHashCode(callSuper = true)
public class TmSysRole extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tm_sys_role_id", unique = true, nullable = false, precision = 11, scale = 0)
	private Integer id;

	@Column(name = "role_code", length = 30, unique = true)
	private String roleCode;

	@Column(name = "role_name", length = 50)
	private String roleName;

	@Column(name="mark_status",length=1,nullable=false)
	private Boolean markStatus;

}
