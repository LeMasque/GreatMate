package edu.washington.mchs.greatmate;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
<<<<<<< HEAD
=======
import android.widget.TableLayout;
>>>>>>> spencer
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
public class Settings extends AppCompatActivity {
=======
public class Settings extends Fragment {
>>>>>>> spencer
    private Button signOut;
    private EditText houseName;
    private Button enterHouse;
    private TabHost tabHost;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private MainActivity hostActivity;

    public Settings() { } // blank constructor for reasons

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        MainActivity qa = (MainActivity) activity;
        hostActivity = qa;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_settings, container, false);
        signOut = (Button) view.findViewById(R.id.signOutButton);
        houseName = (EditText) view.findViewById(R.id.house);
        enterHouse = (Button) view.findViewById(R.id.enterHouse);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent = new Intent(hostActivity, LoginActivity.class);
                startActivity(intent);
            }
        });

        enterHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD
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
=======
                final FirebaseUser user = auth.getCurrentUser();
                final String hn = houseName.getText().toString().trim();
                database.getReference("users/" + user.getUid() + "/house").setValue(hn);
                final DatabaseReference houseLoc = database.getReference("houses/" + hn);
                database.getReference().child("users").child(user.getUid()).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String username = dataSnapshot.getValue(String.class);
                        houseLoc.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (!dataSnapshot.exists()) { // IF THE HOUSE IS NEW, INITIALIZE WITH SOME EXAMPLE DATA
                                    List<User> users = new ArrayList<User>();
                                    users.add(new User(username, user.getEmail(), hn));
                                    List<GroceryItem> groceries = new ArrayList<GroceryItem>();
                                    groceries.add(new GroceryItem("Example", 1, "An example item"));
                                    List<Transaction> transactions = new ArrayList<Transaction>();
                                    transactions.add(new Transaction(new Money(12, 5), "An example transaction", users.get(0)));
                                    houseLoc.setValue(new House(groceries, transactions, users));
                                } else { // house already exists, simply append user to users list.
                                    houseLoc.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            // TODO : FIXME so that i work. need to update value on the DB here...
                                            List<User> list = (List<User>)dataSnapshot.getValue();
                                            list.add(new User(username, user.getEmail(), hn));
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                Toast.makeText(hostActivity, "House Set to " + hn, Toast.LENGTH_SHORT).show();
>>>>>>> spencer
            }
        });
        return view;
    }
}
