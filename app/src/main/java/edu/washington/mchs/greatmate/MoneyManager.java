package edu.washington.mchs.greatmate;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MoneyManager extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_manager);

        TableLayout tl = (TableLayout)findViewById(R.id.dataTable);
        createSingleRow(tl, "Rent", "Jimmy", 100.00);
    }

    public void addMoneyInput(View view) {
        Intent intent = new Intent(MoneyManager.this, MoneyInputActivity.class);
        startActivity(intent);
    }


    private void createSingleRow(TableLayout tl, String transaction, String user, double amt){
        TableRow tr = new TableRow(this);
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        tr.setLayoutParams(rowParams);
        tr.setPadding(5, 5, 5, 5);
        tr.setOrientation(LinearLayout.HORIZONTAL);

        TableRow.LayoutParams textParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams.weight = 1.0f;

        TextView ttrans = new TextView(this);
        ttrans.setLayoutParams(textParams);
        ttrans.setGravity(Gravity.LEFT);
        ttrans.setText(transaction);

        TextView tuser = new TextView(this);
        tuser.setLayoutParams(textParams);
        tuser.setGravity(Gravity.CENTER);
        tuser.setText(user);

        TextView tamt = new TextView(this);
        tamt.setLayoutParams(textParams);
        tamt.setGravity(Gravity.RIGHT);
        tamt.setText(Double.toString(amt));

        tr.addView(ttrans);
        tr.addView(tuser);
        tr.addView(tamt);

        tl.addView(tr, new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

    }

}
