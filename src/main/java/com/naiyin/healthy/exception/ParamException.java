package com.naiyin.healthy.exception;

import cn.hutool.core.text.CharSequenceUtil;
import com.naiyin.healthy.enums.SysErrorEnum;

public class ParamException extends RuntimeException{
    /**
     * 错误码
     */
    private final int code;

    public ParamException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ParamException(SysErrorEnum errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
    }

    public ParamException(SysErrorEnum errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }
}
