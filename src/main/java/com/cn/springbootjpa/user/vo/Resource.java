package com.cn.springbootjpa.user.vo;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Resource implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final static String TYPE_DEVICE = "DEVICE";

	public final static String TYPE_DEVICE_CAT = "DEVICE-CAT";

	public final static String TYPE_DEVICE_START_STR = "DEVICE";

	public final static String TYPE_DEVICE_VIEW = "DEVICE-VIW";
	
	/**
	 * 增加大屏菜单配置
	 */
	public final static String TYPE_SCREEN = "SCREEN";
	public final static String TYPE_SCREEN_CAT = "SCREEN-CAT";
	public final static String TYPE_SCREEN_VIW = "SCREEN-VIW";

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

	private Integer id;
	private String resType;
	private String resNameC;
	private String resNameE;
	private String resPath;
	private Integer resLevel;
	private Integer seqNo;
	private Boolean isLeaf;
	//父节点
	private Resource parentResource;
	//子节点
	private List<Resource> resources = null;

	@Override
	public String toString() {
		return "Resource [id=" + id + ", resType=" + resType + ", resNameC=" + resNameC + ", resNameE=" + resNameE
				+ ", resPath=" + resPath + ", resLevel=" + resLevel + ", seqNo=" + seqNo + "]";
	}

}
