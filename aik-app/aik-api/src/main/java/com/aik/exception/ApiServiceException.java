package com.aik.exception;

import com.aik.assist.ApiResult;
import com.aik.assist.ErrorCodeEnum;

/**
 * Description:
 * Created by as on 2017/8/5.
 */
public class ApiServiceException extends Exception {

    private ErrorCodeEnum errorCodeEnum;

    public ApiServiceException(String message) {
        super(message);
        this.errorCodeEnum = ErrorCodeEnum.ERROR_CODE_1000001;
    }

    public ApiServiceException(String message, Throwable cause) {
        super(message, cause);
        this.errorCodeEnum = ErrorCodeEnum.ERROR_CODE_1000001;
    }

    public ApiServiceException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getErrorMsg());
        this.errorCodeEnum = errorCodeEnum;
    }

    public ApiServiceException(String message, ErrorCodeEnum errorCodeEnum) {
        super(message);
        this.errorCodeEnum = errorCodeEnum;
    }

    public ApiServiceException(String message, Throwable cause, ErrorCodeEnum errorCodeEnum) {
        super(message, cause);
        this.errorCodeEnum = errorCodeEnum;
    }

    public ErrorCodeEnum getErrorCodeEnum() {
        return errorCodeEnum;
    }
}
