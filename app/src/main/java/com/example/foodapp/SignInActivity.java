package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextInputEditText mEmailField;
    private TextInputEditText mPasswordField;
    private MaterialButton signInbtn;
    private MaterialButton signUpbtn;
    private ProgressDialog progressDialog;
    private TextView signIn_alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(ContextCompat.getColor(SignInActivity.this, R.color.appbar));
        }
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();
        mEmailField = findViewById(R.id.txt_signIn_username);
        mPasswordField = findViewById(R.id.txt_signIn_password);
        progressDialog = new ProgressDialog(this);
        signIn_alert = findViewById(R.id.validation_alert_textView_signIn);
        signInbtn =findViewById(R.id.bt_signIn_signIn);//Don't need to type casting in android studio 3
        signInbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
            }
        });
        signUpbtn = findViewById(R.id.bt_signUp);
        signUpbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
       // updateUI(currentUser);
    }
    private void signIn(String email, String password) {
       // Log.d(TAG, "signIn:" + email);

        if (!validateForm()) {
            return;
        }

      //  showProgressDialog();
         progressDialog.setMessage("Signing in...");
         progressDialog.show();
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                        //    Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            signIn_alert.setText(task.getException().getMessage());
                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            updateUI(null);
                        }
                        if (!task.isSuccessful()) {
                            signIn_alert.setText(task.getException().getMessage());
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
    private void updateUI(FirebaseUser user) {
        progressDialog.dismiss();
        if (user != null) {
            finish();
            Intent intent = new Intent(SignInActivity.this, Dashboard.class);
                            startActivity(intent);
        } else {
            signIn_alert.setVisibility(View.VISIBLE);
        }
    }
}
