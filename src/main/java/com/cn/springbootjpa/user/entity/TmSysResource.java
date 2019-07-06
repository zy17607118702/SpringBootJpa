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
	@Column(name = "tm_sys_resource_id", unique = true, nullable = false, precision = 11, scale = 0)
	private Integer id;

	// 上级菜单
	@Column(name = "parent_res_id", length = 11, nullable = false)
	private Integer parentResid;

	@Column(name = "res_type", length = 20)
	private String resType;

	@Column(name = "res_name_c", length = 50)
	private String resNameC;

	@Column(name = "res_name_e", length = 50)
	private String resNameE;

	@Column(name = "res_path", length = 50)
	private String resPath;

	@Column(name = "res_level", precision = 5, scale = 0)
	private Integer resLevel;

	@Column(name = "mark_status", length = 1, nullable = false)
	private Boolean markStatus;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tmSysResource")
	private Set<TrSysRoleResource> trSysRoleResource;
}
