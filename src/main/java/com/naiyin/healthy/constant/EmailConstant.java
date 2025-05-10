package com.naiyin.healthy.constant;

public interface EmailConstant {

    //接收者邮箱
    String RECEIVER_EMAIL = "3221481947@qq.com";

    //发送者邮箱
    String SENDER_EMAIL = "2167474918@qq.com";

    //邮箱授权码
    String PASSWORD = "igdohdarspjrdjjj";

    //SMTP主机名
    String HOST = "smtp.qq.com";

    //主机端口号
    String PORT = "587";

    //是否需要用户认证
    String AUTH = "true";

    //启用TlS加密
    String STARTTLS = "true";

    //邮件主题
    String SUBJECT = "healthy-sys";

    //找回密码邮件正文
    String FIND_PASSWORD_TEXT = "密码：";
}
