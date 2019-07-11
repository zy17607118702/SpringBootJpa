package com.cn.springbootjpa.mq.util;
/**
 * 消息通用工具类
 * @author zhangyang
 *
 */
public class MessageUtil {
	
	/**
	 * 
	 * 检查接口的序列号是否连续
	 * 
	 * @param seqID
	 * 
	 * @param reqID
	 * 
	 * @return
	 */
	public boolean checkSeqIDAndLastReqID(String seqID, Integer reqID) {
		// 第一次接受不需要比较连续性。
		if (null == reqID)
			return true;
		// 如果没有SeqID则不进行检查
		if (seqID == null || "".equals(seqID)) {
			return true;
		}
		// 特定借口不需要Seq检查
		if ("-1".equals(seqID))
			return true;
		try {
			Integer seqIDLong = Integer.valueOf(seqID);
			if (seqIDLong != reqID + 1) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	

}
