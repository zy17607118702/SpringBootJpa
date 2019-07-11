package com.cn.springbootjpa.system.entity.user;

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
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "userCache")
@Table(name = "tm_sys_user", uniqueConstraints = @UniqueConstraint(columnNames = "user_name"))
@org.hibernate.annotations.Table(appliesTo = "tm_sys_user", comment = "用户信息表")
@EqualsAndHashCode(callSuper = true)
public class TmSysUser extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String TAB_TMSYSUSER = "tm_sys_user";
	public static final String FIELD_ID = "id";
	public static final String FIELD_USERNAME = "userName";
	public static final String FIELD_USERPWD = "userPwd";
	public static final String FIELD_REALNAME = "realName";
	public static final String FIELD_SEX = "sex";
	public static final String FIELD_AGE = "age";
	public static final String FIELD_PHONE = "phone";
	public static final String FIELD_EMAIL = "email";
	public static final String FIELD_LOCKED = "locked";
	public static final String FIELD_MARKSTATUS = "markStatus";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tm_sys_user_id", columnDefinition = "bigint(11) comment 'id'", unique = true, nullable = false)
	private Integer id;

	@Column(name = "user_name", columnDefinition = "varchar(100) comment '用户名'", unique = true, nullable = false)
	private String userName;

	@Column(name = "user_pwd", columnDefinition = "varchar(200) comment '用户密码'", nullable = false)
	private String userPwd;

	@Column(name = "real_name", columnDefinition = "varchar(50) comment '用户真实姓名'", nullable = false)
	private String realName;

	@Column(name = "user_sex", columnDefinition = "varchar(10) comment '用户性别'")
	private String sex;

	@Column(name = "user_age", columnDefinition = "int(11) comment '用户年龄'")
	private Integer age;

	@Column(name = "user_phone", columnDefinition = "varchar(20) comment '用户电话'")
	private String phone;

	@Column(name = "user_email", columnDefinition = "varchar(30) comment '用户邮箱'")
	private String email;

	@Column(name = "is_locked", columnDefinition = "bit(1) comment '锁定'")
	private Boolean locked;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tmSysUser")
	private Set<TrSysUserRole> trSysUserRole;
	/**
	 * @Transient 不set该字段到数据库表 很多时候非数据库字段就可以用该注解
	 * @GeneratedValue 主键自增方式
	 * @Id 主键列
	 * @Column 数据库列
	 * @Temporal 时间格式选择 date dateTime timeStamp @Temporal(TemporalType.TIMESTAMP)
	 */
}
