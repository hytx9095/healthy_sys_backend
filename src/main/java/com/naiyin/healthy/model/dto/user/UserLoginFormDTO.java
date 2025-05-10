package com.naiyin.healthy.model.dto.user;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "登录表单实体")
public class UserLoginFormDTO {

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;

}