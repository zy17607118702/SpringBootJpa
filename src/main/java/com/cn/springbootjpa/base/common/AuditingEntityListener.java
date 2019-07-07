package com.cn.springbootjpa.base.common;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.cn.springbootjpa.base.entity.BaseEntity;
import com.cn.springbootjpa.util.LoginUserUtil;

public class AuditingEntityListener {

@PrePersist
public void preSave(BaseEntity entity) {
String username = LoginUserUtil.getLoginUser();
entity.setCreateBy(username);
entity.setCreateTime(new Date());
entity.setUpdateBy(username);
entity.setUpdateTime(new Date());
}

@PreUpdate
public void preUpdate(BaseEntity entity) {
String username = LoginUserUtil.getLoginUser();
entity.setCreateBy(entity.getCreateBy());
entity.setCreateTime(entity.getCreateTime());
entity.setUpdateBy(username);
entity.setUpdateTime(new Date());
}
}