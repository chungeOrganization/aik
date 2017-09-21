package com.aik.enums;

/**
 * Description:
 * Created by as on 2017/8/13.
 */
public class QuestionOrderEnum {

    // 询问方式（0：匹配医生 1：公开）
    public enum QuestionOrderTypeEnum {
        MATCH_DOCTOR((byte) 0, "匹配医生"),
        OPEN((byte) 1, "公开");
        private byte code;
        private String desc;

        QuestionOrderTypeEnum(byte code, String desc) {
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

    // 订单状态（0：审核中 1：付款中 2：处理中 3：评价中 4：退款中 5：正常结束 6：失败订单）
    public enum QuestionOrderStatusEnum {
        ON_AUDIT((byte) 0, "审核中"),
        ON_PAY((byte) 1, "付款中"),
        ON_HANDLE((byte) 2, "处理中"),
        ON_EVALUATION((byte) 3, "评价中"),
        ON_REFUND((byte) 4, "退款中"),
        NORMAL_END((byte) 5, "正常结束"),
        FAIL_END((byte) 6, "失败订单");
        private byte code;
        private String desc;

        QuestionOrderStatusEnum(byte code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public byte getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

        public static byte[] getDoctorValidStatus() {
            return new byte[]{ON_HANDLE.getCode(), ON_EVALUATION.getCode(), ON_REFUND.getCode(), FAIL_END.getCode()};
        }

        public static byte[] getDoctorProcessedStatus() {
            return new byte[]{ON_EVALUATION.getCode(), ON_REFUND.getCode(), NORMAL_END.getCode()};
        }

        public static byte[] getDoctorProcessedStatusWithRefuse() {
            return new byte[]{ON_EVALUATION.getCode(), ON_REFUND.getCode(), NORMAL_END.getCode(), FAIL_END.getCode()};
        }
    }

    // 失败类型（-1：非失败订单 0：审核不通过 1：医生拒绝）
    public enum QuestionOrderFailTypeEnum {
        NOT_FAIL((byte) -1, "非失败订单"),
        AUDIT_FAIL((byte) 0, "审核不通过"),
        DOCTOR_REFUSE((byte) 1, "医生拒绝");

        private byte code;
        private String desc;

        QuestionOrderFailTypeEnum(byte code, String desc) {
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

    // 是否支付给医生（0：未支付 1：已支付）
    public enum QuestionOrderIsPayDoctorEnum {
        NOT_PAY((byte) 0, "未支付"),
        IS_PAY((byte) 1, "已支付");
        private byte code;
        private String desc;

        QuestionOrderIsPayDoctorEnum(byte code, String desc) {
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

    // 订单审核状态（-1：未审核 0：审核不通过 1：审核通过）
    public enum QuestionOrderAuditStatusEnum {
        NOT_AUDIT((byte) -1, "未审核"),
        AUDIT_NOT_PASS((byte) 0, "审核不通过"),
        AUDIT_PASS((byte) 1, "审核通过");
        private byte code;
        private String desc;

        QuestionOrderAuditStatusEnum(byte code, String desc) {
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

    // 退款审核状态（-1：未审核 0：审核不通过 1：审核通过）
    public enum QuestionOrderRefundStatusEnum {
        NOT_AUDIT((byte) -1, "未审核"),
        AUDIT_NOT_PASS((byte) 0, "审核不通过"),
        AUDIT_PASS((byte) 1, "审核通过");
        private byte code;
        private String desc;

        QuestionOrderRefundStatusEnum(byte code, String desc) {
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
