package edu.washington.mchs.greatmate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            Log.d("Main", user + "user alread logged in");
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        */

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        Button toManagerViewButton = (Button) findViewById(R.id.money_manager_button);

    }
}
