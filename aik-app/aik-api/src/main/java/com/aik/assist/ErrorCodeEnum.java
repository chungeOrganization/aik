package com.aik.assist;

/**
 * Description:
 * Created by as on 2017/8/5.
 */
public enum ErrorCodeEnum {
    // 1000*** 系统错误码
    ERROR_CODE_1000001("1000001", "系统异常"),
    ERROR_CODE_1000002("1000002", "请求参数不完整"),
    ERROR_CODE_1000003("1000003", "请求参数校验不通过"),
    ERROR_CODE_1000004("1000004", "登录已过期"),

    // 1001*** 注册模块错误码
    ERROR_CODE_1001001("1001001", "发送手机验证码失败"),
    ERROR_CODE_1001002("1001002", "验证码校验不通过"),
    ERROR_CODE_1001003("1001003", "发送邀请码失败"),
    ERROR_CODE_1001004("1001004", "邀请码校验不通过"),
    ERROR_CODE_1001005("1001005", "用户名已存在"),
    ERROR_CODE_1001006("1001006", "该手机号已被注册使用过"),
    ERROR_CODE_1001007("1001007", "用户不存在"),
    ERROR_CODE_1001008("1001008", "文件上传失败"),
    ERROR_CODE_1001009("1001009", "该手机号未被注册"),

    // 1002*** 登录模块错误码
    ERROR_CODE_1002001("1002001", "用户名或密码错误"),
    ERROR_CODE_1002002("1002002", "刷新用户token失败"),

    // 1003*** 医生端错误码
    ERROR_CODE_1003001("1003001", "未获取到患者信息"),
    ERROR_CODE_1003002("1003002", "已存在该分组"),
    ERROR_CODE_1003003("1003003", "该分组已不存在"),
    ERROR_CODE_1003004("1003004", "问题订单不存在"),
    ERROR_CODE_1003005("1003005", "原始问题不存在"),
    ERROR_CODE_1003006("1003006", "已回答过该问题"),
    ERROR_CODE_1003007("1003007", "问题订单状态不正确"),
    ERROR_CODE_1003008("1003008", "未获取到医生信息"),
    ERROR_CODE_1003009("1003009", "原始密码错误"),
    ERROR_CODE_1003010("1003010", "提现渠道不正确"),
    ERROR_CODE_1003011("1003011", "银行卡不存在"),
    ERROR_CODE_1003012("1003011", "提现金额不正确"),

    // 1004*** 用户端错误码
    ERROR_CODE_1004001("1004001", "问题订单不存在"),
    ERROR_CODE_1004002("1004002", "问题订单状态不正确"),
    ERROR_CODE_1004003("1004003", "未找到医生回答"),
    ERROR_CODE_1004004("1004004", "该回答已被追问过"),
    ERROR_CODE_1004005("1004005", "用户健康档案不存在"),
    ERROR_CODE_1004006("1004006", "食物信息不存在"),
    ERROR_CODE_1004007("1004007", "大家问，专家答问题不存在"),
    ERROR_CODE_1004008("1004008", "营养课堂不存在"),
    ERROR_CODE_1004009("1004009", "查看时间不能超过当前时间"),
    ERROR_CODE_1004010("1004010", "免费订单记录不存在"),
    ERROR_CODE_1004011("1004011", "未获取到医生信息"),

    // 1005*** aik商店错误码
    ERROR_CODE_1005001("1005001", "商品订单不存在"),
    ERROR_CODE_1005002("1005002", "商品订单状态不正确"),
    ERROR_CODE_1005003("1005003", "商品订单物流信息不存在"),
    ERROR_CODE_1005004("1005004", "商品不存在"),

    ERROR_CODE_1999999("1999999", "其他错误");

    private String errorCode;
    private String errorMsg;

    ErrorCodeEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
