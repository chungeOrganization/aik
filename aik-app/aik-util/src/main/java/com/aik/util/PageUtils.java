package com.aik.util;

/**
 * Description:
 * Created by daixiangning on 2017/9/9.
 */
public class PageUtils {

    private Integer page;

    private Integer size;
    
    public PageUtils(Integer page, Integer size) {
    	this.page = page;
    	this.size = size;
	}

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
