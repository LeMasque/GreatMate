package edu.washington.mchs.greatmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
 //       final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
 //       if (user == null) {
  //          // user auth state is changed - user is null
  //          // launch login activity
  //          startActivity(new Intent(MainActivity.this, LoginActivity.class));
   //         finish();
   //     }

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
