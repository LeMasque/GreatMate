package edu.washington.mchs.greatmate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Settings extends AppCompatActivity {
    private Button signOut;
    private EditText houseName;
    private Button enterHouse;
    private TabHost tabHost;
    private FirebaseAuth auth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        signOut = (Button) findViewById(R.id.signOutButton);
        houseName = (EditText) findViewById(R.id.house);
        enterHouse = (Button) findViewById(R.id.enterHouse);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent = new Intent(Settings.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        enterHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hn = houseName.getText().toString().trim();
                FirebaseUser user = auth.getCurrentUser();
                database.getReference("users/" + user.getUid() + "/house")
                        .setValue(hn);
                database.getReference("houses/" + hn + "/groceries/created").setValue(1);
                database.getReference("houses/" + hn + "/transactions/created").setValue(1);
                database.getReference("houses/" + hn + "/users/created").setValue(1);
            }
        });

        tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("mManager");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("gManager");
        TabHost.TabSpec tab3 = tabHost.newTabSpec("Settings");

        tab1.setIndicator("Money");
        tab1.setContent(R.id.tab1);

        tab2.setIndicator("Grocery");
        tab2.setContent(R.id.tab2);

        //this tab logs person out, not sure how to do that
        tab3.setIndicator("Settings");
        tab3.setContent(R.id.tab3);


        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);
        tabHost.setCurrentTab(2);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

                int currentTab = tabHost.getCurrentTab();
                Log.d("tabManager", "" + currentTab);
                if(currentTab == 0) {
                    startActivity(new Intent(Settings.this, MoneyManager.class));
                } else if(currentTab == 1) {
                    startActivity(new Intent(Settings.this, GroceryManager.class));
                } else if(currentTab == 2) {
                    startActivity(new Intent(Settings.this, Settings.class));
                }
            }
        });
    }
}
