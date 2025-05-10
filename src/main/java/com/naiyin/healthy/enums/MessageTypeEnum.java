package com.naiyin.healthy.enums;

public enum MessageTypeEnum {

    ALL("全体用户", "all"),
    SINGLE("单一用户", "one");

    private final String text;

    private final String value;

    MessageTypeEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }


    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
