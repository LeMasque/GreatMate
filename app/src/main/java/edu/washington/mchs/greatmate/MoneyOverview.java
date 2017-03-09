package edu.washington.mchs.greatmate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mitchell on 3/6/2017.
 */

public class MoneyOverview extends Fragment {
    private MainActivity hostActivity;

    public MoneyOverview() { } // blank constructor for reasons

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        MainActivity qa = (MainActivity) activity;
        hostActivity = qa;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_money_overview, container, false);

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
                            double sum = 0.0;
                            int count = 0;
                            HashMap<String, Double> balances = new HashMap<String, Double>();
                            for (DataSnapshot transactionSnapShot : dataSnapshot.getChildren()) {
                                Transaction t = transactionSnapShot.getValue(Transaction.class);
                                sum += t.getAmt().getAmount();
                                count++;
                                String username = t.getUser().getName();
                                if (balances.containsKey(username)) {
                                    balances.put(username, balances.get(username) + t.getAmt().getAmount());
                                } else {
                                    balances.put(username, t.getAmt().getAmount());
                                }
                            }
                            double avg = sum / count;
                            for (String username : balances.keySet()) {
                                createSingleRow(tl, username, new Money(balances.get(username)), new Money(avg - balances.get(username)));
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
    public void addMoneyInput(View view) {
        Intent intent = new Intent(hostActivity, MoneyInputActivity.class);
        startActivity(intent);
    }

    public void getuseraction(View view){
        Intent intent = new Intent(hostActivity, MoneyManager.class);
        startActivity(intent);
    }

    private void createSingleRow(TableLayout tl, String user, Money total, Money balance){
        TableRow tr = new TableRow(hostActivity);
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        tr.setLayoutParams(rowParams);
        tr.setPadding(5, 5, 5, 5);
        tr.setOrientation(LinearLayout.HORIZONTAL);

        TableRow.LayoutParams textParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams.weight = 1.0f;

        TextView tuser = new TextView(hostActivity);
        tuser.setLayoutParams(textParams);
        tuser.setGravity(Gravity.LEFT);
        tuser.setText(user);

        TextView ttotal = new TextView(hostActivity);
        ttotal.setLayoutParams(textParams);
        ttotal.setGravity(Gravity.CENTER);
        ttotal.setText(total.toString());

        TextView tbal = new TextView(hostActivity);
        tbal.setLayoutParams(textParams);
        tbal.setGravity(Gravity.RIGHT);
        tbal.setText(balance.toString());

        tr.addView(tuser);
        tr.addView(ttotal);
        tr.addView(tbal);

        tl.addView(tr, new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

    }
}
