package com.aik.request;

/**
 * Description:
 * Created by as on 2017/9/9.
 */
public class PageRequest {

    private Integer page=1;

    private Integer size=10;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
