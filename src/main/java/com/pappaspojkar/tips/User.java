package com.pappaspojkar.tips;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String name;
    @Column(unique = true)
    @Length(max=100)
    private String email;
    private String password;
    private String phone;
    @Column(unique = true)
    @Length(max=100)
    private String nickname;
    private Integer loginAttempt;
    private Integer payStatus;
    private Long lastLogin;
    private String token;
    private Long tokenLastDate;

    public User() {
    }

    public User( String name, String email, String password, String phone, String nickname) {
        
        this.name = name;
        this.email = email;
        this.password = Utility.encodeMD5(password);
        this.phone = phone;
        this.nickname = nickname;
        this.payStatus = 99;
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

    public Long getTokenLastDate() {
        return tokenLastDate;
    }

    public void setTokenLastDate(Long tokenLastDate) {
        this.tokenLastDate = tokenLastDate;
    }
    
    


}