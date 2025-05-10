package com.naiyin.healthy.constant;

public interface RegexConstant {
    String USER_ACCOUNT = "^(?![\\s])[^\\s]{4,16}$";
    String PASSWORD = "^(?![\\s])[^\\s]{4,16}$";
    String TELEPHONE = "^1[3-9]\\d{9}$";
    String EMAIL = "^[A-Za-z0-9_.-]+@[A-Za-z0-9]+\\.[A-Za-z0-9]+$";
}
