package edu.washington.mchs.greatmate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GroceryManager extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_manager);
    }

    public void addGroceryItem(View view) {
        Intent intent = new Intent(GroceryManager.this, GroceryInputActivity.class);
        startActivity(intent);
    }

}
