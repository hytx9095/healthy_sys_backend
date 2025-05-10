package com.naiyin.healthy.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DoctorContact implements Serializable {
    private Long doctorId;
    private String doctorName;
    private String doctorAvatar;
    private String lastMessage;
    private Integer unread;
    private String lastTime;
}
