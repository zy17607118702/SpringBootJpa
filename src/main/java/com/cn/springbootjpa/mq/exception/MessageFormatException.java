
package com.cn.springbootjpa.mq.exception;

/**
 * 消息转换异常
 * @author zhangyang
 *
 */
public class MessageFormatException extends Exception {

	/**
	 * 序列化版本号，系统自动生成
	 */
	private static final long serialVersionUID = -1305258534229583491L;

	/**
	 * 构造方法
	 * 
	 * @param msg
	 *            异常消息
	 */
	public MessageFormatException(String msg) {
		super(msg);
	}

	/**
	 * 构造方法
	 * 
	 * @param msg
	 *            异常消息
	 * @param ex
	 *            异常源
	 */
	public MessageFormatException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
