package com.rajany.rajdy.classes;


public class User {

    public String username;
    public String email;
    public String uid;
    public String photoURL;
    public String dateSignedIn;
    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email, String uid, String date, String photoURL) {
        this.username = username;
        this.email = email;
        this.uid = uid;
        this.dateSignedIn = date;
        this.photoURL = photoURL;
    }

}