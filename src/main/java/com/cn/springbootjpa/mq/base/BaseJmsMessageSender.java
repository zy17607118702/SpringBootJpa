package com.cn.springbootjpa.mq.base;

import org.springframework.jms.core.JmsTemplate;

import com.cn.springbootjpa.mq.message.MessageHeader;

public class BaseJmsMessageSender implements JmsMessageSender {
	private JmsTemplate jmsTemplate;

	public BaseJmsMessageSender(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	@Override
	public <T> void send(String queue, MessageHeader headers, T message) {
		jmsTemplate.convertAndSend(queue, message);	
	}

}
