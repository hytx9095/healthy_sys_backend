package com.naiyin.healthy.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 用于创建测试程序用到的交换机和队列（只用在程序启动前执行一次）
 */
public class MQInitMain {

    public static void main(String[] args) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("118.25.228.235");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            String EXCHANGE_NAME =  MQMqConstant.HEALTHY_EXCHANGE;
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");

            // 创建队列，随机分配一个队列名称
            String queueName1 = MQMqConstant.HEALTHY_QUEUE;
            String queueName2 = MQMqConstant.SUGGESTION_QUEUE;
            String queueName3 = MQMqConstant.RISK_PREDICTION_QUEUE;
            String queueName4  = MQMqConstant.HEALTHY_TAGS_QUEUE;
            channel.queueDeclare(queueName1, true, false, false, null);
            channel.queueDeclare(queueName2, true, false, false, null);
            channel.queueDeclare(queueName3, true, false, false, null);
            channel.queueDeclare(queueName4, true, false, false, null);
            channel.queueBind(queueName1, EXCHANGE_NAME,  MQMqConstant.HEALTHY_ROUTING_KEY);
            channel.queueBind(queueName2, EXCHANGE_NAME,  MQMqConstant.SUGGESTION_ROUTING_KEY);
            channel.queueBind(queueName3, EXCHANGE_NAME,  MQMqConstant.RISK_PREDICTION_ROUTING_KEY);
            channel.queueBind(queueName4, EXCHANGE_NAME,  MQMqConstant.HEALTHY_TAGS_ROUTING_KEY);
        } catch (Exception e) {

        }

    }
}
