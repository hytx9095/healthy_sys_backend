package com.naiyin.healthy.enums;

/**
 * 用户角色枚举
 *

 */
public enum DietTypeEnum {

    BREAKFAST("早餐 ", 1),
    LUNCH ("午餐", 2),
    DINNER ("晚餐 ", 3),
    LATE_NIGHT_SNACK("夜宵", 4),
    OTHER("其他", 5);

    private final String text;

    private final Integer value;

    DietTypeEnum(String text, Integer value) {
        this.text = text;
        this.value = value;
    }


    public Integer getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
