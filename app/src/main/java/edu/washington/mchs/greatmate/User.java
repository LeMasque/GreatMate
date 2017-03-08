package edu.washington.mchs.greatmate;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by masq on 3/1/17.
 */

@IgnoreExtraProperties
public class User implements Being {

    public String name;
    public String email;
    public String house;

    public User() { }
    public User(String name, String email, String house) {
        this.name = name;
        this.email = email;
        this.house = house;
    }
}
