package com.gr.jiang.remoting;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gr.jiang.message.CommonMessage;

/**
 * Created by jiang on 2017/1/4.
 */
@Component
public class CommonSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    public void notify(String queueName, String className, String methodName, Object... args){
        CommonMessage message = new CommonMessage(className,methodName,args);
        rabbitTemplate.convertAndSend(queueName,message);
    }
}
