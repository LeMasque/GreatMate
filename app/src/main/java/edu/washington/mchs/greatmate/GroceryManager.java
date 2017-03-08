package edu.washington.mchs.greatmate;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
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

import java.util.List;

import static edu.washington.mchs.greatmate.R.id.tabHost;

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
    }

    public void addGroceryItem(View view) {
//        Intent intent = new Intent(GroceryManager.this, GroceryInputActivity.class);
//        startActivity(intent);
    }

    private void createSingleRow(TableLayout tl, String item, int quantity, String desc){
        TableRow tr = new TableRow(hostActivity);
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        tr.setLayoutParams(rowParams);
        tr.setPadding(5, 5, 5, 5);
        tr.setOrientation(LinearLayout.HORIZONTAL);

        TableRow.LayoutParams textParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams.weight = 1.0f;

        TextView titem = new TextView(hostActivity);
        titem.setLayoutParams(textParams);
        titem.setGravity(Gravity.LEFT);
        titem.setText(item);

        TextView tquant = new TextView(hostActivity);
        tquant.setLayoutParams(textParams);
        tquant.setGravity(Gravity.CENTER);
        tquant.setText("" + quantity);

        TextView tdesc = new TextView(hostActivity);
        tdesc.setLayoutParams(textParams);
        tdesc.setGravity(Gravity.RIGHT);
        tdesc.setText(desc);

        tr.addView(titem);
        tr.addView(tquant);
        tr.addView(tdesc);

        tl.addView(tr, new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

    }
}
