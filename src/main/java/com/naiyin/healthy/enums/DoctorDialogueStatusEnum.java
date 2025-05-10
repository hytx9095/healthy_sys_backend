package com.naiyin.healthy.enums;

public enum DoctorDialogueStatusEnum {

    NORMAL(0, "正常"),
    USER_UNREAD(1, "用户未读"),
    DOCTOR_UNREAD(2, "医生未读");


    private Integer value;
    private String desc;
    DoctorDialogueStatusEnum(Integer value, String desc){
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
