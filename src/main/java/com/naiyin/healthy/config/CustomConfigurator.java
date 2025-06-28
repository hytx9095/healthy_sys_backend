package com.naiyin.healthy.config;

import org.springframework.util.StringUtils;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomConfigurator extends ServerEndpointConfig.Configurator {
    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
        // 获取请求头中的 token 信息
        List<String> tokenList = request.getHeaders().get("Authorization");
        if (tokenList != null && !tokenList.isEmpty()) {
            String token = tokenList.get(0);
            config.getUserProperties().put("Authorization", token);
        }


        //先从queryParam获取用户id /ws/websocket?sn=xxxx
//        String query = request.getQueryString();
//        Map<String, String> queryParams = new HashMap<>();
//        if (query != null) {
//            for (String param : query.split("&")) {
//                String[] entry = param.split("=");
//                if (entry.length > 1) {
//                    queryParams.put(entry[0], entry[1]);
//                }
//            }
//        }
//        String userId = queryParams.get("sn");


        //再从路径取用户id /ws/webSocket/xxxxx 中的 xxxxx 作为标识（这步可以不用，WebSocketServer的open方法可以直接取)
//        if (StringUtils.isEmpty(userId)) {
//            URI requestURI = request.getRequestURI();
//            String uriStr = requestURI.getPath();
//            if (uriStr.contains("/ws/websocket")) {
//                userId = uriStr.substring(uriStr.lastIndexOf("/") + 1);
//            }
//        }

        //将用户sn放入配置类
//        config.getUserProperties().put("sn", userId);

    }
}

