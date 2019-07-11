/**
 * 
 */
package com.cn.springbootjpa.system.importvo;

import com.cn.springbootjpa.base.importVo.BaseImportVo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**导入vo类 用户信息
 * @author zhangyang
 *
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class TmSysUserVo extends BaseImportVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName;
	private String userPwd;
	private String realName;
	private String sex;
	private String age;
	private String phone;
	private String email;
	private String locked;
	private String markStatus;

}
