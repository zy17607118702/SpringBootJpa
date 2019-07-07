/**
 * 
 */
package com.cn.springbootjpa.user.bo.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cn.springbootjpa.base.bo.BaseBoImpl;
import com.cn.springbootjpa.base.common.ImportResult;
import com.cn.springbootjpa.base.dao.BaseDao;
import com.cn.springbootjpa.user.bo.TmSysUserBo;
import com.cn.springbootjpa.user.dao.TmSysUserDao;
import com.cn.springbootjpa.user.entity.TmSysUser;
import com.cn.springbootjpa.user.importvo.TmSysUserVo;

/**
 * @author zhangyang
 *
 */
@Service
@Transactional
public class TmSysUserBoImpl extends BaseBoImpl<TmSysUser, Integer> implements TmSysUserBo {
	@Autowired
	private TmSysUserDao dao;

	@Override
	public BaseDao<TmSysUser, Integer> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	@Override
	public TmSysUser checkPassword(String userName, String password) {
		return dao.checkPassword(userName, password);
	}

	@Override
	public TmSysUser findByUsername(String userName) {
		return dao.findByUsername(userName);
	}

	@Override
	public void updatePwd(String username, String pwd) {
		dao.updatePwd(username, pwd);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW,timeout=7200)
	public ImportResult<String> importVo(List<TmSysUserVo> list, boolean isUpdate) {
		List<String> tipsList = new ArrayList<>();
		long total = list.size();
		long failTotal = 0;
		// 需要增加的数据
		List<TmSysUser> insertList = new ArrayList<>();
		// 需要修改的数据
		List<TmSysUser> updateList = new ArrayList<>();
		for (TmSysUserVo vo : list) {
			int index = list.indexOf(vo) + 1;
			String tips = "第" + index + "条记录";
			TmSysUserVo resultVo = importVo(vo, isUpdate, insertList, updateList);
			if (resultVo != null && resultVo.isSaveOrUpdate() != true) {
				tipsList.add(tips + "导入失败! " + resultVo.getMessages());
				failTotal += 1;
			} else {
				tipsList.add(tips + "导入成功！");
			}
		}
		// 将所有数据保存到数据库中
		this.save(insertList);
		this.save(updateList);
		return new ImportResult<String>(tipsList, total, (total - failTotal), failTotal);
	}

	public TmSysUserVo importVo(TmSysUserVo vo, boolean isUpdate, List<TmSysUser> insertList,
			List<TmSysUser> updateList) {
		// 必输字段校验
		String[] vos = new String[] { vo.getUserName(), vo.getUserPwd(), vo.getRealName(), vo.getAge(), vo.getSex() };
		if (StringUtils.isAnyBlank(vos)) {
			vo.setSaveOrUpdate(false);
			vo.setMessages("当前必输字段为空");
			return vo;
		}
		// 特殊字段校验
		try {
			Integer.parseInt(vo.getAge());
		} catch (Exception e) {
			vo.setSaveOrUpdate(false);
			vo.setMessages("用户年龄必须为数字");
			return vo;
		}
		// 数据库重复性校验
		TmSysUser user = this.findByUsername(vo.getUserName());
		BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
		if (user != null && isUpdate) {
			// 修改操作
			// 导入密码加密
			user.setUserName(vo.getUserName());
			user.setUserPwd(bCryptPasswordEncoder.encode(vo.getUserPwd()));
			user.setRealName(vo.getRealName());
			user.setRealName(vo.getRealName());
			user.setSex("男".equals(vo.getSex()) ? "男" : "女");
			user.setAge(Integer.parseInt(vo.getAge()));
			user.setEmail(vo.getEmail());
			user.setPhone(vo.getPhone());
			user.setLocked("1".equals(vo.getLocked()) ? true : false);
			user.setMarkStatus("1".equals(vo.getMarkStatus()) ? false : true);
			updateList.add(user);
			vo.setSaveOrUpdate(true);
			return vo;
		} else if (user == null) {
			// 新增操作
			TmSysUser result = new TmSysUser();
			// 导入密码加密
			result.setUserName(vo.getUserName());
			result.setUserPwd(bCryptPasswordEncoder.encode(vo.getUserPwd()));
			result.setRealName(vo.getRealName());
			result.setRealName(vo.getRealName());
			result.setSex("男".equals(vo.getSex()) ? "男" : "女");
			result.setAge(Integer.parseInt(vo.getAge()));
			result.setEmail(vo.getEmail());
			result.setPhone(vo.getPhone());
			result.setLocked("1".equals(vo.getLocked()) ? true : false);
			result.setMarkStatus("1".equals(vo.getMarkStatus()) ? true : false);
			insertList.add(result);
			vo.setSaveOrUpdate(true);
			return vo;
		} else {
			vo.setSaveOrUpdate(false);
			vo.setMessages("当前用户已存在,不能导入");
			return vo;
		}
	}

	@Override
	public List<TmSysUser> findRoleUsers(String roleCode) {
		return dao.findRoleUsers(roleCode);
	}

	/* (non-Javadoc)
	 * @see com.cn.springbootjpa.user.bo.TmSysUserBo#findUserNoRoles(java.lang.String)
	 */
	@Override
	public List<TmSysUser> findUserNoRoles(String roleCode) {
		// TODO Auto-generated method stub
		return dao.findUserNoRoles(roleCode);
	}

}
