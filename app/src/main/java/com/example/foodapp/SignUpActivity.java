package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.ui.main.NewCustomer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private TextInputEditText mFirstNameField;
    private TextInputEditText mLastNameField;
    private TextInputEditText mEmailField;
    private TextInputEditText mDeliveryAddressField;
    private TextInputEditText mPasswordField;
    private TextInputEditText mCPasswordField;
    private TextView validation_text;
    private MaterialButton signUpbtn;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(ContextCompat.getColor(SignUpActivity.this, R.color.appbar));
        }
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.app_bar_signUp);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mFirstNameField= findViewById(R.id.firstName_input_register);
        mLastNameField= findViewById(R.id.lastName_input_register);
        mEmailField = findViewById(R.id.email_input_register);
        mDeliveryAddressField= findViewById(R.id.deliveryAddress_input_register);
        mPasswordField = findViewById(R.id.password_input_register);
        mCPasswordField = findViewById(R.id.cpassword_text_input_register);
        validation_text= findViewById(R.id.validation_alert_textView);
        signUpbtn = findViewById(R.id.fbt_signIn_signIn);//Don't need to type casting in android studio 3

        signUpbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
       //  updateUI(currentUser);
    }

    private void createAccount(String email, String password) {
        if (!validateForm()) {
            return;
        }
        progressDialog.setMessage("Creating New Account...");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                             Log.d("NewAccountCreationS", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            insertCustomer();
                            progressDialog.dismiss();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                             Log.w("NewAccountCreationF", "createUserWithEmail:failure", task.getException());
                            validation_text.setText("Account not Created: "+task.getException());
                            updateUI(null);
                        }
                    }
                });
    }

        private boolean validateForm () {
            boolean valid = true;

            String email = mEmailField.getText().toString();
            if (TextUtils.isEmpty(email)) {
                mEmailField.setError("Required.");
                valid = false;
            } else {
                mEmailField.setError(null);
            }

            String password = mPasswordField.getText().toString();
            if (TextUtils.isEmpty(password)) {
                mPasswordField.setError("Required.");
                valid = false;
            } else {
                mPasswordField.setError(null);
            }

            return valid;
        }
        private void insertCustomer(){

            NewCustomer newCustomer = new NewCustomer(mAuth.getCurrentUser().getUid(),"",mFirstNameField.getText().toString(),mLastNameField.getText().toString(),mEmailField.getText().toString(),mDeliveryAddressField.getText().toString());
            db.collection("Customers").document(mAuth.getCurrentUser().getUid())
                    .set(newCustomer);



        }
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            finish();
            Intent intent = new Intent(SignUpActivity.this, Dashboard.class);
            startActivity(intent);
        } else {
           validation_text.setVisibility(View.VISIBLE);
        }
    }
    }
