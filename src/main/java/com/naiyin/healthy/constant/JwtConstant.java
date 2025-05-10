package com.naiyin.healthy.constant;

public interface JwtConstant {
    /**
     * JWT存储的请求头
     */
    String TOKEN_HEADER = "Authorization";
    /**
     * jwt加解密使用的密钥
     */
   String SECRET = "mall-jwt-test";
    /**
     * JWT的超时时间
     */
    long EXPIRATION = 3600000000L;
    /**
     * JWT负载中拿到的开头
     */
    String TOKEN_HEAD = "Bearer";
}
