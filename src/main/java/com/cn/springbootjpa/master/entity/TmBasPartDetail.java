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
@EqualsAndHashCode(callSuper = true)
public class TmBasPartDetail extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "tm_bas_part_detail_id", unique = true, nullable = false, precision = 11, scale = 0)
	private Integer id;
	
	@Column(name = "tm_bas_part_id", precision = 11, scale = 0)
	private Integer tmBasPartId;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="tm_bas_part_id",insertable=false,updatable=false)
	private TmBasPart tmBasPart;
	
	@Column(name="part_detail_code",length=20)
	private String partdetailCode;
	
	@Column(name="part_detail_name",length=50)
	private String partdetailName;
	
	@Column(name="part_detail_count",precision=10,scale=0)
	private Integer partdetailCount;
	
	@Column(name="mark_status",length=1,nullable=false)
	private Boolean markStatus;
	
}
