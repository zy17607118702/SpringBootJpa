/**********************************************************************
 * FILE : MessageCommonUtil.java
 * CREATE DATE : 2008-06-18
 * DESCRIPTION :
 *		
 *		消息工具处理
 *      
 * CHANGE HISTORY LOG
 *---------------------------------------------------------------------
 * NO.|    DATE    |     NAME     |     REASON     | DESCRIPTION
 *---------------------------------------------------------------------
 * 1  | 2008-06-18 |  Liu Yan Lu  |    创建草稿版本
 * 2  | 2010-10-12 |  zhiyang     |    对数据进行格式化操作
 *---------------------------------------------------------------------              
 **********************************************************************
 */
package com.cn.springbootjpa.mq.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.saicmotor.framework.exception.FtpDeliveryException;
import com.saicmotor.imes.bo.TcSysCodeListBO;
import com.saicmotor.imes.dao.ITmSysAlertDao;
import com.saicmotor.imes.dataobject.codelist.CodeTypeConstants;
import com.saicmotor.imes.model.TmSysAlert;

/**
 * 
 * @author xiaoxiao
 *         <p>
 *         消息工具处理
 */
@Component("messageCommonUtil")
public class MessageCommonUtil {

	/**
	 * ITmSysAlertDao DAO管理类
	 */
	@Autowired
	public ITmSysAlertDao tmSysAlertDao;

	/**
	 * TcSysCodeListBO DAO管理类
	 */
	@Autowired
	public TcSysCodeListBO tcSysCodeListBO;

	/**
	 * 
	 * 记录一个系统报警信息
	 * 
	 * @param application
	 *            应用ID
	 * @param module
	 *            模块名称
	 * @param place
	 *            报警根源
	 * @param errorInfo
	 *            错误信息
	 * 
	 */
	public void publishAlert(String application, String module, String place,
			String errorInfo) {
		Date currentTime = Calendar.getInstance().getTime();
		TmSysAlert tmSysAlert = new TmSysAlert();
		tmSysAlert.setAlterTime(currentTime);
		tmSysAlert.setAlterLevel(CodeTypeConstants.SYS_ALERT_LEVEL_WARNING);
		tmSysAlert.setAlertApplication(application);
		tmSysAlert.setAlertModule(module);
		tmSysAlert.setAlterPlace(place);
		tmSysAlert.setAlterInfo(errorInfo);
		this.tmSysAlertDao.save(tmSysAlert);
	}

	/**
	 * 
	 * 记录一个系统报警信息
	 * 
	 * @param application
	 *            应用ID
	 * @param module
	 *            模块名称
	 * @param place
	 *            报警根源
	 * @param errorInfo
	 *            错误信息
	 * 
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, timeout = 7200)
	public void publishAlertByCommit(String application, String module, String place,
			String errorInfo) {
		Date currentTime = Calendar.getInstance().getTime();
		TmSysAlert tmSysAlert = new TmSysAlert();
		tmSysAlert.setAlterTime(currentTime);
		tmSysAlert.setAlterLevel(CodeTypeConstants.SYS_ALERT_LEVEL_WARNING);
		tmSysAlert.setAlertApplication(application);
		tmSysAlert.setAlertModule(module);
		tmSysAlert.setAlterPlace(place);
		tmSysAlert.setAlterInfo(errorInfo);
		this.tmSysAlertDao.save(tmSysAlert);
	}
	
	/*
	 * public Long getAlertLevel() { Collection<TcSysCodeList> list =
	 * tcSysCodeListBO.findCodeLists(CodeTypeConstants.SYS_ALERT_LEVEL); if
	 * (list == null || list.size() == 0) { return 0L; } Long ret = 0L; for
	 * (TcSysCodeList vo : list) { if (vo.getCodeValue() != null &&
	 * CodeTypeConstants.SYS_ALERT_LEVEL_WARNING.equals(vo.getCodeValue())) {
	 * ret = vo.getCodeValue() == null ? 0L : Long.valueOf(vo.getCodeValue());
	 * break; } } return ret; }
	 */

	/**
	 * 
	 * 获取消息头信息
	 * 
	 * @param msg
	 *            消息信息
	 * @return 返回消息头参数
	 * @throws JMSException
	 *             JMS处理异常
	 */
	public HashMap<String, String> retriveHeaders(Message msg)
			throws JMSException {

		Enumeration<String> enum_prop = msg.getPropertyNames();
		HashMap<String, String> msgHeaders = new HashMap<String, String>();

		msgHeaders.put("JMSMessageID", msg.getJMSMessageID());
		msgHeaders.put("JMSCorrelationID", msg.getJMSCorrelationID());
		msgHeaders.put("JMSTimestamp", ((Long) msg.getJMSTimestamp())
				.toString());

		while (enum_prop.hasMoreElements()) {
			String propName = (String) enum_prop.nextElement();
			String tempValue = msg.getStringProperty(propName);
			if (null != tempValue) {
				msgHeaders.put(propName, tempValue);
			}
		}
		return msgHeaders;
	}
	
	/**
	 * 
	 * 获取消息头信息
	 * 
	 * @param msg
	 *            消息信息
	 * @return 返回消息头参数
	 * @throws JMSException
	 *             JMS处理异常
	 */
	public HashMap<String, String> retriveNoHeaders(Message msg)
			throws JMSException {

		Enumeration<String> enum_prop = msg.getPropertyNames();
		HashMap<String, String> msgHeaders = new HashMap<String, String>();

		msgHeaders.put("JMSMessageID", UUID.randomUUID().toString());
		msgHeaders.put("JMSCorrelationID", ""); // 关联的消息ID，回传的时候调用
		msgHeaders.put("JMSTimestamp", ((Long)System.currentTimeMillis()).toString());

		while (enum_prop.hasMoreElements()) {
			String propName = (String) enum_prop.nextElement();
			String tempValue = msg.getStringProperty(propName);
			if (null != tempValue) {
				msgHeaders.put(propName, tempValue);
			}
		}
		return msgHeaders;
	}
	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
	}



	/**
	 * 
	 * 对数据进行格式化操作
	 * 
	 * @param str
	 *            需要格式的字符串
	 * @param startAndEndIndexStr
	 *            开始的其实字符
	 * @return
	 * @throws FtpDeliveryException
	 *             FTP文件传输异常
	 */
	public static String format(String str, String startAndEndIndexStr)
			throws FtpDeliveryException {

		if ((startAndEndIndexStr == null || "".equals(startAndEndIndexStr))) {
			throw new FtpDeliveryException("incorrect index defination!");
		}
		try {
			int startIndex = 0;
			int endIndex = 0;
			if (startAndEndIndexStr.indexOf(",") < 0) {
				startIndex = Integer.parseInt(startAndEndIndexStr);
				endIndex = Integer.parseInt(startAndEndIndexStr);
			} else {
				String[] str1 = startAndEndIndexStr.split(",");
				startIndex = Integer.parseInt(str1[0]);
				endIndex = Integer.parseInt(str1[1]);
			}
			int len = endIndex - startIndex + 1;
			if (str == null || "".equals(str)) {
				str = "";
				for (int j = 0; j < len; j++) {
					str += " ";
				}
				return str;
			} else {
				if (str.length() < len) {
					int dif = len - str.length();
					for (int n = 0; n < dif; n++) {
						str += " ";
					}
				} else if (str.length() > len) {
					str = str.substring(0, len);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new FtpDeliveryException(
					"error happends when formating string!", e);
		}
		return str;
	}
}
