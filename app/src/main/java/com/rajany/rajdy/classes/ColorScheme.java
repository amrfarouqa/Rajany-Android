package com.rajany.rajdy.classes;

import com.google.firebase.database.IgnoreExtraProperties;

// [START blog_user_class]
@IgnoreExtraProperties
public class ColorScheme {

    public String username;
    public String email;
    public String scheme;
    public String date;
    public ColorScheme() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public ColorScheme(String username, String email, String scheme, String date) {
        this.username = username;
        this.email = email;
        this.scheme = scheme;
        this.date = date;
    }

}