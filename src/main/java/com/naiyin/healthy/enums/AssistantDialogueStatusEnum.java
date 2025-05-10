package com.naiyin.healthy.enums;

public enum AssistantDialogueStatusEnum {

    NORMAL(0, "用户"),
    LOADING(1, "小助手");

    private Integer value;
    private String desc;
    AssistantDialogueStatusEnum(Integer value, String desc){
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue(){
        return value;
    }

    public String getDesc(){
        return desc;
    }
}
