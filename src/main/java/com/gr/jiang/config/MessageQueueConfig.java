package com.gr.jiang.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gr.jiang.remoting.CommonReceiver;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by jiang on 2017/1/4.
 */
@Configuration
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class MessageQueueConfig {
    //交换机
    @Setter
    @Getter
    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Setter
    @Getter
    @Value("${spring.rabbitmq.queueName}")
    private String queueName;
    /**
     * routing Key
     */
    @Setter
    @Getter
    @Value("${spring.rabbitmq.routingKey}")
    private String routingKey;

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

    /**
     * 监听工厂
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }

    /**
     * 监听容器
     *
     * @param connectionFactory
     * @param listenerAdapter
     * @return
     */
    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             CommonReceiver listenerAdapter) {

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(new String[] { this.queueName });
        container.setMessageListener(listenerAdapter);

        return container;
    }

    /**
     * 创建队列
     *
     * @return
     */
    @Bean
    public Queue messageQueue() {
        return new Queue(this.queueName);
    }

    /**
     * 管理者
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    /**
     * 消息接受者
     *
     * @return
     */
//    @Bean
//    CommonReceiver receiver() {
//        return new CommonReceiver();
//    }

    /**
     * 创建交换机
     *
     * @return
     */
    @Bean
    DirectExchange exchange() {
        return new DirectExchange(this.exchange);
    }

    /**
     * 队列和交换机通过routingKey进行绑定
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(this.routingKey);
    }


}
