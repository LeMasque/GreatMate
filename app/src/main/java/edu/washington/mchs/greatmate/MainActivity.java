package edu.washington.mchs.greatmate;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private TabHost tabHost;
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if(user != null) {
            Log.d(TAG, user.getUid());
        } else {
            Log.d(TAG, "user not found");
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("mManager");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("gManager");
        TabHost.TabSpec tab3 = tabHost.newTabSpec("Settings");

        tab1.setIndicator("Money");
        tab1.setContent(R.id.tab1);

        tab2.setIndicator("Grocery");
        tab2.setContent(R.id.tab2);

        tab3.setIndicator("Settings");
        tab3.setContent(R.id.tab3);

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);
        tabHost.setCurrentTab(0);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

                int currentTab = tabHost.getCurrentTab();
                Log.d("tabManager", "" + currentTab);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                switch(currentTab) {
                    case 0:
                        ft.replace(R.id.placeholder, new MoneyManager());
                        break;
                    case 1:
                        ft.replace(R.id.placeholder, new GroceryManager());
                        break;
                    case 2:
                        ft.replace(R.id.placeholder, new Settings());
                        break;
                    default:
                        Log.wtf(TAG, "Unknown tab pressed");
                        break;
                }
                ft.commit();
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
