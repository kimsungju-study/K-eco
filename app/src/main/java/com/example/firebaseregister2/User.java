package com.example.firebaseregister2;

public class User {
    private String profile;
    private String id;
    private int pw;
    private String un;

    public User(){}


    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPw() {
        return pw;
    }

    public void setPw(int pw) {
        this.pw = pw;
    }

    public String getUn() {
        return un;
    }

    public void setUn(String un) {
        this.un = un;
    }
}
