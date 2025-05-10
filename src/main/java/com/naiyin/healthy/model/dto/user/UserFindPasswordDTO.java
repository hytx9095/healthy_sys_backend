package com.naiyin.healthy.model.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserFindPasswordDTO implements Serializable {
    private String email;
}
