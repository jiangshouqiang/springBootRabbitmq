package com.gr.jiang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gr.jiang.remoting.CommonSender;

/**
 * Created by jiang on 2017/1/4.
 */
@SpringBootApplication
public class Application implements CommandLineRunner{
    @Value("${spring.rabbitmq.queueName}")
    private String queueName;
    @Autowired
    private CommonSender commonSender;

    public static void main(String [] args){
        SpringApplication.run(Application.class,args);
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        commonSender.notify(queueName,"handlePo","setName","jiang");
    }
}
