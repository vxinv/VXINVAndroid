package com.example.wanqian.main.current;

import java.util.List;

public class EndClearBean {
    private String imei;
    private String userAccount;
    private List<Integer> notClear;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public List<Integer> getNotClear() {
        return notClear;
    }

    public void setNotClear(List<Integer> notClear) {
        this.notClear = notClear;
    }
}
