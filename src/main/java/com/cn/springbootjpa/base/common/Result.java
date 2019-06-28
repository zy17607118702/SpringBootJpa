package com.cn.springbootjpa.base.common;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

@Data
public class Result {
	public interface View {
	}

	public static Result error(int error) {
		return new Result(error);
	}

	public static Result ok() {
		return new Result(0);
	}

	@JsonView(View.class)
	private Object data;
	/**
	 * Error错误代码，0时为成功
	 */
	@JsonView(View.class)
	protected int error = 0;
	/**
	 * 消息，成功时为成功消息，错误时为错误消息
	 */
	@JsonView(View.class)
	protected String message;

	public Result() {
		super();
	}

	public Result(Integer error) {
		this.error = error;
	}

	public Result(Integer error, String message) {
		this.error = error;
		this.message = message;
	}

	public Result withData(Object data) {
		this.data = data;
		return this;
	}

	public Result withMessage(String message) {
		this.setMessage(message);
		return this;
	}
}