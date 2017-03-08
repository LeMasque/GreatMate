package edu.washington.mchs.greatmate;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

/**
 * Created by masq on 3/1/17.
 */

@IgnoreExtraProperties
public class House implements Structure {

    public List<GroceryItem> groceries;
    public List<Transaction> transactions;
    public List<User> users;

    public House() { }

    public House(List<GroceryItem> groceries, List<Transaction> transactions, List<User> users) {
        this.groceries = groceries;
        this.transactions = transactions;
        this.users = users;
    }

    public List<GroceryItem> getGroceries() { return this.groceries; }
    public List<Transaction> getTransactions() { return this.transactions; }
    public List<User> getUsers() { return this.users; }

}
