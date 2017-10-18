package com.aik.dto;

/**
 * Description:
 * Created by as on 2017/10/19.
 */
public class SendVoiceRespDTO {
//    {
//        "reason": "语音验证码发送成功，请注意接听",
//            "result": {
//        "valicode": "12345678", /*验证码内容*/
//                "callSid": "1408071238531349000500010002409e", /*验证码标识*/
//                "dateCreated": "2014-08-07 12:38:53" /*发送时间*/
//    },
//        "error_code": 0 /*返回码*/
//    }

    private Integer error_code;

    private String reason;

    private SendVoiceRespDTO result;

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

    class SendVoiceResult {
        private String valicode;
        private String callSid;
        private String dateCreated;

        public String getValicode() {
            return valicode;
        }

        public void setValicode(String valicode) {
            this.valicode = valicode;
        }

        public String getCallSid() {
            return callSid;
        }

        public void setCallSid(String callSid) {
            this.callSid = callSid;
        }

        public String getDateCreated() {
            return dateCreated;
        }

        public void setDateCreated(String dateCreated) {
            this.dateCreated = dateCreated;
        }

        @Override
        public String toString() {
            return "SendVoiceResult{" +
                    "valicode='" + valicode + '\'' +
                    ", callSid='" + callSid + '\'' +
                    ", dateCreated='" + dateCreated + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SendVoiceRespDTO{" +
                "error_code=" + error_code +
                ", reason='" + reason + '\'' +
                ", result=" + result +
                '}';
    }
}
