package com.example.library.domain;


import lombok.Data;

@Data
public class User {

    private String username;
    private String password;
    private String email;
    private String phone;
    private int user_id;
    private int vip;

//    private int status;

    public User(String username,String password,String email,String phone,int user_id){
//        this.status = status;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.user_id = user_id;
    }
    public User(){}

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getVip() {
        return vip;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    //    public int getStatus() {
//        return status;
//    }
//
//    public void setStatus(int status) {
//        this.status = status;
//    }
}
