/**********************************************************************
 * FILE : CodeNameItem.java
 * CREATE DATE : 2008-06-20
 * DESCRIPTION :
 *		
 *		
 *		
 *      
 * CHANGE HISTORY LOG
 *---------------------------------------------------------------------
 * NO.|    DATE    |     NAME     |     REASON     | DESCRIPTION
 *---------------------------------------------------------------------
 * 1  | 2008-06-20 |  Liu Yan Lu  |    创建草稿版本
 * 2  | 2013-05-02 |  Yang Zhi    |	Task #13736: 新增CodeList中的英文描述字段
 *---------------------------------------------------------------------              
 **********************************************************************
 */
package com.cn.springbootjpa.base.common.code;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

public class CodeNameItem implements IdentityObject {

	private static final long serialVersionUID = -6477134999088399608L;

	@SuppressWarnings("unused")
	private static final String String=null;
	/**
	 * Build a CodeNameItem instance from an object with given mapping properties.
	 * 
	 * @param obj
	 * @param idProps
	 * @param codeProps
	 * @param nameProps
	 * @param descProps
	 * @return
	 */
	public static CodeNameItem fromObject(Object obj, String idProps, String codeProps, String nameProps,
			String descProps) {
		Long id;
		try {
			id = (Long) PropertyUtils.getProperty(obj, idProps);
			String code = StringUtils.defaultString((String) PropertyUtils.getProperty(obj, codeProps));
			String name = StringUtils.defaultString((String) PropertyUtils.getProperty(obj, nameProps));
			String description = StringUtils.defaultString((String) PropertyUtils.getProperty(obj, descProps));
			return new CodeNameItem(id, null, code, name, description, description);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Build a CodeNameItem instance from an object with given mapping properties.
	 * 
	 * @param obj
	 * @param idProps
	 * @param codeProps
	 * @param nameProps
	 * @param descProps
	 * @param markStatus
	 *            启用
	 * @return
	 */
	public static CodeNameItem fromObject(Object obj, String idProps, String codeProps, String nameProps,
			String descProps, String markStatus) {
		Long id;
		try {
			id = (Long) PropertyUtils.getProperty(obj, idProps);
			String code = StringUtils.defaultString((String) PropertyUtils.getProperty(obj, codeProps));
			String name = StringUtils.defaultString((String) PropertyUtils.getProperty(obj, nameProps));
			String description = StringUtils.defaultString((String) PropertyUtils.getProperty(obj, descProps));
			Object property = null;
			try {
				property = PropertyUtils.getProperty(obj, markStatus);
			} catch (Exception e) {
				property = true;
			}
			// 判断人员markStatus字段类型
			Boolean status = null;
			if (property instanceof Boolean) {
				status = (Boolean) PropertyUtils.getProperty(obj, markStatus);
			} else if (property instanceof Integer) {
				Integer num = (Integer) property;
				status = num == 1 ? true : false;
			} else if (property instanceof Long) {
				Integer num = 1;
				status = property.equals(num.longValue()) ? true : false;
			} else if (property instanceof String) {
				status = BooleanUtils.toBoolean((String) property);
			} else {
				// 其他类型默认都是启用的
				status = true;
			}
			return new CodeNameItem(id, null, code, name, description, description, status);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Long id = null;

	private String type;

	private String code;

	private String name;
	private String description;

	/**
	 * @Fields descriptionEn : CodeList中的英文描述，2013-01-23，yangzhi
	 */
	private String descriptionEn;
	private String funcCn;// BIZCodeList功能中文描述

	private String funcEn; // BIZCodeList 功能英文描述

	private Boolean markStatus;

	public CodeNameItem() {
	}

	public CodeNameItem(Long id, String type, String code, String name, String description) {
		super();
		this.id = id;
		this.type = type;
		this.code = code;
		this.name = (name == null || "".equals(name)) ? "" : name;
		this.description = (description == null || "".equals(description)) ? "" : description;
	}

	/**
	 * 创建一个新的实例 CodeNameItem,此构造方法能创建CodeList中有英文描述
	 * 
	 * @param id
	 * @param type
	 * @param code
	 * @param name
	 * @param description
	 * @param descriptionEn
	 */
	public CodeNameItem(Long id, String type, String code, String name, String description, String descriptionEn) {
		super();
		this.id = id;
		this.type = type;
		this.code = code;
		this.name = (name == null || "".equals(name)) ? "" : name;
		this.description = (description == null || "".equals(description)) ? "" : description;
		this.descriptionEn = (descriptionEn == null || "".equals(descriptionEn)) ? "" : descriptionEn;
	}

	/**
	 * 创建一个新的实例 CodeNameItem,此构造方法能创建codelist启用不启用
	 * 
	 * @param id
	 * @param type
	 * @param code
	 * @param name
	 * @param description
	 * @param descriptionEn
	 */
	public CodeNameItem(Long id, String type, String code, String name, String description, String descriptionEn,
			Boolean markStatus) {
		super();
		this.id = id;
		this.type = type;
		this.code = code;
		this.name = (name == null || "".equals(name)) ? "" : name;
		this.description = (description == null || "".equals(description)) ? "" : description;
		this.descriptionEn = (descriptionEn == null || "".equals(descriptionEn)) ? "" : descriptionEn;
		this.markStatus = markStatus;
	}

	/**
	 * 创建一个新的实例 CodeNameItem,此构造方法能创建CodeList中有英文描述
	 * 
	 * @param id
	 * @param type
	 * @param code
	 * @param name
	 * @param description
	 * @param descriptionEn
	 * @param funcCn
	 * @param funcEn
	 */
	public CodeNameItem(Long id, String type, String code, String name, String description, String descriptionEn,
			String funcCn, String funcEn) {
		super();
		this.id = id;
		this.type = type;
		this.code = code;
		this.name = (name == null || "".equals(name)) ? "" : name;
		this.description = (description == null || "".equals(description)) ? "" : description;
		this.descriptionEn = (descriptionEn == null || "".equals(descriptionEn)) ? "" : descriptionEn;
		this.funcCn = (funcCn == null || "".equals(funcCn)) ? "" : funcCn;
		this.funcEn = (funcEn == null || "".equals(funcEn)) ? "" : funcEn;
	}

	public int compare(CodeNameItem item) {
		return (this.getCode() + this.getName()).compareToIgnoreCase(item.getCode() + item.getName());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		final CodeNameItem other = (CodeNameItem) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public String getDescriptionEn() {
		return descriptionEn;
	}

	public String getFuncCn() {
		return funcCn;
	}

	public String getFuncEn() {
		return funcEn;
	}

	public Long getId() {
		return id;
	}

	public String getLabel() {
		return this.name;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getValue() {
		return this.code;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((code == null) ? 0 : code.hashCode());
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDescriptionEn(String descriptionEn) {
		this.descriptionEn = descriptionEn;
	}

	public void setFuncCn(String funcCn) {
		this.funcCn = funcCn;
	}

	public void setFuncEn(String funcEn) {
		this.funcEn = funcEn;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getMarkStatus() {
		return markStatus;
	}

	public void setMarkStatus(Boolean markStatus) {
		this.markStatus = markStatus;
	}

	public String toString() {
		return "id:" + id + "\ttype:" + type + "\tcode:" + code + "\tname:" + name + "\tdescription:" + description;
	}
}
