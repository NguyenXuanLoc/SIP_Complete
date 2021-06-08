package com.example.sip_complete.model;

public class AccountInfoModel {
    private String acc_id;
    private String registrar;
    private String domain;
    private String proxy;
    private String username;
    private String password;

    public AccountInfoModel(String acc_id, String registrar, String domain, String username, String password) {
        this.acc_id = acc_id;
        this.registrar = registrar;
        this.domain = domain;
        this.username = username;
        this.password = password;
    }

    public AccountInfoModel(String acc_id, String registrar, String domain, String proxy, String username, String password) {
        this.acc_id = acc_id;
        this.registrar = registrar;
        this.domain = domain;
        this.proxy = proxy;
        this.username = username;
        this.password = password;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getAcc_id() {
        return acc_id;
    }

    public void setAcc_id(String acc_id) {
        this.acc_id = acc_id;
    }

    public String getRegistrar() {
        return registrar;
    }

    public void setRegistrar(String registrar) {
        this.registrar = registrar;
    }

    public String getProxy() {
        return proxy;
    }

    public void setProxy(String proxy) {
        this.proxy = proxy;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AccountInfoModel{" +
                "acc_id='" + acc_id + '\'' +
                ", registrar='" + registrar + '\'' +
                ", proxy='" + proxy + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
