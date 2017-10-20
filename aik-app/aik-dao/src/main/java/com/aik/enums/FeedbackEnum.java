package com.aik.enums;

/**
 * Description: 问题反馈枚举
 * Created by as on 2017/10/4.
 */
public class FeedbackEnum {

    // 用户类型 0：用户 1：医生
    public static enum FbUserTypeEnum {
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
    public static enum FbTypeEnum {
        TYPE_FUNCTION((byte) 0, "功能异常", "功能故障或不能用"),
        TYPE_ADVICE((byte) 1, "产品建议", "数据错误，我有建议"),
        TYPE_OTHER((byte) 2, "其他", "");

        private byte code;
        private String typeName;
        private String typeDesc;

        FbTypeEnum(byte code, String typeName, String typeDesc) {
            this.code = code;
            this.typeName = typeName;
            this.typeDesc = typeDesc;
        }

        public byte getCode() {
            return code;
        }

        public String getTypeName() {
            return typeName;
        }

        public String getTypeDesc() {
            return typeDesc;
        }
    }
}
