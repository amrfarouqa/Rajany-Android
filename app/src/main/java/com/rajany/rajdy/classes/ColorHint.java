package com.rajany.rajdy.classes;

import com.google.firebase.database.IgnoreExtraProperties;

// [START blog_user_class]
@IgnoreExtraProperties
public class ColorHint {

    public String username;
    public String email;
    public String hint;
    public String date;
    public ColorHint() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public ColorHint(String username, String email, String hint, String date) {
        this.username = username;
        this.email = email;
        this.hint = hint;
        this.date = date;
    }

}