package com.naiyin.healthy.exception.handler;

import com.naiyin.healthy.common.R;
import com.naiyin.healthy.enums.SysErrorEnum;
import com.naiyin.healthy.exception.AddException;
import com.naiyin.healthy.exception.AuthException;
import com.naiyin.healthy.exception.CommonException;
import com.naiyin.healthy.exception.ParamException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        log.error("Exception", e);
        return R.error(SysErrorEnum.SYSTEM_ERROR.getCode(), "系统错误");
    }

    @ExceptionHandler(RuntimeException.class)
    public R<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException", e);
        return R.error(SysErrorEnum.SYSTEM_ERROR.getCode(), "系统错误");
    }

    @ExceptionHandler(AuthException.class)
    public R handleException(AuthException e) {
        return R.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(AddException.class)
    public R handleException(AddException e) {
        return R.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(ParamException.class)
    public R handleException(ParamException e) {
        return R.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(CommonException.class)
    public R handleException(CommonException e) {
        return R.error(e.getCode(), e.getMessage());
    }
}
