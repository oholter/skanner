package com.skanner.org.skanner;

public class User {
    public static User instance;
    private String userName;

    public User() {}

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
            instance.setUserName("NO_USER"); // for testing
        }
        return instance;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String u) {
        userName = u;
    }

    public boolean isLoggedIn() {
        return userName != null;
    }

    public void logOut() {
        userName = null;
    }

    public void logIn(String u) {
        userName = u;
    }

}
