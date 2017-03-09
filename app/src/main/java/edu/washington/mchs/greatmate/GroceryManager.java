package edu.washington.mchs.greatmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

public class  GroceryManager extends AppCompatActivity {
    private TabHost tabHost;
    private List<String> selectedRows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_manager);
        createTabs();

        TableLayout tl = (TableLayout)findViewById(R.id.groceryData);
        createSingleRow(tl, "test", 1, "desctest", "id1");
        createSingleRow(tl, "test2", 2, "desctest2", "id2");
        createSingleRow(tl, "test3", 3, "desctest3", "id3");

        selectedRows = new ArrayList<String>();
    }

    public void addGroceryItem(View view) {
        Intent intent = new Intent(GroceryManager.this, GroceryInputActivity.class);
        startActivity(intent);
    }

    // removes rows with itemId in selectRows list
    public void removeItem(View view){
        Log.i("remove", selectedRows.toString());
    }

    private void createSingleRow(TableLayout tl, String item, int quantity, String desc, String itemId){
        TableRow tr = new TableRow(this);
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rowParams.setMargins(5, 0, 5, 0);
        tr.setLayoutParams(rowParams);
        tr.setOrientation(LinearLayout.HORIZONTAL);

        TableRow.LayoutParams textParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams.weight = 1.0f;

        TextView titem = new TextView(this);
        titem.setLayoutParams(textParams);
        titem.setGravity(Gravity.BOTTOM | Gravity.LEFT);
        titem.setText(item);

        TextView tquant = new TextView(this);
        tquant.setLayoutParams(textParams);
        tquant.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        tquant.setText(Integer.toString(quantity));

        TextView tdesc = new TextView(this);
        tdesc.setLayoutParams(textParams);
        tdesc.setGravity(Gravity.RIGHT);
        tdesc.setText(desc);


        CheckBox remove = new CheckBox(this);
        textParams.weight = 0.2f;
        tquant.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        remove.setLayoutParams(textParams);
        remove.setTag(itemId);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selected = (String)view.getTag();
                if(selectedRows.contains(selected)){
                    selectedRows.remove(selected);
                }else{
                    selectedRows.add(selected);
                }
            }
        });

        tr.addView(remove);
        tr.addView(titem);
        tr.addView(tquant);
        tr.addView(tdesc);

        tl.addView(tr, new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

    }

    public void createTabs() {
        tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("mManager");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("gManager");
        TabHost.TabSpec tab3 = tabHost.newTabSpec("Settings");

        tab1.setIndicator("Money");
        tab1.setContent(R.id.tab1);

        tab2.setIndicator("Grocery");
        tab2.setContent(R.id.tab2);

        tab3.setIndicator("Settings");
        tab3.setContent(R.id.tab3);


        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);
        tabHost.setCurrentTab(1);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

                int currentTab = tabHost.getCurrentTab();
                Log.d("tabManager", "" + currentTab);
                if(currentTab == 0) {
                    startActivity(new Intent(GroceryManager.this, MoneyManager.class));
                } else if(currentTab == 1) {
                    startActivity(new Intent(GroceryManager.this, GroceryManager.class));
                } else if(currentTab == 2) {
                    startActivity(new Intent(GroceryManager.this, Settings.class));
                }
            }
        });
    }

}
