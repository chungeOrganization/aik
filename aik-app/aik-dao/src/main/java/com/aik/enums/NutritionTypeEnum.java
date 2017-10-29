package com.aik.enums;

/**
 * Description:
 * Created by as on 2017/10/29.
 */
public enum NutritionTypeEnum {
    // protein：蛋白质 lipid：脂类 carbs：碳水化合物 vitamin：维他命 minerals：矿物质 water：水 dietaryFiber：膳食纤维
    protein("protein", "蛋白质"),
    lipid("lipid", "脂类"),
    carbs("carbs", "碳水化合物"),
    vitamin("vitamin", "维他命"),
    minerals("minerals", "矿物质"),
    water("water", "水"),
    dietaryFiber("dietaryFiber", "膳食纤维");
    private String type;
    private String desc;

    NutritionTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
