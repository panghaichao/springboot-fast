//package com.cctc.fast.core.rabbitmq.service;
//
//import java.util.UUID;
//
//import javax.annotation.Resource;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.rabbit.support.CorrelationData;
//import org.springframework.stereotype.Service;
//
//@Service
//public class SenderService {
//	@Resource
//    private RabbitTemplate rabbitTemplate;
//	
//	private final static Logger LOGGER = LoggerFactory.getLogger(SenderService.class);
//	
//	/**
//     * 测试广播模式.
//     *
//     * @param p the p
//     * @return the response entity
//     */
//    public void broadcast(String p) {
//        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
//        rabbitTemplate.convertAndSend("FANOUT_EXCHANGE", "", p, correlationData);
//    }
//    
//    /**
//     * 测试Direct模式.
//     *
//     * @param p the p
//     * @return the response entity
//     */
//    public void direct(String p) {
//        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
//        rabbitTemplate.convertAndSend("DIRECT_EXCHANGE", "DIRECT_ROUTING_KEY", p, correlationData);
//    }
//}
