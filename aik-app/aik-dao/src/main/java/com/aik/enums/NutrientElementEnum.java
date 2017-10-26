package com.aik.enums;

/**
 * Description:
 * Created by as on 2017/10/26.
 */
public enum NutrientElementEnum {
    PROTEIN((byte)1, "protein", "蛋白质"),
    LIPID((byte)2, "lipid", "脂类"),
    CARBS((byte)3, "carbs", "碳水化合物"),
    VITAMIN((byte)4, "vitamin", "维他命"),
    MINERALS((byte)5, "minerals", "矿物质"),
    WATER((byte)6, "water", "水"),
    DIETARY_FIBER((byte)7, "dietaryFiber", "膳食纤维");

    private byte code;
    private String element;
    private String desc;

    NutrientElementEnum(byte code, String element, String desc) {
        this.code = code;
        this.element = element;
        this.desc = desc;
    }

    public byte getCode() {
        return code;
    }

    public String getElement() {
        return element;
    }

    public String getDesc() {
        return desc;
    }
}
