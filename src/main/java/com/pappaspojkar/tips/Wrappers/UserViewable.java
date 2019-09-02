package com.pappaspojkar.tips.Wrappers;

import com.pappaspojkar.tips.User;

public class UserViewable {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String nickname;
    private Integer payStatus;
    private Long lastLogin;
    private String token;
    private Long tokenLastValidDate;
    private Integer attemptedLogins;
    private Long loginDeniedUntil;

    public UserViewable(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = "???";
        this.phone = user.getPhone();
        this.nickname = user.getNickname();
        this.payStatus = user.getPayStatus();
        this.lastLogin = user.getLastLogin();
        this.token = user.getToken();
        this.tokenLastValidDate = user.getTokenLastValidDate();
        this.attemptedLogins = user.getAttemptedLogins();
        this.loginDeniedUntil = user.getLoginDeniedUntil();
    }

    public UserViewable() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Long getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Long lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getTokenLastValidDate() {
        return tokenLastValidDate;
    }

    public void setTokenLastValidDate(Long tokenLastValidDate) {
        this.tokenLastValidDate = tokenLastValidDate;
    }

    public Integer getAttemptedLogins() {
        return attemptedLogins;
    }

    public void setAttemptedLogins(Integer attemptedLogins) {
        this.attemptedLogins = attemptedLogins;
    }

    public Long getLoginDeniedUntil() {
        return loginDeniedUntil;
    }

    public void setLoginDeniedUntil(Long loginDeniedUntil) {
        this.loginDeniedUntil = loginDeniedUntil;
    }
}
