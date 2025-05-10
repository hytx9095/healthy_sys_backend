package com.naiyin.healthy.enums;

public enum HealthyKnowledgeTypeEnum {

    ELSE_HEALTHY_KNOWLEDGE(0, "其他健康知识"),
    SPORT_HEALTHY_KNOWLEDGE(1, "运动健康知识"),
    DIET_HEALTHY_KNOWLEDGE(2, "饮食健康知识"),
    ;
    private Integer code;
    private String desc;

    HealthyKnowledgeTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
