package com.shiroSpringboot.rabbitmq;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 生产者
 * @author 
 *
 */

@Component
public class SendMsg implements RabbitTemplate.ConfirmCallback{

	private static final Logger log = LoggerFactory.getLogger(SendMsg.class);
   
	@Autowired
    private RabbitTemplate rabbitTemplatenew;
    public void send() {
        
        rabbitTemplatenew.setConfirmCallback(this);
        String msg="callbackSender : i am callback sender";
        System.out.println(msg );
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());  
        System.out.println("callbackSender UUID: " + correlationData.getId());  
        this.rabbitTemplatenew.convertAndSend("exchange", "topic.messages", msg, correlationData);  
    }
	
	
	
	/**
	 * 发送回调
	 */
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		 
		log.info("confirm->"+correlationData.getId()+"--ack->"+ack+"--cause->"+cause);
		
	}

}
