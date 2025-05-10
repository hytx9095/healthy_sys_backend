package com.naiyin.healthy.enums;

public enum MessageStatusEnum {

    SCHEDULED("scheduled", 2),
    PUBLISHED("published", 1),
    DRAFT("draft", 0);

    private final String text;

    private final Integer value;

    MessageStatusEnum(String text, Integer value) {
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
