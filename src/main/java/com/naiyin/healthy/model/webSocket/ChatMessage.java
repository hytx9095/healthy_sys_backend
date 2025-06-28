package com.naiyin.healthy.model.webSocket;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class ChatMessage {

    private String type;
    private String content;
    private String sender;
    private String receiver;
    private String role;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long doctorId;
}
