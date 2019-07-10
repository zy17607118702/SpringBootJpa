package com.cn.springbootjpa.mq.base;

import java.util.Map;

import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import com.cn.springbootjpa.mq.message.MessageHeader;

/**
 * 基础的基于Annotation JmsListener的异步消息监听类。
 * 
 * @author LiangShenFan
 *
 * @param <T>
 */
public abstract class BaseJmsMessageListener<T> {

    /**
     * 业务处理响应方法，数据已经进行过转换。
     * 
     * @param headers
     * @param message
     * @return
     */
    protected abstract Object onInternalMessage(MessageHeader headers, T message);

    /**
     * 用于JmsListener annotation的消息响应方法，子类在重写本方法上 添加 JmsListener，并立即调用父类本方法。
     * 
     * @param headers
     * @param obj
     */
    public void onMessage(@Headers Map<String, Object> headers, @Payload T obj) {
        String msgType = (String) headers.get(MessageHeader.MESSAGE_TYPE);
        String srcSystem = (String) headers.get(MessageHeader.SRC_SYSTEM);
        String desSystem = (String) headers.get(MessageHeader.DES_SYSTEM);
        onInternalMessage(new MessageHeader(msgType, srcSystem, desSystem), obj);
    }

}
