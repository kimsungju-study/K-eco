package com.example.firebaseregister2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class UserAccount  {


    //생성자
    public UserAccount(){
    }
    //Id
    public String getIdToken() {return idToken;}

    public void setIdToken(String idToken){
        this.idToken = idToken;
    }
    private String idToken;

    public String getEmailId() {return emailId;}

    public void setEmailId(String emailId){this.emailId = emailId;}
    private String emailId;

    //password
    public String getPassword() {return password;}

    public void setPassword(String password){this.password = password;}
    private String password;


}