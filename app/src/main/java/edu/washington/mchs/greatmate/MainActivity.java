package edu.washington.mchs.greatmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button toManagerViewButton = (Button) findViewById(R.id.money_manager_button);

    }

    public void moneyManager(View view) {
        Intent intent = new Intent(MainActivity.this, MoneyManager.class);
        startActivity(intent);
    }

    public void groceryManager(View view) {
        Intent intent = new Intent(MainActivity.this, GroceryManager.class);
        startActivity(intent);
    }
}
