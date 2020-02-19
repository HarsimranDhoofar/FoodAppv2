package com.example.foodapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceFragmentCompat;

import com.example.foodapp.ui.main.NewCustomer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    FirebaseUser user;
    private TextView userEmail_profile;
    private TextView userName_profile;
    private TextView userAddress_profile;
    private MaterialButton logout_button_profile;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(ContextCompat.getColor(ProfileActivity.this, R.color.appbar));
        }
        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this, SignInActivity.class));
        }
         user = firebaseAuth.getCurrentUser();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.app_bar_signUp);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userEmail_profile = (TextView) findViewById(R.id.userEmail_profile);
        userName_profile = (TextView) findViewById(R.id.userName_profile);
        userAddress_profile =(TextView) findViewById(R.id.userAddress_profile);
       // userEmail_profile.setText(user.getEmail());

        logout_button_profile = findViewById(R.id.logout_btn_profile);
        logout_button_profile.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.listview_profile);
        populateProfileData();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Edit Profile");
        arrayList.add("Change Password");
        arrayList.add("Saved Cards");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);

        listView.setAdapter(arrayAdapter);
    }
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
    private void populateProfileData(){
        DocumentReference docRef = db.collection("Customers").document(user.getUid());
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    System.err.println("Listen failed: " + e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    System.out.println("Current data: " + snapshot.getData());
                    NewCustomer newCustomer= snapshot.toObject(NewCustomer.class);
                    userEmail_profile.setText(newCustomer.getEmail());
                    userName_profile.setText(newCustomer.getFirstName()+" "+newCustomer.getLastName());
                    userAddress_profile.setText(newCustomer.getDeliveryAddress());
                } else {
                    System.out.print("Current data: null");
                }
            }
        });
    }
    @Override
    public void onClick(View v) {

        if(v == logout_button_profile ){
            firebaseAuth.signOut();
            finish();
            Dashboard.fa.finish();
            startActivity(new Intent(this, SignInActivity.class));
        }
    }
}
