package edu.washington.mchs.greatmate;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import static edu.washington.mchs.greatmate.R.id.tabHost;

public class MoneyManager extends AppCompatActivity {
    private TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_manager);

        createTabs();
        TableLayout tl = (TableLayout)findViewById(R.id.dataTable);
        createSingleRow(tl, "Rent", "Jimmy", 100.00);
        createSingleRow(tl, "Electricity", "Hunter", 92.80);
    }

    public void addMoneyInput(View view) {
        Intent intent = new Intent(MoneyManager.this, MoneyInputActivity.class);
        startActivity(intent);
    }


    private void createSingleRow(TableLayout tl, String transaction, String user, double amt){
        TableRow tr = new TableRow(this);
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        tr.setLayoutParams(rowParams);
        tr.setPadding(5, 5, 5, 5);
        tr.setOrientation(LinearLayout.HORIZONTAL);

        TableRow.LayoutParams textParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams.weight = 1.0f;

        TextView ttrans = new TextView(this);
        ttrans.setLayoutParams(textParams);
        ttrans.setGravity(Gravity.LEFT);
        ttrans.setText(transaction);

        TextView tuser = new TextView(this);
        tuser.setLayoutParams(textParams);
        tuser.setGravity(Gravity.CENTER);
        tuser.setText(user);

        TextView tamt = new TextView(this);
        tamt.setLayoutParams(textParams);
        tamt.setGravity(Gravity.RIGHT);
        tamt.setText(Double.toString(amt));

        tr.addView(ttrans);
        tr.addView(tuser);
        tr.addView(tamt);

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

        //this tab logs person out, not sure how to do that
        tab3.setIndicator("Settings");
        tab3.setContent(R.id.tab3);


        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);
        tabHost.setCurrentTab(0);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

                int currentTab = tabHost.getCurrentTab();
                Log.d("tabManager", "" + currentTab);
                if(currentTab == 0) {
                    startActivity(new Intent(MoneyManager.this, MoneyManager.class));
                } else if(currentTab == 1) {
                    startActivity(new Intent(MoneyManager.this, GroceryManager.class));
                } else if(currentTab == 2) {
                    startActivity(new Intent(MoneyManager.this, Settings.class));
                }
            }
        });
    }

}
