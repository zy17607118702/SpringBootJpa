/**
 * 
 */
package com.cn.springbootjpa.user.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cn.springbootjpa.base.bo.BaseBo;
import com.cn.springbootjpa.base.common.code.CodeTypeConstants;
import com.cn.springbootjpa.base.common.page.PageReq;
import com.cn.springbootjpa.base.common.page.QueryCondition;
import com.cn.springbootjpa.base.common.query.ImportResult;
import com.cn.springbootjpa.base.common.query.QueryParam;
import com.cn.springbootjpa.base.controller.BaseController;
import com.cn.springbootjpa.base.exception.AppException;
import com.cn.springbootjpa.base.exception.ApplicationException;
import com.cn.springbootjpa.user.bo.TmSysUserBo;
import com.cn.springbootjpa.user.entity.TmSysUser;
import com.cn.springbootjpa.user.importvo.TmSysUserVo;
import com.cn.springbootjpa.util.DateUtils;
import com.cn.springbootjpa.util.LoginUserUtil;
import com.cn.springbootjpa.util.excel.JxlsExcelView;
import com.cn.springbootjpa.util.excel.JxlsReader;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Override
	protected BaseBo<TmSysUser, Integer> getBo() {
		// TODO Auto-generated method stub
		return tmSysUserBo;
	}

	@Override
	public void preSave(TmSysUser model) {
		// 用户加密
		model.setUserPwd(bCryptPasswordEncoder.encode(model.getUserPwd()));
	}

	@Override
	protected Map<String, Object> validateUnique(TmSysUser t) {
		Map<String, Object> result = new HashMap<>();
		result.put(TmSysUser.FIELD_USERNAME, t.getUserName());
		result.put(TmSysUser.FIELD_AGE, t.getAge());
		return result;
	}

	@ResponseBody
	@PostMapping(value = { "/import" })
	@ApiOperation(value = "导入数据", notes = "导入用户数据")
	@ApiImplicitParams({ @ApiImplicitParam(name = "file", value = "导入文件", required = true, dataType = "file"),
			@ApiImplicitParam(name = "chkUpdate", value = "重复时可导入", required = true, dataType = "String") })
	public ImportResult<String> importDo(@RequestBody MultipartFile file, @RequestParam("chkUpdate") String chkUpdate) {
		if (file != null) {
			String fileNameStr = file.getOriginalFilename();
			String fileType = fileNameStr.substring(fileNameStr.lastIndexOf(".") + 1).toLowerCase();
			if (!fileType.equals("xls") && !fileType.equals("xlsx")) {
				throw new AppException("文件[" + fileNameStr + "]不是正确的导入模板！");
			}
			List<TmSysUserVo> userVoList = new ArrayList<TmSysUserVo>();
			Map<String, Object> beans = new HashMap<String, Object>();
			beans.put("list", userVoList);
			try {
				List<String> errors = JxlsReader.readXls(
						new ClassPathResource("/templates/user/user.xml").getInputStream(), file.getInputStream(),
						beans);
				if (errors != null && !errors.isEmpty()) {
					throw new AppException(errors.toArray(new String[0]).toString());
				}
				try {
					boolean isUpdate = chkUpdate.equals("1");
					return tmSysUserBo.importVo(userVoList, isUpdate);
				} catch (Exception e) {
					throw new AppException(e.getLocalizedMessage());
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new AppException("数据导入异常:" + e.getLocalizedMessage());
			}
		} else {
			throw new AppException("上传文件为空!");
		}
	}

	/**
	 * 导出功能实现
	 * 
	 * @param query
	 * @return
	 */
	@PostMapping(value = { "/export" })
	@ApiOperation(value = "导出数据", notes = "导出页面数据")
	@ApiImplicitParam(name = "request", value = "查询条件集合", required = true, dataType = "JSON")
	public ModelAndView export(@RequestBody QueryParam query) {
		// 清除分页相关信息
		PageReq pageReq = super.preBuildCriteria(query);
		// 编辑导出条件
		List<QueryCondition> criteria = super.buildCriteria(query);
		long total = getBo().count(criteria);
		/**
		 * 注意bug 超过最大值就不能导出
		 */
		if (total > CodeTypeConstants.EXPORT_MAX_NUM) {
			throw new ApplicationException("AE0008");
		}
		if (total < CodeTypeConstants.EXPORT_MIN_NUM) {
			throw new ApplicationException("AE0009");
		}
		pageReq.setRows(CodeTypeConstants.EXPORT_MAX_NUM);

		Page<TmSysUser> findAll = getBo().findAll(criteria, PageReq.getPageable(pageReq));
		List<TmSysUser> data = findAll.getContent();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", data != null ? data : null);
		String ymd = DateUtils.format(new Date(), DateUtils.FORMAT_DATE_YYYY_MM_DD_HHMMSS);
		return new ModelAndView(new JxlsExcelView("/templates/user/user_export.xlsx", "user_" + ymd), map);
	}
	
	@ApiOperation(value = "用户信息", notes = "获取登陆用户信息")
	@GetMapping("/info")
	public TmSysUser findByUserName() {
		TmSysUser tmSysUser=null;
		try {
			String username=LoginUserUtil.getLoginUser();
			tmSysUser= tmSysUserBo.findByUsername(username);
			if(tmSysUser==null) {
				throw new ApplicationException("AE0001",username);
			}
		} catch (Exception e) {
			throw new AppException(e.getLocalizedMessage());
		}
		return tmSysUser;
	}
}
