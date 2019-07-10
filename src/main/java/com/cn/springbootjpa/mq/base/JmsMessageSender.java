package com.cn.springbootjpa.mq.base;

import com.cn.springbootjpa.mq.message.MessageHeader;

public interface JmsMessageSender {
	<T> void send(String queue, MessageHeader headers, T message);
}