package edu.washington.mchs.greatmate;

/**
 * Created by GanHome on 3/6/2017.
 */
public class GroceryItem {
    private String groceryname;
    private int groceryamt;
    private String grocerydesc;

    public GroceryItem(String itemName, int itemAmount, String itemDescr) {
        this.groceryname = itemName;
        this.groceryamt = itemAmount;
        this.grocerydesc = itemDescr;
    }

    public void setItemName(String itemName){
        this.groceryname = itemName;
    }

    public String getItemName(){
        return groceryname;
    }

    public void setItemAmount(int itemAmount){
        this.groceryamt = itemAmount;
    }

    public int getItemAmount(){
        return groceryamt;
    }

    public void setItemDescr(String itemDescr){
        this.grocerydesc = itemDescr;
    }

    public String getItemDescr(){
        return grocerydesc;
    }
}
