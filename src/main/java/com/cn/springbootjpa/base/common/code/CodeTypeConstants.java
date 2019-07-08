package com.cn.springbootjpa.base.common.code;

/**
 * 常量code和编码对应关系类
 * @author zhangyang
 *
 */
public class CodeTypeConstants {
	public static final int EXPORT_MAX_NUM=65000; 
	public static final int EXPORT_MIN_NUM=0;
	//权限分类
	public static final String ROLE_ADMIN="1";
	//=====================设备分类
	//pad端
	public final static String TYPE_DEVICE = "DEVICE";
	public final static String TYPE_DEVICE_CAT = "DEVICE-CAT";
	public final static String TYPE_DEVICE_START_STR = "DEVICE";
	public final static String TYPE_DEVICE_VIEW = "DEVICE-VIW";
	//大屏端
	public final static String TYPE_SCREEN = "SCREEN";
	public final static String TYPE_SCREEN_CAT = "SCREEN-CAT";
	public final static String TYPE_SCREEN_VIW = "SCREEN-VIW";
	//富客户端
	public final static String TYPE_RICHCLIENT = "RC";
	public final static String TYPE_UI_ACTION = "ACT";
	public final static String TYPE_UI_CATEGORY = "CAT";
	public final static String TYPE_UI_VIEW = "VIW";
	//web端
	public final static String TYPE_WEB = "WEB";
	public final static String TYPE_WEB_CAT = "WEB-CAT";
	public final static String TYPE_WEB_FUNCTION = "FUNC";
	public final static String TYPE_WEB_URL = "URL";
	public final static String TYPE_SUPPLIER_WEB_FUNCTION = "FUNC";

}
