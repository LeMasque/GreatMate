package edu.washington.mchs.greatmate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MoneyInputActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private EditText editTextName;
    private EditText editTextAmount;
    private EditText textViewDescr;
    private Button buttonSave;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private FirebaseUser user;
    private String name;
    private int amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_input);

        Firebase.setAndroidContext(this);
        buttonSave = (Button) findViewById(R.id.pBtn);
        editTextName = (EditText) findViewById(R.id.pName);
        editTextAmount = (EditText) findViewById(R.id.pAmt);
        textViewDescr = (EditText) findViewById(R.id.pDesc);
        database = FirebaseDatabase.getInstance();
    }

    public void saveMoneyItem(View view) {
        //Creating firebase object
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Getting values to store
        name = editTextName.getText().toString().trim();
        amount = Integer.parseInt(editTextAmount.getText().toString().trim());
        String gDescr = textViewDescr.getText().toString().trim();
        gDescr = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //Creating Person object
        final MoneyItem mItem = new MoneyItem(name, amount, gDescr);


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        //database.getReference("users/" + user.getUid() + "/house")
        //.setValue(houseName.getText().toString());

        mDatabase.child("users/" + user.getUid() + "/house").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String houseName = (String) dataSnapshot.getValue();
                mDatabase.child("users/" + user.getUid() + "/name").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String userName = (String) dataSnapshot.getValue();
                        final MoneyItem mItem = new MoneyItem(name, amount, userName);
                        mDatabase.child("houses/" + houseName + "/transactions/").push().setValue(mItem);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Intent intent = new Intent(MoneyInputActivity.this, MoneyManager.class);
        startActivity(intent);

    }
}
