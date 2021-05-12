package com.example.driveeasy;

public class UserHelperClass {
    String name, user, pass, confirmpass;
    public UserHelperClass() {

    }
    public UserHelperClass(String name, String user, String pass, String confirmpass) {
        this.name = name;
        this.user = user;
        this.pass = pass;
        this.confirmpass = confirmpass;
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

    public String getConfirmpass() {
        return confirmpass;
    }

    public void setConfirmpass(String confirmpass) {
        this.confirmpass = confirmpass;
    }
}
