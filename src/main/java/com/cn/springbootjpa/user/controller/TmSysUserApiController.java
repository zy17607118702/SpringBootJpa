/**
 * 
 */
package com.cn.springbootjpa.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cn.springbootjpa.base.bo.BaseBo;
import com.cn.springbootjpa.base.common.CodeTypeConstants;
import com.cn.springbootjpa.base.common.QueryParam;
import com.cn.springbootjpa.base.common.page.PageReq;
import com.cn.springbootjpa.base.common.page.QueryCondition;
import com.cn.springbootjpa.base.controller.BaseController;
import com.cn.springbootjpa.base.exception.ApplicationException;
import com.cn.springbootjpa.user.bo.TmSysUserBo;
import com.cn.springbootjpa.user.entity.TmSysUser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @author zhangyang
 *
 */
@Api(value = "用户 主数据增删改查", description = "详细描述")
@RestController
@RequestMapping(value = "user")
public class TmSysUserApiController extends BaseController<TmSysUser, Integer> {
	@Autowired
	private TmSysUserBo tmSysUserBo;

	@Override
	protected BaseBo<TmSysUser, Integer> getBo() {
		// TODO Auto-generated method stub
		return tmSysUserBo;
	}

	@Override
	public void preSave(TmSysUser model) {
		// 用户加密
		model.setUserPwd(DigestUtils.md5Hex(model.getUserPwd()));
	}

	@Override
	protected Map<String, Object> validateUnique(TmSysUser t) {
		Map<String, Object> result = new HashMap<>();
		result.put(TmSysUser.FIELD_USERNAME, t.getUsername());
		result.put(TmSysUser.FIELD_AGE, t.getAge());
		return result;
	}

	// @GetMapping(value="exception")
	// public void ExceptionTest() {
	// throw new ApplicationException("AE0001");
	// }


	
//	@SuppressWarnings("rawtypes")
//	@ResponseBody
//	@PostMapping(value = { "/import" })
//	public List<ImportResult> importDo(@RequestBody MultipartFile file,
//	@RequestParam("chkUpdate") String chkUpdate, HttpServletRequest request) {
//	if(file!=null) {
//	String fileNameStr = file.getOriginalFilename();
//	String fileType = fileNameStr.substring(fileNameStr.lastIndexOf(".") + 1).toLowerCase();
//	if (!fileType.equals("xls") && !fileType.equals("xlsx")) {
//	throw new AppException("文件[" + fileNameStr + "]不是正确的导入模板！");
//	}
//	List<PartkitVo> partkitList =new ArrayList<PartkitVo>(); 
//	List<PartkitPartVo> partkitPart1List =new ArrayList<PartkitPartVo>(); 
//	List<PartkitPartVo> partkitPart2List =new ArrayList<PartkitPartVo>();
//	Map<String, Object> beans = new HashMap<String, Object>();
//	beans.put("partkitList", partkitList);
//	beans.put("partkitPart1List", partkitPart1List);
//	beans.put("partkitPart2List", partkitPart2List);
//	try {
//	List<String> errors = JxlsReader.readXls(
//	new ClassPathResource("/templates/partkit/partkit.xml").getInputStream(), file.getInputStream(),
//	beans);
//	if (errors != null && !errors.isEmpty()) {
//	throw new AppException(errors.toArray(new String[0]).toString());
//	}
//	try {
//	boolean isUpdate = chkUpdate.equals("1");
//	ImportResult<String> partkitresult = TmBasPartkitBo.importFrom(partkitList, isUpdate);
//	//导入子表数据
//	ImportResult<String> partkitPart1result = trBasPartkitPartBo.importPartForm1(partkitPart1List, isUpdate);
//	ImportResult<String> partkitPart2result = trBasPartkitPartBo.importPartForm2(partkitPart2List, isUpdate);
//	List<ImportResult> results = new ArrayList<ImportResult>();
//	results.add(partkitresult);
//	results.add(partkitPart1result);
//	results.add(partkitPart2result);
//	return results;
//	} catch (Exception e) {
//	throw new AppException(e.getLocalizedMessage());
//	}
//	} catch (Exception e) {
//	e.printStackTrace();
//	throw new AppException("数据导入异常:" + e.getLocalizedMessage());
//	}
//	}else {
//	throw new AppException("上传文件为空!");
//	}
//	}
	
/**
 * 导出功能实现
 * @param query
 * @return
 */
	@SuppressWarnings("unused")
	@PostMapping(value = { "/export" })
	@ApiOperation(value = "导出数据", notes = "导出页面数据")
	@ApiImplicitParam(name = "request", value = "查询条件集合", required = true, dataType = "JSON")
	public ModelAndView export(@RequestBody QueryParam query) {
		//清除分页相关信息
	PageReq pageReq = super.preBuildCriteria(query);
	//编辑导出条件
	List<QueryCondition> criteria = super.buildCriteria(query);
	long total =getBo().count(criteria);
	/**
	 * 注意bug 超过最大值就不能导出
	 */
	if(total>CodeTypeConstants.EXPORT_MAX_NUM) {
	throw new ApplicationException("AE0008");
	}
	if(total<CodeTypeConstants.EXPORT_MIN_NUM) {
	throw new ApplicationException("AE0009");
	}
	pageReq.setRows(CodeTypeConstants.EXPORT_MAX_NUM);
	
	Page<TmSysUser> findAll = getBo().findAll(criteria,PageReq.getPageable(pageReq));
	
//	List<TrBasPartkitPart> partPart1=new ArrayList<>();
//	List<TrBasPartkitPart> partPart2=new ArrayList<>();
//	        return new ModelAndView(new JxlsExcelView("/templates/partkit/parkit_export.xls", "parkit_" + ymd), map);
//	}
	return null;
}
}
