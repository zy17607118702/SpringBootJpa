package com.cn.springbootjpa.master.entity;

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
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "tm_bas_part_detail")
@org.hibernate.annotations.Table(appliesTo = "tm_bas_part_detail",comment="零部件信息详表")
@EqualsAndHashCode(callSuper = true)
public class TmBasPartDetail extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tm_bas_part_detail_id",columnDefinition = "bigint(11) comment 'id'", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "tm_bas_part_id",columnDefinition = "bigint(11) comment '零件信息id'", nullable = false)
	private Integer tmBasPartId;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="tm_bas_part_id",insertable=false,updatable=false)
	private TmBasPart tmBasPart;
	
	@Column(name="part_detail_code",columnDefinition = "varchar(20) comment '零件详情编号'", nullable = false)
	private String partdetailCode;
	
	@Column(name="part_detail_name",columnDefinition = "varchar(50) comment '零件详情名称'")
	private String partdetailName;
	
	@Column(name="part_detail_count",columnDefinition = "int(10) comment '零件详情数量'")
	private Integer partdetailCount;
		
}
