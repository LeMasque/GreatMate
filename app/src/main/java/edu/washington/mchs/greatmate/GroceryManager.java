package edu.washington.mchs.greatmate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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

    private void createSingleRow(TableLayout tl, String item, int quantity, String desc){
        TableRow tr = new TableRow(this);
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        tr.setLayoutParams(rowParams);
        tr.setPadding(5, 5, 5, 5);
        tr.setOrientation(LinearLayout.HORIZONTAL);

        TableRow.LayoutParams textParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams.weight = 1.0f;

        TextView titem = new TextView(this);
        titem.setLayoutParams(textParams);
        titem.setGravity(Gravity.LEFT);
        titem.setText(item);

        TextView tquant = new TextView(this);
        tquant.setLayoutParams(textParams);
        tquant.setGravity(Gravity.CENTER);
        tquant.setText(quantity);

        TextView tdesc = new TextView(this);
        tdesc.setLayoutParams(textParams);
        tdesc.setGravity(Gravity.RIGHT);
        tdesc.setText(desc);

        tr.addView(titem);
        tr.addView(tquant);
        tr.addView(tdesc);

        tl.addView(tr, new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

    }

}
