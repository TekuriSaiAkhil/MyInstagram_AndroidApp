package com.example.myinstagram;

public class UserHelperClass {

    String username,phonenumber,password;

    public UserHelperClass(){
    }

    public UserHelperClass(String username, String phonenumber, String password) {
        this.username = username;
        this.phonenumber = phonenumber;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
