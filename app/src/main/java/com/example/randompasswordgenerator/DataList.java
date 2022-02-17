package com.example.randompasswordgenerator;

public class DataList {

    private String Name;
    private String Email;
    private String Password;
    private String Username;

    public DataList(String username, String name, String email, String password){
        Username = username;
        Name = name;
        Email = email;
        Password = password;
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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}



