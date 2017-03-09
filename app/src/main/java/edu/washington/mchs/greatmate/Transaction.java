package edu.washington.mchs.greatmate;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by masq on 3/8/17.
 */

@IgnoreExtraProperties
public class Transaction {

    public Money amt;
    public String description;
    public User user;

    public Transaction() { }
    public Transaction(Money amt, String desc, User user) {
        this.amt = amt;
        this.description = desc;
        this.user = user;
    }

    public Money getAmt() {
        return this.amt; // TODO
    }

    public String getDescription() {
        return this.description; // TODO
    }

    public User getUser() {
        return this.user; // TODO
    }
}
