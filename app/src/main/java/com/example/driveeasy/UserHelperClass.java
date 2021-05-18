package com.example.driveeasy;

public class UserHelperClass {
    String name, user, pass, phone;

    public UserHelperClass() {

    }
//, String phone
    public UserHelperClass(String name, String user, String pass) {
        this.name = name;
        this.user = user;
        this.pass = pass;
//        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }

}