package com.example.wanqian.bean;

import java.io.Serializable;
import java.util.List;

public class testBean implements Serializable {
    List<String> devIds;

    public List<String> getData() {
        return devIds;
    }

    public void setData(List<String> data) {
        this.devIds = data;
    }
}
