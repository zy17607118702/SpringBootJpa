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
@Table(name = "tm_sys_user", uniqueConstraints = @UniqueConstraint(columnNames = "user_name"))
@EqualsAndHashCode(callSuper = true)
public class TmSysUser extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
	@Column(name = "tm_sys_user_id", unique = true, nullable = false, precision = 11, scale = 0)
	private Integer id;

	@Column(name="user_name",length=50,unique=true)
	private String userName;

	@Column(name="user_pwd",length=50)
	private String userPwd;

	@Column(name="real_name",length=30)
	private String realName;

	@Column(name="user_sex",length=10)
	private String sex;

	@Column(name="user_age", precision = 11, scale = 0)
	private Integer age;

	@Column(name="user_phone",length=15)
	private String phone;

	@Column(name="user_email",length=30)
	private String email;

	@Column(name="is_locked",length=1,nullable=false)
	private Boolean locked;

	@Column(name="mark_status",length=1,nullable=false)
	private Boolean markStatus;

}
