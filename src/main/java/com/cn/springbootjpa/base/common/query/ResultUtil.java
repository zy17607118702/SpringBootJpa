package com.cn.springbootjpa.base.common.query;

/**
 * 进行web结果的包装方法
 * 主要提供成功时的方法success和错误时的error方法
 * @author find
 *
 */
public class ResultUtil {
	
	private ResultUtil() {
		
	}
	
	public static <T> Result<T> success(T data){
		Result<T> result = new Result<>();
		result.setCode(ResultCodeEnum.SUCCESS.getCode());
		result.setMsg(ResultCodeEnum.SUCCESS.getMsg());
		result.setData(data);
		
		return result;
	}
	
	public static <T> Result<T> error(IResultCode result) {
		
		return error(result.getCode(), result.getMsg());
	}
	
	public static <T> Result<T> error(Integer code, String msg){
		Result<T> res = new Result<>();
		res.setCode(code);
		res.setMsg(msg);
		
		return res;
	}
}
