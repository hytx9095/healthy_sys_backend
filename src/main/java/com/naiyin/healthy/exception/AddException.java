package com.naiyin.healthy.exception;

import cn.hutool.core.text.CharSequenceUtil;
import com.naiyin.healthy.enums.SysErrorEnum;

/**
 * 自定义新增异常
 *
 * @author ktkj
 * @since 2022.1.0
 */
public class AddException extends RuntimeException {
    /**
     * 错误码
     */
    private final int code;

    public AddException(int code, String message) {
        super(message);
        this.code = code;
    }

    public AddException(SysErrorEnum errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
    }

    public AddException(SysErrorEnum errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }
}
