package com.example.randompasswordgenerator;

public class DataLogin {

    private String Username;
    private String Password;

    public DataLogin(){}

    public DataLogin(String text1, String text2){
        Username = text1;
        Password = text2;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
