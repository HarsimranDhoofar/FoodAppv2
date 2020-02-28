package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.foodapp.ui.main.NewCustomer;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    FirebaseUser user;
    private FirebaseAuth mAuth;
    private TextInputEditText mFirstNameField;
    private TextInputEditText mLastNameField;
    private TextInputEditText mEmailField;
    private TextInputEditText mDeliveryAddressField;
    private TextView validation_text;
    private MaterialButton changebtn;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(ContextCompat.getColor(EditProfileActivity.this, R.color.appbar));
        }

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.app_bar_signUp);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mFirstNameField= findViewById(R.id.firstName_input_editProfile);
        mLastNameField= findViewById(R.id.lastName_input_editprofile);
        mEmailField = findViewById(R.id.email_input_editprofile);
        mDeliveryAddressField= findViewById(R.id.deliveryAddress_input_editProfile);
        validation_text= findViewById(R.id.validation_alert_textView);
        populateProfileData();
        changebtn = findViewById(R.id.fbt_change_change);

        changebtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               updateCustomer();
            }
        });

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
                    mFirstNameField.setText(newCustomer.getFirstName());
                    mEmailField.setText(newCustomer.getEmail());
                    mLastNameField.setText(newCustomer.getLastName());
                    mDeliveryAddressField.setText(newCustomer.getDeliveryAddress());
                } else {
                    System.out.print("Current data: null");
                }
            }
        });
    }
    private void updateCustomer(){
        Map<String, Object> user = new HashMap<>();
        user.put("firstName", mFirstNameField.getText().toString());
        user.put("lastName", mLastNameField.getText().toString());
        user.put("email", mEmailField.getText().toString());
        user.put("deliveryAddress", mDeliveryAddressField.getText().toString());
        NewCustomer newCustomer = new NewCustomer(mAuth.getCurrentUser().getUid(),"",mFirstNameField.getText().toString(),mLastNameField.getText().toString(),mEmailField.getText().toString(),mDeliveryAddressField.getText().toString());
        db.collection("Customers").document(mAuth.getCurrentUser().getUid())
                .update(user)
                 .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("update", "DocumentSnapshot successfully updated!");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("update", "Error updating document", e);
                    }
                });


    }

}
