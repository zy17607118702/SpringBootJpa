package com.cn.springbootjpa.user.entity;

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
@Table(name = "tm_sys_resource", uniqueConstraints = @UniqueConstraint(columnNames = { "res_type", "res_name_c" }))
@EqualsAndHashCode(callSuper = true)
public class TmSysResource extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

	@Column(name="mark_status",length=1,nullable=false)
	private Boolean markStatus;

}
