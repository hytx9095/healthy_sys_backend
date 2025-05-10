package com.naiyin.healthy.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class MQMessageProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     * @param message
     */
    public void sendMessageToAssistant(String message) {
        log.info("发送消息：{}", message);
        rabbitTemplate.convertAndSend(MQMqConstant.HEALTHY_EXCHANGE, MQMqConstant.HEALTHY_ROUTING_KEY, message);
    }

    public void getSuggestionMessage(String message) {
        log.info("发送消息：{}", message);
        rabbitTemplate.convertAndSend(MQMqConstant.HEALTHY_EXCHANGE, MQMqConstant.SUGGESTION_ROUTING_KEY, message);
    }

    public void getRiskPredictionMessage(String message) {
        log.info("发送消息：{}", message);
        rabbitTemplate.convertAndSend(MQMqConstant.HEALTHY_EXCHANGE, MQMqConstant.RISK_PREDICTION_ROUTING_KEY, message);
    }

    public void getHealthyTagsMessage(String message) {
        log.info("发送消息：{}", message);
        rabbitTemplate.convertAndSend(MQMqConstant.HEALTHY_EXCHANGE, MQMqConstant.HEALTHY_TAGS_ROUTING_KEY, message);
    }
}
