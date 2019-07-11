package com.cn.springbootjpa.mq.base;

import com.cn.springbootjpa.mq.exception.MessageFormatException;
import com.cn.springbootjpa.mq.message.MessageHeader;

public interface AmqpMessageSender {
	//不绑定交换机的mq队列
    <T> void send(String routingKey, MessageHeader headers, T message) throws MessageFormatException;
    //绑定交换机的mq队列
    <T> void send(String exchange, String routingKey, MessageHeader headers, T message) throws MessageFormatException;

}