package com.example.foodapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.example.foodapp.ui.main.NewCustomer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class ProviderDescriptionActivity extends AppCompatActivity {

    ProvidersClass providersClass;
    CollapsingToolbarLayout collapsingToolbar;
    ImageView imageView;
    String providerUid;
    ArrayList<String> uId=new ArrayList<String>();
    String postion;
    Context context;
    RecyclerView recyclerView;
    ProviderDescriptionAdapter providerDescriptionAdapter;
    private FirebaseFirestore db;
    ArrayList<ProviderDescriptionClass> providerDescription = new ArrayList<ProviderDescriptionClass>();
   ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_description);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(ContextCompat.getColor(ProviderDescriptionActivity.this, R.color.appbar));
        }
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent in = new Intent();
        context =this;
        providersClass= (ProvidersClass) getIntent().getSerializableExtra("KEY_EVENT");
        System.out.println(providersClass.getUid());
        providerUid = providersClass.getUid();

        SharedPreferences sharedPref = getSharedPreferences("SomeName", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("String1", providersClass.getUid());  // value is the string you want to save
        editor.commit();
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        imageView = (ImageView) findViewById(R.id.collapseImageView);
        collapsingToolbar.setTitle(providersClass.getServiceName());
        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.BLACK)
                .borderWidthDp(0)
                .cornerRadiusDp(30)
                .oval(false)
                .build();
        Picasso.get()
                .load(providersClass.getAvatarImage())
                .fit().centerCrop()
                .into(imageView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FirebaseRetrive();

    }
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
    public void FirebaseRetrive() {
        db = FirebaseFirestore.getInstance();
        db.collection("Providers").document(providersClass.getUid()).collection("mealPackage")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                           //     Log.d("", document.getId() + " => " + document.getData());
                                ProviderDescriptionClass providerDescriptionClass = document.toObject(ProviderDescriptionClass.class);
                                ProviderDescriptionClass providerDescriptionClass1 = new ProviderDescriptionClass();
                                providerDescriptionClass1.setPackageImg(providerDescriptionClass.getPackageImg());
                                providerDescriptionClass1.setPrice(providerDescriptionClass.getPrice());
                                providerDescriptionClass1.setPackageName(providerDescriptionClass.getPackageName());
                                providerDescriptionClass1.setMonday(providerDescriptionClass.getMonday());
                                providerDescriptionClass1.setTuesday(providerDescriptionClass.getTuesday());
                                providerDescriptionClass1.setWednesday(providerDescriptionClass.getWednesday());
                                providerDescriptionClass1.setThursday(providerDescriptionClass.getThursday());
                                providerDescriptionClass1.setFriday(providerDescriptionClass.getFriday());
                                providerDescriptionClass1.setSaturday(providerDescriptionClass.getSaturday());
                                providerDescriptionClass1.setSunday(providerDescriptionClass.getSunday());
                                providerDescription.add(providerDescriptionClass1);
                                providerDescriptionAdapter = new ProviderDescriptionAdapter(providerDescription);
                                recyclerView = (RecyclerView) findViewById(R.id.mealPackageContent);
                                recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(providerDescriptionAdapter);
                                recyclerView.setVisibility(View.VISIBLE);


                             //   Log.d("", document.getId() + " => " + providersClass.getServiceName());
                            }
                        } else {
                            Log.w("", "Error getting documents.", task.getException());
                        }
                    }
                });

    }
}