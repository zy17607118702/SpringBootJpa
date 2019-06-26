package com.cn.springbootjpa.base.entity;

import java.io.Serializable;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 *
 * @author zhangyang 这个是所有entity的基类，该类描述了获取用户id，设置用户id，判断是否删除标记。
 *
 */
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer", "password" })
public abstract class Entity implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** flag indicates whether data is tend to be deleted physically. */
	private boolean deleted;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// TODO: CAUSION,DONT REMOVE THIS OVERRIDE METHOD.
		if (obj instanceof Entity)
			return this.getId() == null ? super.equals(obj) : this.getId().equals(((Entity) obj).getId());
		return super.equals(obj);
	}

	/**
	 * get entity id.
	 * 
	 * @return id.
	 */
	public abstract Integer getId();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.getId() == null ? super.hashCode() : this.getId().intValue();
	}

	/**
	 * get is deleted flag.
	 * 
	 * @return delete flag.
	 */
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * Newly constructed instance , not persistent yet.
	 * 
	 * @return
	 */
	@JsonIgnore
	@Transient
	public boolean isNew() {
		return this.getId() == null || 0 >= this.getId();
	}

	/**
	 * set delete flag.
	 * 
	 * @param deleted.
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * set entity id.
	 * 
	 * @param id
	 *            entity id.
	 */
	public abstract void setId(Integer id);

}
