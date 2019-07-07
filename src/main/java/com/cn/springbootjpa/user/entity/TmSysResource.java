package com.cn.springbootjpa.user.entity;

import java.util.Set;

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

import com.cn.springbootjpa.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "tm_sys_resource", uniqueConstraints = @UniqueConstraint(columnNames = { "res_type", "res_name_c" }))
@org.hibernate.annotations.Table(appliesTo = "tm_sys_resource",comment="资源信息表")
@EqualsAndHashCode(callSuper = true)
public class TmSysResource extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String TAB_TMSYSRESOURCE = "tm_sys_resource";
	public static final String FIELD_ID = "id";
	public static final String FIELD_PARENTRESID = "parentResid";
	public static final String FIELD_RESTYPE = "resType";
	public static final String FIELD_RESNAMEC = "resNameC";
	public static final String FIELD_RESNAMEE = "resNameE";
	public static final String FIELD_RESPATH = "resPath";
	public static final String FIELD_RESLEVEL = "resLevel";
	public static final String FIELD_MARKSTATUS = "markStatus";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tm_sys_resource_id",columnDefinition = "bigint(11) comment 'id'", unique = true, nullable = false)
	private Integer id;

	// 上级菜单
	@Column(name = "parent_res_id",columnDefinition = "bigint(11) comment '上级菜单id'", nullable = false)
	private Integer parentResid;

	@Column(name = "res_type",columnDefinition = "varchar(20) comment '菜单类型'")
	private String resType;

	@Column(name = "res_name_c",columnDefinition = "varchar(50) comment '菜单中文名'", nullable = false)
	private String resNameC;

	@Column(name = "res_name_e",columnDefinition = "varchar(50) comment '菜单英文名'", nullable = false)
	private String resNameE;

	@Column(name = "res_path",columnDefinition = "varchar(50) comment '菜单路径'")
	private String resPath;

	@Column(name = "res_level",columnDefinition = "int(5) comment '菜单等级'")
	private Integer resLevel;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tmSysResource")
	private Set<TrSysRoleResource> trSysRoleResource;
}
