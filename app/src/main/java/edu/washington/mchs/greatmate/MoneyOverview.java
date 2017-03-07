package edu.washington.mchs.greatmate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Mitchell on 3/6/2017.
 */

public class MoneyOverview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_overview);
    }

    public void addMoneyInput(View view) {
        Intent intent = new Intent(MoneyOverview.this, MoneyInputActivity.class);
        startActivity(intent);
    }

    public void getTransaction(View view){
        Intent intent = new Intent(MoneyOverview.this, MoneyManager.class);
        startActivity(intent);
    }
}
