package com.cn.springbootjpa.base.common.code;

import java.io.Serializable;

public interface IdentityObject extends Serializable {
	public abstract Long getId();

	public abstract void setId(Long id);
}
