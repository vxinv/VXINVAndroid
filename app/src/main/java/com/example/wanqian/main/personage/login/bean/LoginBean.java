package com.example.wanqian.main.personage.login.bean;

public class LoginBean {

    /**
     * code : 200
     * msg : 登录成功
     * data : {"account":"zhangyu","password":"888888","userName":"323323232","id":1}
     */
    /**
     * account : zhangyu
     * password : 888888
     * userName : 323323232
     * id : 1
     */

    private String account;
    private String password;
    private String userName;
    private int id;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
