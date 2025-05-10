package com.naiyin.healthy.enums;

import java.util.Arrays;

public enum DoctorSpecialtyEnum {
    // 内科系统
    CARDIOLOGY("cardiology", "心血管内科"),
    RESPIRATORY("respiratory", "呼吸内科"),
    GASTROENTEROLOGY("gastroenterology", "消化内科"),
    ENDOCRINOLOGY("endocrinology", "内分泌科"),
    NEUROLOGY("neurology", "神经内科"),

    // 外科系统
    GENERAL_SURGERY("general_surgery", "普通外科"),
    ORTHOPEDICS("orthopedics", "骨科"),
    NEUROSURGERY("neurosurgery", "神经外科"),
    UROLOGY("urology", "泌尿外科"),

    // 其他科室
    OPHTHALMOLOGY("ophthalmology", "眼科"),
    ENT("ent", "耳鼻喉科"),
    PEDIATRICS("pediatrics", "儿科"),
    OBSTETRICS("obstetrics", "产科"),
    GYNECOLOGY("gynecology", "妇科"),


    // 特殊类型
    TRADITIONAL_CHINESE("traditional_chinese", "中医"),
    EMERGENCY("emergency", "急诊科"),
    PATHOLOGY("pathology", "病理科"),
    ANESTHESIOLOGY("anesthesiology", "麻醉科");

    private final String specialtyCode;
    private final String specialtyName;

    DoctorSpecialtyEnum(String specialtyCode, String specialtyName) {
        this.specialtyCode = specialtyCode;
        this.specialtyName = specialtyName;
    }

    public String getSpecialtyCode() {
        return specialtyCode;
    }

    public String getSpecialtyName() {
        return specialtyName;
    }

    // 按科室大类分组
    public String getCategory() {
        if (name().contains("_MEDICINE") ||
                Arrays.asList(CARDIOLOGY, RESPIRATORY, GASTROENTEROLOGY, ENDOCRINOLOGY, NEUROLOGY).contains(this)) {
            return "内科系统";
        } else if (name().contains("_SURGERY") ||
                Arrays.asList(GENERAL_SURGERY, ORTHOPEDICS, NEUROSURGERY, UROLOGY).contains(this)) {
            return "外科系统";
        } else {
            return "其他科室";
        }
    }

    // 通过code获取枚举
    public static DoctorSpecialtyEnum fromCode(String code) {
        return Arrays.stream(values())
                .filter(e -> e.specialtyCode.equals(code))
                .findFirst()
                .orElse(null);
    }
}
