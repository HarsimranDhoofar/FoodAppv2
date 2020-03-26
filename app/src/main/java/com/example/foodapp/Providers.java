package com.example.foodapp;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.foodapp.ui.main.NewCustomer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


public class Providers extends Fragment {
    RecyclerView recyclerView;
    ProviderAdapter providerAdapter;
    private FirebaseFirestore db;
    ArrayList<ProvidersClass> providers = new ArrayList<ProvidersClass>();
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_providerstab, container, false);
    }
    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        db = FirebaseFirestore.getInstance();
        db.collection("Providers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("", document.getId() + " => " + document.getData());
                                ProvidersClass providersClass = document.toObject(ProvidersClass.class);
                                ProvidersClass providersClass1 = new ProvidersClass();
                                providersClass1.setUid(providersClass.getUid());
                                providersClass1.setAddress(providersClass.getAddress());
                                providersClass1.setAvatarImage(providersClass.getAvatarImage());
                                providersClass1.setServiceName(providersClass.getServiceName());
                                providers.add(providersClass1);
                                providerAdapter = new ProviderAdapter(providers);
                                recyclerView = (RecyclerView) view.findViewById(R.id.TvShows);
                                recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(providerAdapter);
                                recyclerView.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                Log.d("", document.getId() + " => " + providersClass.getServiceName());
                            }
                        } else {
                            Log.w("", "Error getting documents.", task.getException());
                        }
                    }
                });
//        DocumentReference docRef = db.collection("Providers").document(user.getUid());
//        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot snapshot,
//                                @Nullable FirebaseFirestoreException e) {
//                if (e != null) {
//                    System.err.println("Listen failed: " + e);
//                    return;
//                }
//
//                if (snapshot != null && snapshot.exists()) {
//                    System.out.println("Current data: " + snapshot.getData());
//                    NewCustomer newCustomer= snapshot.toObject(NewCustomer.class);
//                    userEmail_profile.setText(newCustomer.getEmail());
//                    userName_profile.setText(newCustomer.getFirstName()+" "+newCustomer.getLastName());
//                    userAddress_profile.setText(newCustomer.getDeliveryAddress());
//                } else {
//                    System.out.print("Current data: null");
//                }
//            }
//        });
//       FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
//       DatabaseReference mReference = mDatabase.getReference("provider");
//
//
//        mReference.addValueEventListener(new ValueEventListener() {
//                                             @Override
//                                             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                 tvShows.clear();
//                                                 for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                                                     ProvidersClass tvShow = new ProvidersClass();
//                                                     tvShow.setProvidername(ds.child("name").getValue().toString());
//                                                     tvShow.setImgProvider(ds.child("img").getValue().toString());
//
//                                                     tvShows.add(tvShow);
//                                                     tvShowAdapter = new ProviderAdapter(tvShows);
//
//                                                     recyclerView = (RecyclerView) view.findViewById(R.id.TvShows);
//                                                     recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//                                                     recyclerView.setItemAnimator(new DefaultItemAnimator());
//                                                     recyclerView.setAdapter(tvShowAdapter);
//                                                 }
//                                             }
//
//                                             @Override
//                                             public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                             }
//                                         });

    }
    }


