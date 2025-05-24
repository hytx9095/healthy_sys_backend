package com.naiyin.healthy.controller;

import com.naiyin.healthy.util.WebsocketUtil;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * websocket接口处理类
 */

@Component
@ServerEndpoint(value = "/chat/{userId}")
public class WebsocketController {

    /**
     * 连接事件，加入注解
     * @param userId
     * @param session
     */
    @OnOpen
    public void onOpen(@PathParam(value = "userId") String userId, Session session) {
        String message = "[" + userId + "]加入聊天室！！";
        // 添加到session的映射关系中
        WebsocketUtil.addSession(userId, session);
        // 广播通知，某用户上线了
        WebsocketUtil.sendMessageForAll(message);
    }

    /**
     * 连接事件，加入注解
     * 用户断开链接
     *
     * @param userId
     * @param session
     */
    @OnClose
    public void onClose(@PathParam(value = "userId") String userId, Session session) {
        String message = "[" + userId + "]退出了聊天室...";
        // 删除映射关系
        WebsocketUtil.removeSession(userId);
        // 广播通知，用户下线了
        WebsocketUtil.sendMessageForAll(message);
    }

    /**
     * 当接收到用户上传的消息
     *
     * @param userId
     * @param session
     */
    @OnMessage
    public void onMessage(@PathParam(value = "userId") String userId, Session session, String message) {
        String msg = "[" + userId + "]:" + message;
        System.out.println("接收到信息：" + msg);
        // 直接广播
        WebsocketUtil.sendMessageForAll(msg);
    }

    /**
     * 处理用户活连接异常
     *
     * @param session
     * @param throwable
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throwable.printStackTrace();
    }
}
