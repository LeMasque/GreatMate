package edu.washington.mchs.greatmate;

/**
 * Created by GanHome on 3/8/2017.
 */
public class MoneyItem {

    private String transname;
    private int transamt;
    private String transdesc;

    public MoneyItem() {}

    public MoneyItem(String itemName, int itemAmount, String itemDescr) {
        this.transname = itemName;
        this.transamt = itemAmount;
        this.transdesc = itemDescr;
    }

    public void setItemName(String itemName){
        this.transname = itemName;
    }

    public String getItemName(){
        return transname;
    }

    public void setItemAmount(int itemAmount){
        this.transamt = itemAmount;
    }

    public int getItemAmount(){
        return transamt;
    }

    public void setItemDescr(String itemDescr){
        this.transdesc = itemDescr;
    }

    public String getItemDescr(){
        return transdesc;
    }
}
