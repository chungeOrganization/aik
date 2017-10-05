package com.aik.enums;

/**
 * Description: 问题反馈枚举
 * Created by as on 2017/10/4.
 */
public class FeedbackEnum {

    // 用户类型 0：用户 1：医生
    enum FbUserTypeEnum {
        TYPE_USER((byte) 0, "用户"),
        TYPE_DOCTOR((byte) 1, "医生");
        private byte code;
        private String desc;

        FbUserTypeEnum(byte code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public byte getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }
    }

    // TODO:枚举类型修改
    enum FbTypeEnum {
        TYPE_OTHER((byte) 0, "其他");
        private byte code;
        private String desc;

        FbTypeEnum(byte code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public byte getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }
    }
}
