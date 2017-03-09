package edu.washington.mchs.greatmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

public class GroceryInputActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private EditText editTextName;
    private EditText editTextAmount;
    private EditText textViewDescr;
    private Button buttonSave;
    private FirebaseAuth auth;
    private String houseName;
    GroceryItem gItem;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_input);

        Firebase.setAndroidContext(this);
        buttonSave = (Button) findViewById(R.id.pBtn);
        editTextName = (EditText) findViewById(R.id.gName);
        editTextAmount = (EditText) findViewById(R.id.gAmt);
        textViewDescr = (EditText) findViewById(R.id.gDesc);
        database = FirebaseDatabase.getInstance();
    }

    public void saveGroceryItem(View view) {

        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Getting values to store
        String name = editTextName.getText().toString().trim();
        int amount = Integer.parseInt(editTextAmount.getText().toString().trim());
        String gDescr = textViewDescr.getText().toString().trim();

        //Creating Person object
        gItem = new GroceryItem(name, amount, gDescr);


        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        //database.getReference("users/" + user.getUid() + "/house")
        //.setValue(houseName.getText().toString());

        //get current users house
        mDatabase.child("users/" + user.getUid() + "/house").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                houseName = (String) dataSnapshot.getValue();
                mDatabase.child("houses/" + houseName + "/groceries/").push().setValue(gItem);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Intent intent = new Intent(GroceryInputActivity.this, GroceryManager.class);
        startActivity(intent);

    }
}
