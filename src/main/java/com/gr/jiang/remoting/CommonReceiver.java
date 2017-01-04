package com.gr.jiang.remoting;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.gr.jiang.message.CommonMessage;

/**
 * Created by jiang on 2017/1/4.
 */
@Component
public class CommonReceiver implements MessageListener{

    @Autowired
    private ApplicationContext applicationContext;

    private MessageConverter messageConverter = new Jackson2JsonMessageConverter();
    @Override
    public void onMessage(Message message) {
        CommonMessage po = (CommonMessage)messageConverter.fromMessage(message);;
        Object bean = applicationContext.getBean(po.getClassName());
        try {
            Method method = bean.getClass().getDeclaredMethod(po.getMethodName(),po.getParameterTypes());
            method.invoke(bean,po.getArgs());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
