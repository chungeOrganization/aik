package com.aik.dto.response;

/**
 * Description:
 * Created by as on 2017/10/19.
 */
public class SendSmsRespDTO {
//    {
//        "reason": "短信发送成功",
//            "result": {
//        "count": 1, /*发送数量*/
//                "fee": 1, /*扣除条数*/
//                "sid": "23d6bc4913614919a823271d820662af" /*短信ID*/
//    },
//        "error_code": 0 /*发送成功*/
//    }

    private Integer error_code;

    private String reason;

    private SendSmsResult result;

    public Integer getError_code() {
        return error_code;
    }

    public void setError_code(Integer error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public SendSmsResult getResult() {
        return result;
    }

    public void setResult(SendSmsResult result) {
        this.result = result;
    }

    public class SendSmsResult {
        private int count;
        private int fee;
        private String sid;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getFee() {
            return fee;
        }

        public void setFee(int fee) {
            this.fee = fee;
        }

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        @Override
        public String toString() {
            return "SendSmsResult{" +
                    "count=" + count +
                    ", fee=" + fee +
                    ", sid='" + sid + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SendSmsRespDTO{" +
                "error_code=" + error_code +
                ", reason='" + reason + '\'' +
                ", result=" + result +
                '}';
    }
}
