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
@org.hibernate.annotations.Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,region="userCache")
@Table(name = "tm_sys_user", uniqueConstraints = @UniqueConstraint(columnNames = "user_name"))
@EqualsAndHashCode(callSuper = true)
public class TmSysUser extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String TAB_TMSYSUSER="tm_sys_user";
	public static final String FIELD_ID="id";
	public static final String FIELD_USERNAME="userName";
	public static final String FIELD_USERPWD="userPwd";
	public static final String FIELD_REALNAME="realName";
	public static final String FIELD_SEX="sex";
	public static final String FIELD_AGE="age";
	public static final String FIELD_PHONE="phone";
	public static final String FIELD_EMAIL="email";
	public static final String FIELD_LOCKED="locked";
	public static final String FIELD_MARKSTATUS="markStatus";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "tm_sys_user_id", unique = true, nullable = false, precision = 11, scale = 0)
	private Integer id;

	@Column(name="user_name",length=100,unique=true)
	private String userName;

	@Column(name="user_pwd",length=200)
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
