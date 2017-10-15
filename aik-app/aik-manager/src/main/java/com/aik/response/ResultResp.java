package com.aik.response;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/10/15.
 */
public class ResultResp {

    public static final String RESULT_SUCCESS = "0";
    public static final String RESULT_MSG_KEY = "msg";
    public static final String RESULT_SUCCESS_MSG = "操作成功。";
    public static final String RESULT_FAIL = "-1";
    public static final String RESULT_FAIL_MSG = "操作失败。";

    private String code;

    private Map<String, Object> data = new HashMap<>();

    public ResultResp() {
        this.code = RESULT_SUCCESS;
        this.data.put(RESULT_MSG_KEY, RESULT_SUCCESS_MSG);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public ResultResp withCode(String code) {
        this.code = code;
        return this;
    }


    public ResultResp withData(Map<String, Object> data) {
        this.data = data;
        return this;
    }

    public ResultResp withDataKV(String key, Object value) {
        if (null == this.data) {
            this.data = new HashMap<>();
        }

        data.put(key, value);
        return this;
    }
}
