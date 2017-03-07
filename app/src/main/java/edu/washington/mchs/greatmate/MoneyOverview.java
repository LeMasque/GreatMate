package edu.washington.mchs.greatmate;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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

    public void getuseraction(View view){
        Intent intent = new Intent(MoneyOverview.this, MoneyManager.class);
        startActivity(intent);
    }

    private void createSingleRow(TableLayout tl, String user, String total, double balance){
        TableRow tr = new TableRow(this);
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        tr.setLayoutParams(rowParams);
        tr.setPadding(5, 5, 5, 5);
        tr.setOrientation(LinearLayout.HORIZONTAL);

        TableRow.LayoutParams textParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams.weight = 1.0f;

        TextView tuser = new TextView(this);
        tuser.setLayoutParams(textParams);
        tuser.setGravity(Gravity.LEFT);
        tuser.setText(user);

        TextView ttotal = new TextView(this);
        ttotal.setLayoutParams(textParams);
        ttotal.setGravity(Gravity.CENTER);
        ttotal.setText(total);

        TextView tbal = new TextView(this);
        tbal.setLayoutParams(textParams);
        tbal.setGravity(Gravity.RIGHT);
        tbal.setText(Double.toString(balance));

        tr.addView(tuser);
        tr.addView(ttotal);
        tr.addView(tbal);

        tl.addView(tr, new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

    }
}
