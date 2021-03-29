package com.example.wanqian.bean;

import com.example.wanqian.main.history.bean.HistoryBean;

import java.io.Serializable;
import java.util.List;

public class CarMessageBaseBean implements Serializable {

    private int pageNo;
    private int pages;
    private int total;
    private int pageSize;
    private List<CarMessageBean> list;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<CarMessageBean> getList() {
        return list;
    }

    public void setList(List<CarMessageBean> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "CarMessageBaseBean{" +
                "pageNo=" + pageNo +
                ", pages=" + pages +
                ", total=" + total +
                ", pageSize=" + pageSize +
                ", list=" + list +
                '}';
    }
}
