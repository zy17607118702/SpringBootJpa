package com.cn.springbootjpa.util;

public class ValueText {
	
	private Object value;
	private String text;
	
	public ValueText() {
		super();
	}
	
	public ValueText(Object value, String text) {
		super();
		this.value = value;
		this.text = text;
	}

	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
