package com.naiyin.healthy.enums;

public enum DoctorDialogueEnum {

    USER("user", "用户"),
    Doctor("doctor", "医生");

    private String value;
    private String desc;
    DoctorDialogueEnum(String value, String desc){
        this.value = value;
        this.desc = desc;
    }

    public String getValue(){
        return value;
    }

    public String getDesc(){
        return desc;
    }
}
