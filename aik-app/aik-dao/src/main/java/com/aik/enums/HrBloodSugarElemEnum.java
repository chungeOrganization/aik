package com.aik.enums;

/**
 * Description:
 * Created by as on 2017/10/28.
 */
public enum HrBloodSugarElemEnum {
    BLOOD_SUGAR("bloodSugar", "血糖", "mg/L"),
    TCH("tch", "胆固醇（CH）", "毫克"),
    TG("tg", "甘油三酯（CH）", "%"),
    HDL_C("hdlC", "高密度脂蛋白（HDL-c）", "mmol/L"),
    LDL_C("ldlC", "低密度脂蛋白（LDL-c）", "mg/L"),
    CR("cr", "肌酐（Cr）", "mg/L"),
    BUN("bun", "尿素氮（BUN）", "mg/L");
    private String field;
    private String elemName;
    private String unit;

    HrBloodSugarElemEnum(String field, String elemName, String unit) {
        this.field = field;
        this.elemName = elemName;
        this.unit = unit;
    }

    public String getField() {
        return field;
    }

    public String getElemName() {
        return elemName;
    }

    public String getUnit() {
        return unit;
    }
}
