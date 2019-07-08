package com.cn.springbootjpa.base.common.code;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CodeNameItemConvertor<T> {
	private List<T> list;

	public CodeNameItemConvertor(List<T> list) {
		this.list = list;
	}

	public <U> List<U> map(Function<? super T, ? extends U> converter) {
		if (list == null)
			return null;
		return list.stream().map(converter::apply).collect(Collectors.toList());
	}

}
