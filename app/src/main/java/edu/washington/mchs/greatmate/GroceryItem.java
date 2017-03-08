package edu.washington.mchs.greatmate;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by GanHome on 3/6/2017.
 */

@IgnoreExtraProperties
public class GroceryItem {
    public String itemName;
    public int itemAmount;
    public String itemDescr;

    public GroceryItem() { }
    public GroceryItem(String itemName, int itemAmount, String itemDescr) {
        this.itemName = itemName;
        this.itemAmount = itemAmount;
        this.itemDescr = itemDescr;
    }

    public void setItemName(String itemName){
        this.itemName = itemName;
    }

    public String getItemName(){
        return itemName;
    }

    public void setItemAmount(int itemAmount){
        this.itemAmount = itemAmount;
    }

    public int getItemAmount(){
        return itemAmount;
    }

    public void setItemDescr(String itemDescr){
        this.itemDescr = itemDescr;
    }

    public String getItemDescr(){
        return itemDescr;
    }
}
