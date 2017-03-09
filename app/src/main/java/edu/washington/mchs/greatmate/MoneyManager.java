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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static edu.washington.mchs.greatmate.R.id.tabHost;

public class MoneyManager extends AppCompatActivity {
    private TabHost tabHost;
    private TableLayout tl;
    private FirebaseDatabase fdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_manager);

        createTabs();
        tl = (TableLayout)findViewById(R.id.dataTable);

        fdb = FirebaseDatabase.getInstance();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        fdb.getReference().child("users").child(uid).child("house").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String houseid = dataSnapshot.getValue(String.class);
                System.out.println(houseid);
                if (houseid != null) {
                    fdb.getReference().child("houses").child(houseid).child("transactions").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            MoneyItem gi = dataSnapshot.getValue(MoneyItem.class);
                            System.out.println(gi.getItemName());

                            for(DataSnapshot groceryItemSnapShot : dataSnapshot.getChildren()) {
                                Log.d("GroceryItemProblem", groceryItemSnapShot.getKey());
                                if(groceryItemSnapShot.getKey() != "created") {
                                    String itemName = "";
                                    String description = "";
                                    int amount = 0;

                                    for(DataSnapshot giSnap : groceryItemSnapShot.getChildren()) {
                                        String key = giSnap.getKey();
                                        if(key.equals("itemAmount")) {
                                            amount = giSnap.getValue(Integer.class);
                                        } else if(key.equals("itemDescr")) {
                                            description = giSnap.getValue(String.class);
                                        } else if(key.equals("itemName")) {
                                            itemName = giSnap.getValue(String.class);
                                        }
                                    }
                                    if(!itemName.equals("") && itemName != null){
                                        createSingleRow(tl, itemName, description, amount);
                                    }

                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    Toast.makeText(MoneyManager.this, "ERROR: No house set.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
