package com.aik.enums;

/**
 * Description:
 * Created by as on 2017/10/28.
 */
public enum HrRoutineBloodElemEnum {
    HB("hb", "血红蛋白(Hb)", "g/L"),
    RBC("rbc", "红细胞(RBC)", "*10^12/L"),
    WBC("wbc", "白细胞(WBC)", "*10^9/L"),
    PLT("plt", "血小板(PLT)", "*10^9/L"),
    RET("ret", "网织红细胞计数", "%"),
    BN("bn", "中性杆状核粒细胞", "%"),
    SN("sn", "中性分叶核粒细胞", "mmol/L"),
    EOS("eos", "嗜酸性粒细胞", "mg/L"),
    BASO("baso", "嗜碱性粒细胞", "mg/L"),
    LYM("lym", "淋巴细胞", "mg/L"),
    MNC("mnc", "单核细胞", "mg/L");
    private String field;
    private String elemName;
    private String unit;

    HrRoutineBloodElemEnum(String field, String elemName, String unit) {
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
