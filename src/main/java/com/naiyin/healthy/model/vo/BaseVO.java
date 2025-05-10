package com.naiyin.healthy.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseVO implements Serializable {

    private String username;
    private String userAvatar;
    private static final long serialVersionUID = 1L;
}
