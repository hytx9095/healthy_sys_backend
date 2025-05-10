package com.naiyin.healthy.model.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class GetUserVODTO implements Serializable {

    /**
     * 用户id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}
