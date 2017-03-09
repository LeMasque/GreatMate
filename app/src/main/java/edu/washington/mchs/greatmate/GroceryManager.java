package edu.washington.mchs.greatmate;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
<<<<<<< HEAD
=======
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
>>>>>>> spencer
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
<<<<<<< HEAD
import android.widget.CheckBox;
=======
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
>>>>>>> spencer

import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
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
=======
public class  GroceryManager extends Fragment {

    private MainActivity hostActivity;

    public GroceryManager() { } // blank constructor for reasons

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        MainActivity qa = (MainActivity) activity;
        hostActivity = qa;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_grocery_manager, container, false);
        final TableLayout tl = (TableLayout) view.findViewById(R.id.grocerymanager_table);
        final FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        fdb.getReference().child("users").child(uid).child("house").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String houseid = dataSnapshot.getValue(String.class);
                if (houseid != null) {
                    fdb.getReference().child("houses").child(houseid).child("groceries").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot groceryItemSnapShot : dataSnapshot.getChildren()) {
                                GroceryItem gi = groceryItemSnapShot.getValue(GroceryItem.class);
                                createSingleRow(tl, gi.getItemName(), gi.getItemAmount(), gi.getItemDescr());
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    Toast.makeText(hostActivity, "ERROR: No house set.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
>>>>>>> spencer
    }

    public void addGroceryItem(View view) {
//        Intent intent = new Intent(GroceryManager.this, GroceryInputActivity.class);
//        startActivity(intent);
    }

<<<<<<< HEAD
    // removes rows with itemId in selectRows list
    public void removeItem(View view){
        Log.i("remove", selectedRows.toString());
    }

    private void createSingleRow(TableLayout tl, String item, int quantity, String desc, String itemId){
        TableRow tr = new TableRow(this);
=======
    private void createSingleRow(TableLayout tl, String item, int quantity, String desc){
        TableRow tr = new TableRow(hostActivity);
>>>>>>> spencer
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rowParams.setMargins(5, 0, 5, 0);
        tr.setLayoutParams(rowParams);
        tr.setOrientation(LinearLayout.HORIZONTAL);

        TableRow.LayoutParams textParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams.weight = 1.0f;

        TextView titem = new TextView(hostActivity);
        titem.setLayoutParams(textParams);
        titem.setGravity(Gravity.BOTTOM | Gravity.LEFT);
        titem.setText(item);

        TextView tquant = new TextView(hostActivity);
        tquant.setLayoutParams(textParams);
<<<<<<< HEAD
        tquant.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        tquant.setText(Integer.toString(quantity));
=======
        tquant.setGravity(Gravity.CENTER);
        tquant.setText("" + quantity);
>>>>>>> spencer

        TextView tdesc = new TextView(hostActivity);
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
<<<<<<< HEAD

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

=======
>>>>>>> spencer
}
