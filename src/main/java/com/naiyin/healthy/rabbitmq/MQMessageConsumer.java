package com.naiyin.healthy.rabbitmq;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.naiyin.healthy.ai.AiManager;
import com.naiyin.healthy.constant.AiPreContentConstant;
import com.naiyin.healthy.enums.AssistantDialogueStatusEnum;
import com.naiyin.healthy.enums.SysErrorEnum;
import com.naiyin.healthy.exception.CommonException;
import com.naiyin.healthy.model.dto.RiskPrediction.RiskPredictionDTO;
import com.naiyin.healthy.model.dto.suggestions.SuggestionDTO;
import com.naiyin.healthy.model.entity.*;
import com.naiyin.healthy.service.AssistantDialogueService;
import com.naiyin.healthy.service.RiskPredictionService;
import com.naiyin.healthy.service.SuggestionService;
import com.naiyin.healthy.service.UserTagsService;
import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import java.util.Date;

@Component
@Slf4j
public class MQMessageConsumer {

    @Resource
    private AiManager aiManager;

    @Resource
    private AssistantDialogueService assistantDialogueService;

    @Resource
    private SuggestionService suggestionService;

    @Resource
    private RiskPredictionService riskPredictionService;

    @Resource
    private UserTagsService userTagsService;

    // 指定程序监听的消息队列和确认机制
    @SneakyThrows
    @RabbitListener(queues = {MQMqConstant.HEALTHY_QUEUE}, ackMode = "MANUAL")
    public void receiveAssistantMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        log.info("receiveMessage message = {}", message);
        if (StrUtil.isBlank(message)) {
            // 如果失败，消息拒绝
            channel.basicNack(deliveryTag, false, false);
            throw new CommonException(SysErrorEnum.SYSTEM_ERROR, "消息为空");
        }

        String[] ids = message.split(":");
        long userDialogueId = Long.parseLong(ids[0]);
        long assistantDialogueId = Long.parseLong(ids[1]);
        AssistantDialogue userDialogue = assistantDialogueService.getById(userDialogueId);
        if (userDialogue == null) {
            channel.basicNack(deliveryTag, false, false);
            throw new CommonException(SysErrorEnum.NOT_FOUND_ERROR, "对话为空");
        }
        // 调用 AI
        String result = aiManager.doChat(userDialogue.getContent(), AiPreContentConstant.HEALTH_ASSISTANT);
        if (StrUtil.isBlank(result)){
            channel.basicNack(deliveryTag, false, false);
            throw new CommonException(SysErrorEnum.SYSTEM_ERROR, "AI生成失败");
        }

        AssistantDialogue answerDialogue = assistantDialogueService.getById(assistantDialogueId);
        answerDialogue.setContent(result);
        answerDialogue.setStatus(AssistantDialogueStatusEnum.NORMAL.getValue());
        answerDialogue.setOperationTime(new Date());
        boolean save = assistantDialogueService.updateById(answerDialogue);
        if (!save){
            channel.basicNack(deliveryTag, false, false);
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "对话添加失败");
        }
        // 消息确认
        channel.basicAck(deliveryTag, false);
    }

    @SneakyThrows
    @RabbitListener(queues = {MQMqConstant.SUGGESTION_QUEUE}, ackMode = "MANUAL")
    public void receiveSuggestionMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        log.info("receiveMessage message = {}", message);
        if (StrUtil.isBlank(message)) {
            // 如果失败，消息拒绝
            channel.basicNack(deliveryTag, false, false);
            throw new CommonException(SysErrorEnum.SYSTEM_ERROR, "消息为空");
        }

        UserAllHealthyInfo userAllHealthyInfo = JSONUtil.toBean(message, UserAllHealthyInfo.class);
        // 调用 AI
        String result = aiManager.doChat(message, AiPreContentConstant.INTELLIGENT_SUGGESTION);
        if (StrUtil.isBlank(result)){
            channel.basicNack(deliveryTag, false, false);
            throw new CommonException(SysErrorEnum.SYSTEM_ERROR, "AI生成失败");
        }
        String suggestions = result.replaceAll("```json", "").replaceAll("```", "");
        SuggestionMessage suggestionMessage = JSONUtil.toBean(suggestions, SuggestionMessage.class);
        for (SuggestionDTO suggestionDTO : suggestionMessage.getSuggestionDTOList()) {
            Suggestion suggestion = BeanUtil.copyProperties(suggestionDTO, Suggestion.class);
            suggestion.setUserId(userAllHealthyInfo.getUserId());
            boolean save = suggestionService.save(suggestion);
            if (!save){
                channel.basicNack(deliveryTag, false, false);
                throw new CommonException(SysErrorEnum.OPERATION_ERROR, "建议添加失败");
            }
        }
        // 消息确认
        channel.basicAck(deliveryTag, false);
    }

    @SneakyThrows
    @RabbitListener(queues = {MQMqConstant.RISK_PREDICTION_QUEUE}, ackMode = "MANUAL")
    public void receiveRiskPredictionMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        log.info("receiveMessage message = {}", message);
        if (StrUtil.isBlank(message)) {
            // 如果失败，消息拒绝
            channel.basicNack(deliveryTag, false, false);
            throw new CommonException(SysErrorEnum.SYSTEM_ERROR, "消息为空");
        }

        UserAllHealthyInfo userAllHealthyInfo = JSONUtil.toBean(message, UserAllHealthyInfo.class);
        // 调用 AI
        String result = aiManager.doChat(message, AiPreContentConstant.RISK_PREDICTION);
        if (StrUtil.isBlank(result)){
            channel.basicNack(deliveryTag, false, false);
            throw new CommonException(SysErrorEnum.SYSTEM_ERROR, "AI生成失败");
        }
        String riskPredictions = result.replaceAll("```json", "").replaceAll("```", "");
        RiskPredictionMessage riskPredictionMessage = JSONUtil.toBean(riskPredictions, RiskPredictionMessage.class);
        for (RiskPredictionDTO riskPredictionDTO : riskPredictionMessage.getRiskPredictionDTOList()) {
            RiskPrediction riskPrediction = BeanUtil.copyProperties(riskPredictionDTO, RiskPrediction.class);
            riskPrediction.setUserId(userAllHealthyInfo.getUserId());
            boolean save = riskPredictionService.save(riskPrediction);
            if (!save){
                channel.basicNack(deliveryTag, false, false);
                throw new CommonException(SysErrorEnum.OPERATION_ERROR, "风险添加失败");
            }
        }
        // 消息确认
        channel.basicAck(deliveryTag, false);
    }

    @SneakyThrows
    @RabbitListener(queues = {MQMqConstant.HEALTHY_TAGS_QUEUE}, ackMode = "MANUAL")
    public void receiveHealthyTagsMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        log.info("receiveMessage message = {}", message);
        if (StrUtil.isBlank(message)) {
            // 如果失败，消息拒绝
            channel.basicNack(deliveryTag, false, false);
            throw new CommonException(SysErrorEnum.SYSTEM_ERROR, "消息为空");
        }

        UserAllHealthyInfo userAllHealthyInfo = JSONUtil.toBean(message, UserAllHealthyInfo.class);
        // 调用 AI
        String result = aiManager.doChat(message, AiPreContentConstant.HEALTHY_TAGS);
        if (StrUtil.isBlank(result)){
            channel.basicNack(deliveryTag, false, false);
            throw new CommonException(SysErrorEnum.SYSTEM_ERROR, "AI生成失败");
        }

        String userTags = result.replaceAll("```json", "").replaceAll("```", "");
        UserTagsMessage userTagsMessage = JSONUtil.toBean(userTags, UserTagsMessage.class);
        UserTags saveUserTags = new UserTags();
        saveUserTags.setUserId(userAllHealthyInfo.getUserId());
        saveUserTags.setTags(JSONUtil.toJsonStr(userTagsMessage.getTags()));
        boolean save = userTagsService.save(saveUserTags);
        if (!save){
            channel.basicNack(deliveryTag, false, false);
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "标签添加失败");
        }
        // 消息确认
        channel.basicAck(deliveryTag, false);
    }
}
