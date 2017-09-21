package com.aik.assist;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/8/5.
 */
public class ApiResult {
    private static final String RESULT_SUCCESS = "1";
    private static final String RESULT_SUCCESS_MSG = "操作成功。";
    private static final String RESULT_FAIL = "-1";
    private static final String RESULT_FAIL_MSG = "操作失败。";

    public ApiResult() {
        this.result = RESULT_SUCCESS;
        this.msg = RESULT_SUCCESS_MSG;
    }

    private String result;

    private String msg;

    private Map<Object, Object> data = new HashMap<>();

    private String errorCode = "";

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public ApiResult withSuccessResult() {
        this.result = RESULT_SUCCESS;
        this.msg = RESULT_SUCCESS_MSG;
        return this;
    }

    public ApiResult withFailResult(ErrorCodeEnum errorCodeEnum) {
        this.result = RESULT_FAIL;
        this.msg = RESULT_FAIL_MSG;
        if (null != errorCodeEnum) {
            this.msg = errorCodeEnum.getErrorMsg();
            this.errorCode = errorCodeEnum.getErrorCode();
        }
        return this;
    }

    public ApiResult withMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public ApiResult withData(Map data) {
        this.data = data;
        return this;
    }

    public ApiResult withDataKV(String key, Object value) {
        data.put(key, value);
        return this;
    }

    public ApiResult withErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }
}
