package com.cn.springbootjpa.util;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.cn.springbootjpa.base.entity.BaseEntity;

public class AuditingEntityListener {

@PrePersist
public void preSave(BaseEntity entity) {
String username = LoginUserUtil.getLoginUser();
Date date = DateUtils.getCurrentDate();
entity.setCreateBy(username);
entity.setCreateTime(date);
entity.setUpdateBy(username);
entity.setUpdateTime(date);
}

@PreUpdate
public void preUpdate(BaseEntity entity) {
String username = LoginUserUtil.getLoginUser();
Date date = DateUtils.getCurrentDate();
entity.setCreateBy(entity.getCreateBy());
entity.setCreateTime(entity.getCreateTime());
entity.setUpdateBy(username);
entity.setUpdateTime(date);
}
}