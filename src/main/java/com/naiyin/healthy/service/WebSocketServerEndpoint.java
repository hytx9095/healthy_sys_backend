package com.naiyin.healthy.service;


import cn.hutool.json.JSONUtil;
import com.naiyin.healthy.config.CustomConfigurator;
import com.naiyin.healthy.enums.DoctorDialogueStatusEnum;
import com.naiyin.healthy.model.webSocket.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.URI;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket服务类
 */
@Component
@Slf4j
@ServerEndpoint(value = "/ws/chat/{userId}", configurator = CustomConfigurator.class)
public class WebSocketServerEndpoint {

    /**
     * 心跳消息
     */
    private final static String PING = "ping";

    private final static String PONG = "pong";

    /**
     * 存放每个客户端对应的 WebSocketServer 对象
     */
    private static ConcurrentHashMap<String, WebSocketServerEndpoint> webSocketMap = new ConcurrentHashMap<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 接收 userId
     */
    private String userId = "";
    private DoctorDialogueService doctorDialogueService;
    private static AutowireCapableBeanFactory beanFactory;

    @Autowired
    public void setBeanFactory(AutowireCapableBeanFactory beanFactory) {
        WebSocketServerEndpoint.beanFactory = beanFactory;
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        log.info("连接成功！");
        //获取用户id
        if (StringUtils.isEmpty(userId)) {
            userId = (String) session.getUserProperties().get("sn");
        }
        if (StringUtils.isEmpty(userId)) {
            URI requestURI = session.getRequestURI();
            log.info("请求连接未传用户id,请求路径:{}", requestURI.getPath());
            return;
        }

        this.session = session;
        this.userId = userId;
        doctorDialogueService = beanFactory.getBean(DoctorDialogueService.class);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (!webSocketMap.containsKey(userId)) {
            return;
        }

        webSocketMap.remove(userId);

        log.info("用户下线:" + userId + ", 当前在线人数为:" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        ChatMessage chatMessage = JSONUtil.toBean(message, ChatMessage.class);
        log.info("用户"+ userId + "===>>>接收到用户:{}  发送的消息:{}", chatMessage.getSender(), message);
        if (chatMessage.getType().equals("auth")){
            //获取token并校验
            String token = chatMessage.getContent();
            log.info("===>>>WebSocketServer.open token:{}", token);

            webSocketMap.put(userId, this);
            log.info("新用户上线:" + userId + ", 当前在线人数为:" + getOnlineCount());
        }
//        if (PING.equals(chatMessage.getContent())) {
//            try {
//                this.sendMessage(PONG, "ping");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        if (chatMessage.getType().equals("chat")){
            //发消息
            sendMessageToUser(chatMessage);
        }
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    private void sendMessage(ChatMessage chatMessage) throws IOException {
        log.info("===>>>向用户:{} 发送消息:{}", this.userId, chatMessage.getContent());
        String jsonStr = JSONUtil.toJsonStr(chatMessage);
        this.session.getBasicRemote().sendText(jsonStr);
    }

    /**
     * 单发消息
     */
    public void sendMessageToUser(ChatMessage chatMessage) throws IOException {
        if (webSocketMap.containsKey(chatMessage.getReceiver())) {
            webSocketMap.get(chatMessage.getReceiver()).sendMessage(chatMessage);
            this.doctorDialogueService.saveDialogue(chatMessage, DoctorDialogueStatusEnum.NORMAL.getValue());
        } else {
            log.error("请求的 userId:" + chatMessage.getReceiver() + "不在该服务器上");
            int isRead = DoctorDialogueStatusEnum.USER_UNREAD.getValue();
            if (chatMessage.getRole().equals("user")){
                isRead = DoctorDialogueStatusEnum.DOCTOR_UNREAD.getValue();
            }
            this.doctorDialogueService.saveDialogue(chatMessage, isRead);
        }
    }

    /**
     * 群发消息
     */
//    public static void sendMessageToAll(String message) throws IOException {
//        for (Map.Entry<String, WebSocketServerEndpoint> entry : webSocketMap.entrySet()) {
//            WebSocketServerEndpoint webSocketServer = entry.getValue();
//            webSocketServer.sendMessage(message, "all");
//        }
//    }



    /**
     * 在线用户
     */
    public static Set<String> getOnlineUsers() {
        Set<String> set = new HashSet<>();
        Enumeration<String> enumeration = webSocketMap.keys();

        while (enumeration.hasMoreElements()) {
            set.add(enumeration.nextElement());
        }
        return set;
    }

    /**
     * 获取在线人数
     */
    public static int getOnlineCount() {
        return webSocketMap.size();
    }

    /**
     * 用户是否在线
     */
    public static Boolean isOnline(String userId) {
        return webSocketMap.containsKey(userId);
    }

}



