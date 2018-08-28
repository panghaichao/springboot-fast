//package com.cctc.fast.core.rabbitmq.listener;
//
//import java.io.IOException;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//import com.rabbitmq.client.Channel;
//
///**
// * 
// * 消息监听器
// * @author Hejeo
// *
// */
//@Component
//public class Receiver {
//	private final static Logger LOGGER = LoggerFactory.getLogger(Receiver.class);
//	
//	/**
//     * FANOUT广播队列监听一.
//     *
//     * @param message the message
//     * @param channel the channel
//     * @throws IOException the io exception  这里异常需要处理
//     */
//    @RabbitListener(queues = {"FANOUT_QUEUE_A"})
//    public void on(Message message, Channel channel) throws IOException {
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
//        LOGGER.info("FANOUT_QUEUE_A " + new String(message.getBody()));
//    }
//    
//    
//    /**
//     * FANOUT广播队列监听二.
//     *
//     * @param message the message
//     * @param channel the channel
//     * @throws IOException the io exception   这里异常需要处理
//     */
//    @RabbitListener(queues = {"FANOUT_QUEUE_B"})
//    public void t(Message message, Channel channel) throws IOException {
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
//        LOGGER.info("FANOUT_QUEUE_B " + new String(message.getBody()));
//    }
//    
//    
//    /**
//     * DIRECT模式.
//     *
//     * @param message the message
//     * @param channel the channel
//     * @throws IOException the io exception  这里异常需要处理
//     */
//    @RabbitListener(queues = {"DIRECT_QUEUE"})
//    public void message(Message message, Channel channel) throws IOException {
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
//        LOGGER.info("DIRECT " + new String(message.getBody()));
//    }
//}
