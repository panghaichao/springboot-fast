//package com.cctc.fast.core.rabbitmq.config;
//
//import javax.annotation.Resource;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.Exchange;
//import org.springframework.amqp.core.ExchangeBuilder;
//import org.springframework.amqp.core.FanoutExchange;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.QueueBuilder;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
//import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
//import org.springframework.amqp.rabbit.support.CorrelationData;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RabbitMqConfig {
//	@Resource
//    private RabbitTemplate rabbitTemplate;
//	
//	private final static Logger LOGGER = LoggerFactory.getLogger(RabbitTemplate.class);
//	
//	@Bean
//    public AmqpTemplate amqpTemplate() {
//        // 使用jackson 消息转换器
//        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
//        rabbitTemplate.setEncoding("UTF-8");
//        // 消息发送失败返回到队列中，yml需要配置 publisher-returns: true
//        rabbitTemplate.setMandatory(true);
//        
//        rabbitTemplate.setReturnCallback(new ReturnCallback() {
//			
//			@Override
//			public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
//				String correlationId = message.getMessageProperties().getCorrelationIdString();
//				LOGGER.info("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}", correlationId, replyCode, replyText, exchange, routingKey);
//			}
//		});
//        
//        // 消息确认，yml需要配置 publisher-confirms: true
//        rabbitTemplate.setConfirmCallback(new ConfirmCallback() {
//			@Override
//			public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//				if (ack) {
//	            	LOGGER.info("消息发送到exchange成功,id: {}", correlationData.getId());
//	            } else {
//	            	LOGGER.info("消息发送到exchange失败,原因: {}", cause);
//	            }
//				
//			}
//		});
//        
//        return rabbitTemplate;
//    }
//	
//	
//	/**
//     * 声明Direct交换机 支持持久化.
//     *
//     * @return the exchange
//     */
//    @Bean("directExchange")
//    public Exchange directExchange() {
//        return ExchangeBuilder.directExchange("DIRECT_EXCHANGE").durable(true).build();
//    }
//    
//    /**
//     * 声明一个队列 支持持久化.
//     *
//     * @return the queue
//     */
//    @Bean("directQueue")
//    public Queue directQueue() {
//        return QueueBuilder.durable("DIRECT_QUEUE").build();
//    }
//    
//    /**
//     * 通过绑定键 将指定队列绑定到一个指定的交换机 .
//     *
//     * @param queue    the queue
//     * @param exchange the exchange
//     * @return the binding
//     */
//    @Bean
//    public Binding directBinding(@Qualifier("directQueue") Queue queue,@Qualifier("directExchange") Exchange exchange){
//        return BindingBuilder.bind(queue).to(exchange).with("DIRECT_ROUTING_KEY").noargs();
//    }
//    
//    /**
//     * 声明 fanout 交换机.
//     *
//     * @return the exchange
//     */
//    @Bean("fanoutExchange")
//    public FanoutExchange fanoutExchange() {
//        return (FanoutExchange) ExchangeBuilder.fanoutExchange("FANOUT_EXCHANGE").durable(true).build();
//    }
//    
//    
//    /**
//     * Fanout queue A.
//     *
//     * @return the queue
//     */
//    @Bean("fanoutQueueA")
//    public Queue fanoutQueueA() {
//        return QueueBuilder.durable("FANOUT_QUEUE_A").build();
//    }
//    
//    /**
//     * Fanout queue B .
//     *
//     * @return the queue
//     */
//    @Bean("fanoutQueueB")
//    public Queue fanoutQueueB() {
//        return QueueBuilder.durable("FANOUT_QUEUE_B").build();
//    }
//    
//    /**
//     * 绑定队列A 到Fanout 交换机.
//     *
//     * @param queue          the queue
//     * @param fanoutExchange the fanout exchange
//     * @return the binding
//     */
//    @Bean
//    public Binding bindingA(@Qualifier("fanoutQueueA") Queue queue,
//                            @Qualifier("fanoutExchange") FanoutExchange fanoutExchange) {
//        return BindingBuilder.bind(queue).to(fanoutExchange);
//    }
//    
//    
//    /**
//     * 绑定队列B 到Fanout 交换机.
//     *
//     * @param queue          the queue
//     * @param fanoutExchange the fanout exchange
//     * @return the binding
//     */
//    @Bean
//    public Binding bindingB(@Qualifier("fanoutQueueB") Queue queue,
//                            @Qualifier("fanoutExchange") FanoutExchange fanoutExchange) {
//        return BindingBuilder.bind(queue).to(fanoutExchange);
//    }
//}
