package com.cn.springbootjpa.master.entity;

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

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 子母表关联关系注解demo
 * @author zhangyang
 *
 */
@Data
@Entity
@Table(name = "tm_bas_part",uniqueConstraints=@UniqueConstraint(columnNames="part_no"))
@org.hibernate.annotations.Table(appliesTo = "tm_bas_part",comment="零部件信息表")
@EqualsAndHashCode(callSuper = true)
public class TmBasPart extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tm_bas_part_id",columnDefinition = "bigint(11) comment 'id'", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "part_no",columnDefinition = "varchar(20) comment '零件编号'", nullable = false)
	private String partNo;
	
	@Column(name = "part_name_c",columnDefinition = "varchar(50) comment '零件中文名'", nullable = false)
	private String partNameC;
	
	@Column(name = "part_name_e",columnDefinition = "varchar(50) comment '零件英文名'")
	private String partNameE;
	
	@Column(name = "part_type",columnDefinition = "int(50) comment '零件类型'", nullable = false)
	private Integer partType;
	
	/**
	 * 一对多注解 @onetoMany
	 * cascade 类似于hibernate 级联 fetch 类似于懒加载
	 * mappedBy 多方中配置的一方对象
	 * 注意:多方和一方对象关联需要使用jsonIgnore防止出现循环注入
	 */
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="tmBasPart")
	private Set<TmBasPartDetail> tmBasPartDetail;

}
