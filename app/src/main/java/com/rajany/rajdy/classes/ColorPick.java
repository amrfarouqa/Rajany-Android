package com.rajany.rajdy.classes;

import com.google.firebase.database.IgnoreExtraProperties;

// [START blog_user_class]
@IgnoreExtraProperties
public class ColorPick {

    public String username;
    public String email;
    public String color;
    public String date;
    public ColorPick() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public ColorPick(String username, String email, String color, String date) {
        this.username = username;
        this.email = email;
        this.color = color;
        this.date = date;
    }

}