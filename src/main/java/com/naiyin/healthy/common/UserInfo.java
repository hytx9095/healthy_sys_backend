package com.naiyin.healthy.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class UserInfo implements Serializable {

    private Long userId;
    private String username;
    private String userAvatar;
    private static final long serialVersionUID = 1L;

}
