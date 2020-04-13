package com.example.foodapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.ui.main.CustomerSubscribed;
import com.example.foodapp.ui.main.NewCustomer;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

public class PaypalPaymentActivity extends AppCompatActivity {
    private static final int PAYPAL_REQUEST_CODE = 7777;
    private FirebaseAuth firebaseAuth;
    SharedPreferences sharedPref;
    private FirebaseFirestore db;
    FirebaseUser user;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);
    MaterialButton btnPayNow;
    TextView providerNameText;
    TextView providerPriceText;
    String providerID;
    String providerName;
    String providerPrice;
    String providerImg;
    String monday;
    String tuesday;
    String wednesday;
    String thursday;
    String friday;
    String saturday;
    String sunday;
    String amount = "";

    private FirebaseAuth mAuth;
    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(ContextCompat.getColor(PaypalPaymentActivity.this, R.color.appbar));
        }
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        SharedPreferences sharedPref = getSharedPreferences("SomeName", Context.MODE_PRIVATE);
         providerID = sharedPref.getString("String1", "defaultValue");
        System.out.println(providerID);
        db.setFirestoreSettings(settings);
        user = firebaseAuth.getCurrentUser();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.app_bar_payment);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        providerNameText = (TextView) findViewById(R.id.providername_pay);
        providerPriceText = (TextView) findViewById(R.id.providerprice_pay);
        providerName = getIntent().getSerializableExtra("PROVIDER_NAME").toString();
        providerPrice = getIntent().getSerializableExtra("PACKAGE_PRICE").toString();
        providerImg = getIntent().getSerializableExtra("PACKAGE_IMAGE").toString();
        monday = getIntent().getSerializableExtra("MONDAY").toString();
        tuesday = getIntent().getSerializableExtra("TUESDAY").toString();
        wednesday = getIntent().getSerializableExtra("WEDNESDAY").toString();
        thursday = getIntent().getSerializableExtra("THURSDAY").toString();
        friday = getIntent().getSerializableExtra("FRIDAY").toString();
        saturday = getIntent().getSerializableExtra("SATURDAY").toString();
        sunday = getIntent().getSerializableExtra("SUNDAY").toString();
        providerNameText.setText("Package Name: "+ providerName);
        providerPriceText.setText("Price: "+ providerPrice );
        System.out.println(providerName);
        Intent intent = new Intent(this,PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);

        btnPayNow = findViewById(R.id.pay);

        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processPayment();
            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
    private void processPayment() {
        amount = providerPrice;

        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)),"USD",
                "Purchase Goods",PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);
    }
    private void subToPackage(){

        ProviderDescriptionClass newProvider = new ProviderDescriptionClass(monday,tuesday,wednesday,thursday,friday,saturday,sunday,providerName,providerImg,providerPrice);
        db.collection("Customers").document(firebaseAuth.getCurrentUser().getUid()).collection("currentsub").document("provider")
                .set(newProvider);



    }
    private void addCustomerToProvider(){
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
                    NewCustomer newCustomer = snapshot.toObject(NewCustomer.class);
                    System.out.println(providerID);
                    CustomerSubscribed customer = new CustomerSubscribed(user.getUid(), newCustomer.getProfileImg().trim().toString(), newCustomer.getFirstName().trim().toString(), newCustomer.getLastName().trim().toString(), newCustomer.getEmail().trim().toString(), newCustomer.getDeliveryAddress().trim().toString(),providerName,providerPrice,"123","3123","approved");
                    db.collection("Providers").document(providerID).collection("currentsub").document(user.getUid())
                            .set(customer);
                } else {
                    System.out.print("Current data: null");
                }
            }

            });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                System.out.println(confirmation);
                if (confirmation != null) {
                    try {
                        String paymentDetails = confirmation.toJSONObject().toString(4);


                        subToPackage();

                        addCustomerToProvider();
                        Intent intent = new Intent(this,Dashboard.class);
                        startService(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED)
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
    }
}
