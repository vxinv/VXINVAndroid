package com.example.wanqian.bean;

import java.io.Serializable;
import java.util.List;

public class TestLocationBean implements Serializable {
    public List<TaskListBean> data;

    public List<TaskListBean> getData() {
        return data;
    }

    public void setData(List<TaskListBean> data) {
        this.data = data;
    }
}
