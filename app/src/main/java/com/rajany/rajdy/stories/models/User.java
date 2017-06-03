package com.rajany.rajdy.stories.models;

import com.google.firebase.database.IgnoreExtraProperties;

// [START blog_user_class]
@IgnoreExtraProperties
public class User {

    public String username;
    public String email;
    public String photoURL;
    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email, String photoURL) {
        this.username = username;
        this.email = email;
        this.photoURL = photoURL;
    }

}
// [END blog_user_class]
