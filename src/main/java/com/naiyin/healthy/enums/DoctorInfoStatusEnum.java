package com.naiyin.healthy.enums;

/**
 * 用户角色枚举
 *

 */
public enum DoctorInfoStatusEnum {

    EXAMINE_FAILED("审核失败", 2),
    PASS("审核通过", 1),
    PENDING_EXAMINE("待审核", 0);

    private final String text;

    private final Integer value;

    DoctorInfoStatusEnum(String text, Integer value) {
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
