package com.naiyin.healthy.enums;

public enum AssistantDialogueEnum {

    USER("user", "用户"),
    ASSISTANT("assistant", "小助手");

    private String value;
    private String desc;
    AssistantDialogueEnum(String code, String desc){
        this.value = code;
        this.desc = desc;
    }

    public String getValue(){
        return value;
    }

    public String getDesc(){
        return desc;
    }
}
