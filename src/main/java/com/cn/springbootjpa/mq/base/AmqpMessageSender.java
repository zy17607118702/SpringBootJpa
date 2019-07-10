package com.cn.springbootjpa.mq.base;

import com.cn.springbootjpa.mq.exception.MessageFormatException;
import com.cn.springbootjpa.mq.message.MessageHeader;

public interface AmqpMessageSender {

    <T> void send(String routingKey, MessageHeader headers, T message) throws MessageFormatException;

    <T> void send(String exchange, String routingKey, MessageHeader headers, T message) throws MessageFormatException;

}