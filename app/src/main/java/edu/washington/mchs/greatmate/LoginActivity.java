package edu.washington.mchs.greatmate;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private boolean isSignUp = true;
    private EditText email;
    private EditText password;
    private EditText verifyPassword;
    private Button go;
    private RelativeLayout rl;
    private TextView changeForm;
    private EditText name;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        verifyPassword = (EditText) findViewById(R.id.verify_password);
        go = (Button) findViewById(R.id.go);
        changeForm = (TextView) findViewById(R.id.changeForm);
        name = (EditText) findViewById(R.id.name);
        database = FirebaseDatabase.getInstance();

        changeForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeForm();
                isSignUp = !isSignUp;
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    Log.d("Login", "user signed in");
                } else {
                    Log.d("Login", "user not signed in");
                }
            }
        };

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().equals(verifyPassword.getText().toString()) &&
                        email.getText().toString().length() > 0 && isSignUp) {
                    mAuth.createUserWithEmailAndPassword(email.getText().toString(),
                            password.getText().toString())
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    database.getReference("users/" +
                                            FirebaseAuth.getInstance().getCurrentUser().getUid() +
                                            "/" + "name").setValue(name.getText().toString());
                                    database.getReference("users/" +
                                            FirebaseAuth.getInstance().getCurrentUser().getUid() +
                                            "/" + "house").setValue("");

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);

                                    if(!task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this,
                                                "user creation failed: " + task.getException(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else if(!isSignUp) {
                    mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);

                                    if(!task.isSuccessful()) {
                                        Log.d("Login", "sign in not successful: " + task.getException());
                                        Toast.makeText(LoginActivity.this,
                                                "sign in failed: " + task.getException(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    public void changeForm() {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        if(isSignUp) {
            verifyPassword.setVisibility(View.INVISIBLE);
            name.setVisibility(View.INVISIBLE);
            lp.addRule(RelativeLayout.BELOW, password.getId());
            go.setLayoutParams(lp);
            go.setText("Login");
            changeForm.setText("Need to create an account?");
        } else {
            verifyPassword.setVisibility(View.VISIBLE);
            name.setVisibility(View.VISIBLE);
            lp.addRule(RelativeLayout.BELOW, verifyPassword.getId());
            go.setLayoutParams(lp);
            go.setText("Sign Up");
            changeForm.setText("Already have an account?");
        }
    }
}
