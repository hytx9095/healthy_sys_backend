package com.naiyin.healthy.model.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ExcelTarget("userVO")
public class UserVO implements Serializable {

    /**
     * id
     */
    @Excel(name = "id", orderNum = "1", width = 20)
    private Long id;
    /**
     * 用户账号
     */
    @Excel(name = "用户账号", orderNum = "2", width = 20)
    private String userAccount;

    /**
     * 用户密码
     */
//    private String userPassword;

    /**
     * 电话号码
     */
    @Excel(name = "电话号码", orderNum = "3", width = 20)
    private String telephone;

    /**
     * 用户名
     */
    @Excel(name = "用户名", orderNum = "4", width = 20)
    private String username;

    /**
     * 邮箱
     */
    @Excel(name = "邮箱", orderNum = "5", width = 20)
    private String email;

    /**
     * 用户角色
     */
    @Excel(name = "用户角色", orderNum = "6", width = 20)
    private String role;

    /**
     * 用户头像
     */
    @Excel(name = "用户头像", orderNum = "7", width = 20)
    private String userAvatar;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间", orderNum = "8", width = 20, exportFormat = "yyyy-MM-dd HH:mm:ss", importFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


}
