package com.example.firebaseregister2;

public class UserAccount {

    private String idToken;
    private String emailId;
    private String password;
    private String affiliation; // Added affiliation field

    private String name; // Added affiliation field



    // Default constructor
    public UserAccount() {
    }

    public void setIdToken(String idToken) {this.idToken = idToken;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
