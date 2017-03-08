package edu.washington.mchs.greatmate;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.ActionBar;
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

import static edu.washington.mchs.greatmate.R.id.tabHost;

public class MoneyManager extends Fragment {
    private MainActivity hostActivity;

    public MoneyManager() { } // blank constructor for reasons

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        MainActivity qa = (MainActivity) activity;
        hostActivity = qa;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_grocery_manager, container, false);

        final TableLayout tl = (TableLayout) view.findViewById(R.id.dataTable);
        final FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        fdb.getReference().child("users").child(uid).child("house").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String houseid = dataSnapshot.getValue(String.class);
                if (houseid != null) {
                    fdb.getReference().child("houses").child(houseid).child("transactions").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            Transaction transaction = dataSnapshot.getValue(Transaction.class);
//                            createSingleRow(tl, transaction.getAmt(), transaction.getDesc(), transaction.getUser());
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
//        createSingleRow(tl, "Rent", "Jimmy", 100.00);
        return view;
    }

    public void addMoneyInput(View view) {
        Intent intent = new Intent(hostActivity, MoneyInputActivity.class);
        startActivity(intent);
    }


    private void createSingleRow(TableLayout tl, Money amt, String desc, User user ) {
        TableRow tr = new TableRow(hostActivity);
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        tr.setLayoutParams(rowParams);
        tr.setPadding(5, 5, 5, 5);
        tr.setOrientation(LinearLayout.HORIZONTAL);

        TableRow.LayoutParams textParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams.weight = 1.0f;

        TextView ttrans = new TextView(hostActivity);
        ttrans.setLayoutParams(textParams);
        ttrans.setGravity(Gravity.LEFT);
        ttrans.setText(desc == null? "NULL" : desc);

        TextView tuser = new TextView(hostActivity);
        tuser.setLayoutParams(textParams);
        tuser.setGravity(Gravity.CENTER);
        tuser.setText(user == null? "NULL" : user.toString());

        TextView tamt = new TextView(hostActivity);
        tamt.setLayoutParams(textParams);
        tamt.setGravity(Gravity.RIGHT);
        tamt.setText(amt == null? "NULL" : amt.toString());

        tr.addView(ttrans);
        tr.addView(tuser);
        tr.addView(tamt);

        tl.addView(tr, new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

    }
}
