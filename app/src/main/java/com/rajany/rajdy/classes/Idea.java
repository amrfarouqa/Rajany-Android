package com.rajany.rajdy.classes;

import com.google.firebase.database.IgnoreExtraProperties;

// [START blog_user_class]
@IgnoreExtraProperties
public class Idea {

    public String username;
    public String email;
    public String idea;
    public String date;
    public Idea() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Idea(String username, String email, String idea, String date) {
        this.username = username;
        this.email = email;
        this.idea = idea;
        this.date = date;
    }

}