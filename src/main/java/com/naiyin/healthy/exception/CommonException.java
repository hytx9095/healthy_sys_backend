package com.naiyin.healthy.exception;

import cn.hutool.core.text.CharSequenceUtil;
import com.naiyin.healthy.enums.SysErrorEnum;

public class CommonException extends RuntimeException{
    /**
     * 错误码
     */
    private final int code;

    public CommonException(int code, String message) {
        super(message);
        this.code = code;
    }

    public CommonException(SysErrorEnum errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
    }

    public CommonException(SysErrorEnum errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }

}
