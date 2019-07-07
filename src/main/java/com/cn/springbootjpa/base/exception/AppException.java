
package com.cn.springbootjpa.base.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Max
 * 
 *         A exception class for dao operations.
 */
public class AppException extends RuntimeException {

	private static final long serialVersionUID = 5439915454935047935L;

	public static final String copyright = "Licensed Materials - Property of IBM " + "6949-31G "
			+ "(C) Copyright IBM Corp. 2007 All Rights Reserved. "
			+ "(C) Copyright State of New York 2002 All Rights Reserved. "
			+ "US Government Users Restricted Rights - Use, duplication or "
			+ "disclosure restricted by GSA ADP Schedule Contract with IBM Corp.";

	private String errorCode;

	/**
	 * application exception list.
	 */
	private Collection<AppException> exceptions = new ArrayList<AppException>();

	/**
	 * message key.
	 */
	private String messageKey = null;

	/**
	 * message args.
	 */
	private Object[] messageArgs = null;

	/**
	 * Constructor.
	 * 
	 * @param message,
	 *            the error message.
	 */
	public AppException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param errorCode
	 * @param message,
	 *            the error message.
	 */
	public AppException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	/**
	 * Get the error code.
	 * 
	 * @return error code.
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/* throwable */
	protected Throwable rootCause = null;

	/**
	 * application exception constructor.
	 */
	public AppException() {
		super();
	}

	/**
	 * application exception constructor.
	 * 
	 * @param rootCause
	 *            cause.
	 */
	public AppException(Throwable rootCause) {
		this.rootCause = rootCause;
	}

	/**
	 * get exceptions list.
	 * 
	 * @return exception list
	 */
	@SuppressWarnings("rawtypes")
	public Collection getCollection() {
		return exceptions;
	}

	/**
	 * 添加系统异常到异常列表中。
	 * 
	 * @param ex
	 *            系统异常
	 */
	public void addException(AppException ex) {
		exceptions.add(ex);
	}

	/**
	 * 设定消息的键值
	 * 
	 * @param key
	 *            消息键值
	 */
	public void setMessageKey(String key) {
		this.messageKey = key;
	}

	/**
	 * 获取消息的键值
	 * 
	 * @return 消息键值
	 */
	public String getMessageKey() {
		return messageKey;
	}

	/**
	 * 设定消息的变量参数值
	 * 
	 * @param args
	 *            消息的变量参数值
	 */
	public void setMessageArgs(Object[] args) {
		this.messageArgs = args;
	}

	/**
	 * 获取消息的变量参数值
	 * 
	 * @return 消息的变量参数值
	 */
	public Object[] getMessageArgs() {
		return messageArgs;
	}

	/**
	 * 设置异常的RootCause
	 * 
	 * @param anException
	 *            Root Cause异常信息
	 */
	public void setRootCause(Throwable anException) {
		rootCause = anException;
	}

	/**
	 * 获取异常的RootCause
	 * 
	 * @return Root Cause异常信息
	 */
	public Throwable getRootCause() {
		return rootCause;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Throwable#printStackTrace()
	 */
	public void printStackTrace() {
		printStackTrace(System.err);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Throwable#printStackTrace(java.io.PrintStream)
	 */
	public void printStackTrace(PrintStream outStream) {
		printStackTrace(new PrintWriter(outStream));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Throwable#printStackTrace(java.io.PrintWriter)
	 */
	public void printStackTrace(PrintWriter writer) {
		super.printStackTrace(writer);
		if (getRootCause() != null) {
			getRootCause().printStackTrace(writer);
		}
		writer.flush();
	}

}