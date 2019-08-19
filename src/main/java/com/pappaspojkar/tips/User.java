package com.pappaspojkar.tips;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;


@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Length(max = 50)
    private String name;

    @NotNull
    @Length(max = 100)
    @Column(unique = true)
    private String email;
  
    @NotNull
    @Length(max = 100)
    private String password;
  
    @NotNull
    @Length(max = 20)
    private String phone;

    @NotNull
    @Length(max = 20)
    @Column(unique = true)
    private String nickname;
  
    @NotNull
    private Integer payStatus;
    @NotNull
    private Long lastLogin;
    @NotNull
    private String token;
    @NotNull
    private Long tokenLastValidDate;
    @NotNull
    private Integer attemptedLogins;
    @NotNull
    private Long loginDeniedUntil;

    public User() {
    }

    public User(String name, String email, String password, String phone, String nickname) {
        this.name = name;
        this.email = email;

        this.password = Utility.MD5Encode(password);

        this.phone = phone;
        this.nickname = nickname;

        this.payStatus = 99;
        this.lastLogin = 0L;
        this.token = "";
        this.tokenLastValidDate = 0L;
        this.attemptedLogins = 0;
        this.loginDeniedUntil = 0L;
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