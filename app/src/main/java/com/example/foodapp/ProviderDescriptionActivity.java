package com.example.foodapp;

import android.content.Intent;
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
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class ProviderDescriptionActivity extends AppCompatActivity {

    ProvidersClass providersClass;
    CollapsingToolbarLayout collapsingToolbar;
    ImageView imageView;
    FirebaseFirestore db;
    ArrayList<String> uId=new ArrayList<String>();
    String postion;


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
        providersClass= (ProvidersClass) getIntent().getSerializableExtra("KEY_EVENT");
        System.out.println(providersClass.getUid());
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        imageView = (ImageView) findViewById(R.id.collapseImageView);
        collapsingToolbar.setTitle(providersClass.getServiceName());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    //    FirebaseRetrive();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
//    public void FirebaseRetrive() {
//        System.out.println("In FirebaseRetrive");
//
//        db.collection("Providers")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d("", document.getId() + " => " + document.getData());
//                                ProvidersClass providersClass = document.toObject(ProvidersClass.class);
//                                providersClass1.setUid(providersClass.getUid());
//                                System.out.println("inside 1st one");
//                                uId.add(document.getId());
//
//                                DocumentReference docRef = db.collection("Providers").document(uId);
//                                    docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                                    @Override
//                                       public void onEvent(@Nullable DocumentSnapshot snapshot,
//                                       @Nullable FirebaseFirestoreException e) {
//                                       if (e != null) {
//                                       System.err.println("Listen failed: " + e);
//                                       return;
//                            }
//
//                            if (snapshot != null && snapshot.exists()) {
//                                 System.out.println("Current data: " + snapshot.getData());
//                                 Log.e("selected:", providersClass1.getUid());
//                                 NewCustomer newCustomer = snapshot.toObject(NewCustomer.class);
////                               userEmail_profile.setText(newCustomer.getEmail());
////                               userName_profile.setText(newCustomer.getFirstName()+" "+newCustomer.getLastName());
////                               userAddress_profile.setText(newCustomer.getDeliveryAddress());
//                            } else {
//                                 System.out.print("Current data: null");
//                            }
//                        }
//                    });
//                                System.out.println(uId);
//                            }
//                        } else {
//                            System.out.println("inside 2st one");
//                            Log.w("", "Error getting documents.", task.getException());
//                        }
//                    }
//                });
//
//    }
}