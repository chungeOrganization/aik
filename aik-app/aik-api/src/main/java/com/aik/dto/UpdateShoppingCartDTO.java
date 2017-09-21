package com.aik.dto;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/9/4.
 */
public class UpdateShoppingCartDTO {

    private List<Integer> deleteScIds;

    private List<UpdateShoppingCart> updateScList;

    public List<Integer> getDeleteScIds() {
        return deleteScIds;
    }

    public void setDeleteScIds(List<Integer> deleteScIds) {
        this.deleteScIds = deleteScIds;
    }

    public List<UpdateShoppingCart> getUpdateScList() {
        return updateScList;
    }

    public void setUpdateScList(List<UpdateShoppingCart> updateScList) {
        this.updateScList = updateScList;
    }

    public static class UpdateShoppingCart {
        private Integer scId;

        private Integer number;

        public Integer getScId() {
            return scId;
        }

        public void setScId(Integer scId) {
            this.scId = scId;
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }
    }
}
