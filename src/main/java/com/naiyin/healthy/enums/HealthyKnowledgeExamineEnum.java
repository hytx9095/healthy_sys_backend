package com.naiyin.healthy.enums;

/**
 * 用户角色枚举
 *

 */
public enum HealthyKnowledgeExamineEnum {

    PASS("pass", 1),
    NOT_PASS("not pass", 0);

    private final String text;

    private final Integer value;

    HealthyKnowledgeExamineEnum(String text, Integer value) {
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
