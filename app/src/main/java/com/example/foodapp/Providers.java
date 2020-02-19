package com.example.foodapp;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.FirebaseApp;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


public class Providers extends Fragment {
    RecyclerView recyclerView;
    ProviderAdapter tvShowAdapter;
    ArrayList<ProvidersClass> tvShows = new ArrayList<ProvidersClass>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_providerstab, container, false);
    }
    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
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


