package com.aik.dto.request.user;

import com.aik.dto.request.PageReqDTO;

/**
 * Description:
 * Created by as on 2017/10/28.
 */
public class GetFoodsForDietReqDTO extends PageReqDTO {

    private Byte dietType;

    private Byte findType;

    private String foodName;

    public Byte getDietType() {
        return dietType;
    }

    public void setDietType(Byte dietType) {
        this.dietType = dietType;
    }

    public Byte getFindType() {
        return findType;
    }

    public void setFindType(Byte findType) {
        this.findType = findType;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }
}
