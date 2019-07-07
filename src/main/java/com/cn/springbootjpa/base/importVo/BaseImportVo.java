/**
 * 
 */
package com.cn.springbootjpa.base.importVo;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**页面导入vo类基础类
 * @author zhangyang
 *
 */
@Setter
@Getter
@MappedSuperclass //该类不会映射到数据库中 但是该类的子类一定会映射到数据库中
@NoArgsConstructor
public class BaseImportVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**数据导入信息**/
	private String messages;
	
	/** 是否更新旧数据 **/
	private boolean update;
	
	/** true:save  false:update **/
	private boolean saveOrUpdate;

}
