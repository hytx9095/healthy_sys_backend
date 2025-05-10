package com.naiyin.healthy.ai;

import io.github.briqt.spark4j.SparkClient;
import io.github.briqt.spark4j.constant.SparkApiVersion;
import io.github.briqt.spark4j.model.SparkMessage;
import io.github.briqt.spark4j.model.SparkSyncChatResponse;
import io.github.briqt.spark4j.model.request.SparkRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于对接 AI 平台
 */
@Service
@Slf4j
public class AiManager {

    @Resource
    private SparkClient sparkClient;

    /**
     * 向讯飞 AI 发送请求
     *
     * @return
     */
    public String doChat(final String content, String preContent) {
        List<SparkMessage> messages = new ArrayList<>();
        messages.add(SparkMessage.userContent(preContent + content));
        // 构造请求
        SparkRequest sparkRequest = SparkRequest.builder()
                .messages(messages)// 消息列表
                .maxTokens(2048) // 模型回答的tokens的最大长度,非必传,取值为[1,4096],默认为2048
                .temperature(0.2) // 核采样阈值。用于决定结果随机性,取值越高随机性越强即相同的问题得到的不同答案的可能性越高 非必传,取值为[0,1],默认为0.
                .apiVersion(SparkApiVersion.V3_5)// 指定请求版本 这个版本根据自己的 API 版本进行修改
                .build();
        // 同步调用
        SparkSyncChatResponse chatResponse = sparkClient.chatSync(sparkRequest);
        String responseContent = chatResponse.getContent();
        log.info("星火 AI 返回的结果 {}", responseContent);
        return responseContent;
    }

}
