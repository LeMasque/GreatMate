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
import com.google.firebase.database.DatabaseReference;
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

//        Log.i("SPENCER", "Local class: " + this.getLocalClassName() + "\r\nClass: " + this.getClass());

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

        go.setOnClickListener(new Submit());
    }

    private class Submit implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            final String pw = password.getText().toString().trim();
            final String vpw = verifyPassword.getText().toString().trim();
            final String em = email.getText().toString().trim();
            final String un = name.getText().toString().trim();
            final boolean su = isSignUp;
            if (pw.length() > 0 && un.length() > 0) {
                if (su) {
                    if (em.length() > 0 && pw.equals(vpw)) {
                        login(em, pw, un, su);
                    } else {
                        Toast.makeText(LoginActivity.this,
                                "ERROR: Empty email or password fields do not match.",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    login(em, pw, un, su);
                }
            } else {
                Toast.makeText(LoginActivity.this,
                        "ERROR: Empty username or password fields are not allowed.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void login(final String email, final String password, final String username, final boolean signUp) {
        OnCompleteListener ocl = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (signUp) {
                        DatabaseReference dr = database.getReference();
                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        dr.child("users").child(uid).child("name").setValue(username);
                        dr.child("users").child(uid).child("house").setValue(null);
                    }

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this,
                            "user creation failed: " + task.getException(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        };
        Task<AuthResult> t = null;
        if (signUp) {
            t = mAuth.createUserWithEmailAndPassword(email, password);
        } else {
            t = mAuth.signInWithEmailAndPassword(email, password);
        }
        t.addOnCompleteListener(LoginActivity.this, ocl);
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
