package com.cn.springbootjpa.user.vo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

public class Resource implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5714009278575516772L;
	public final static String TYPE_DEVICE = "DEVICE";

	public final static String TYPE_DEVICE_CAT = "DEVICE-CAT";

	public final static String TYPE_DEVICE_START_STR = "DEVICE";

	public final static String TYPE_DEVICE_VIEW = "DEVICE-VIW";

	public final static String TYPE_RICHCLIENT = "RC";

	public final static String TYPE_UI_ACTION = "ACT";

	public final static String TYPE_UI_CATEGORY = "CAT";

	// public final static String TYPE_UI_PERSPECTIVE = "PER";

	public final static String TYPE_UI_VIEW = "VIW";

	public final static String TYPE_WEB = "WEB";

	public final static String TYPE_WEB_CAT = "WEB-CAT";

	public final static String TYPE_WEB_FUNCTION = "FUNC";

	public final static String TYPE_WEB_URL = "URL";

	public final static String TYPE_SUPPLIER_WEB_FUNCTION = "FUNC";

	private Long concreteResource;

	private Long id;
    
    private Long roleId;
    
	private Boolean isLeaf;
	//上级菜单
	private Resource parentResource;

	private String resDesc;

	private String resName;
	//路径
	private String resourcePath;
	//子菜单
	private Collection<Resource> resources = null;
	//菜单类型
	private String resourceType;

	private Integer sequenceNum;
	
	private Boolean isNegative;
	//等级
	private Integer level;

	public Resource() {
	}

	public Resource(Resource parentResource, String resourceType,
			String resourcePath, String resName, String resDesc,
			Long concreteResource, Integer sequenceNum, Boolean isLeaf,
			Set<Resource> tmSysResourcesForFuncResourceId,
			Set<Resource> tmSysResourcesForUiResourceId,
			Set<Resource> tmSysResources) {
		this.parentResource = parentResource;
		this.resourceType = resourceType;
		this.resourcePath = resourcePath;
		this.resName = resName;
		this.resDesc = resDesc;
		this.concreteResource = concreteResource;
		this.sequenceNum = sequenceNum;
		this.isLeaf = isLeaf;
		this.resources = tmSysResources;
	}

	public Long getConcreteResource() {
		return this.concreteResource;
	}

	public Long getId() {
		return this.id;
	}

	public Boolean getIsLeaf() {
		return this.isLeaf;
	}

	public Resource getParentResource() {
		return this.parentResource;
	}

	public String getResDesc() {
		return this.resDesc;
	}

	public String getResName() {
		return this.resName;
	}

	public String getResourcePath() {
		return this.resourcePath;
	}

	public Collection<Resource> getResources() {
		return this.resources;
	}

	public String getResourceType() {
		return this.resourceType;
	}

	public Integer getSequenceNum() {
		return this.sequenceNum;
	}

	public void setConcreteResource(Long concreteResource) {
		this.concreteResource = concreteResource;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIsLeaf(Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public void setParentResource(Resource tmSysResource) {
		this.parentResource = tmSysResource;
	}

	public void setResDesc(String resDesc) {
		this.resDesc = resDesc;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	public void setResources(Collection<Resource> tmSysResources) {
		this.resources = tmSysResources;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public void setSequenceNum(Integer sequenceNum) {
		this.sequenceNum = sequenceNum;
	}
    
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
    
	
//	public Integer getLevel() {
//		return level;
//	}
//
//	public void setLevel(Integer level) {
//		this.level = level;
//	}

	public Boolean getIsNegative() {
		return isNegative;
	}

	public void setIsNegative(Boolean isNegative) {
		this.isNegative = isNegative;
	}

	public String toString() {
		return this.id + "\t" + this.resourceType + "\t" + this.resName + "\t"
				+ this.resDesc + "\t" + this.resourcePath;
	}
}
