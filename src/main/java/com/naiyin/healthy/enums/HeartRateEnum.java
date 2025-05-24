package com.naiyin.healthy.enums;

import cn.hutool.core.util.ObjectUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户角色枚举
 *

 */
public enum HeartRateEnum {

    ONE("<1岁", 110, 150),
    ONE_TO_THREE("1-3岁", 90, 130),
    THREE_TO_SIX("3-6岁", 80, 120),
    SIX(">6岁", 60, 100);

    private final String text;

    private final Integer min;

    private final Integer max;


    HeartRateEnum(String text, Integer min, Integer max) {
        this.text = text;
        this.max = max;
        this.min = min;
    }

    public static Integer getRateMax(Integer age) {

       if (age < 1){
           return ONE.max;
       }  else if (age >= 1 && age <= 3) {
           return ONE_TO_THREE.max;
       } else if (age > 3 && age <= 6) {
           return THREE_TO_SIX.max;
       } else if (age > 6) {
           return SIX.max;
       }
        return null;
    }

    public static Integer getRateMin(Integer age) {

        if (age < 1){
            return ONE.min;
        }  else if (age >= 1 && age <= 3) {
            return ONE_TO_THREE.min;
        } else if (age > 3 && age <= 6) {
            return THREE_TO_SIX.min;
        } else if (age > 6) {
            return SIX.min;
        }
        return null;
    }

    public Integer getMin() {
        return min;
    }

    public Integer getMax() {
        return max;
    }

    public String getText() {
        return text;
    }
}
