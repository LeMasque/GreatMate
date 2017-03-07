package edu.washington.mchs.greatmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GroceryInputActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private EditText editTextName;
    private EditText editTextAmount;
    private EditText textViewDescr;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_input);

        Firebase.setAndroidContext(this);
        buttonSave = (Button) findViewById(R.id.pBtn);
        editTextName = (EditText) findViewById(R.id.gName);
        editTextAmount = (EditText) findViewById(R.id.gAmt);
        textViewDescr = (EditText) findViewById(R.id.gDesc);
    }

    public void saveGroceryItem(View view){
        //Click Listener for button
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating firebase object
                mDatabase = FirebaseDatabase.getInstance().getReference();

                //Getting values to store
                String name = editTextName.getText().toString().trim();
                int amount = Integer.parseInt(editTextAmount.getText().toString().trim());
                String gDescr = textViewDescr.getText().toString().trim();

                //Creating Person object
                GroceryItem gItem = new GroceryItem(name, amount, gDescr);

                //Adding values
                gItem.setItemName(name);
                gItem.setItemAmount(amount);

                //Storing values to firebase
                mDatabase.child("Grocery Item").push().setValue(gItem);

                Intent intent = new Intent(GroceryInputActivity.this, GroceryManager.class);
                startActivity(intent);
            }
        });
    }
}
