package com.example.foodapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Providers extends Fragment {
    RecyclerView recyclerView;
    ProviderAdapter tvShowAdapter;
    ArrayList<ProvidersClass> tvShows = new ArrayList<ProvidersClass>();

   // public static final String[] TvShows= {"Breaking Bad","Rick and Morty", "FRIENDS","Sherlock","Stranger Things"};
  //  public static final int[] TvShowImgs = {R.drawable.shr_logo,R.drawable.shr_logo,R.drawable.shr_logo,R.drawable.shr_logo,R.drawable.shr_logo};




//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_providerstab, container, false);
    }
    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
       FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
       DatabaseReference mReference = mDatabase.getReference("provider");


        mReference.addValueEventListener(new ValueEventListener() {
                                             @Override
                                             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                 tvShows.clear();
                                                 for (int i = 0; i <2; i++) {
                                                     ArrayList<String> providerNames = new ArrayList<>();
                                                     ProvidersClass tvShow = new ProvidersClass();
                                                     providerNames.add((String) dataSnapshot.child("p"+i).child("name").getValue());

                                                     tvShow.setProvidername(providerNames.get(0));
                                                     tvShow.setImgProvider(R.drawable.shr_logo);

                                                     tvShows.add(tvShow);
                                                     tvShowAdapter = new ProviderAdapter(tvShows);

                                                     recyclerView = (RecyclerView) view.findViewById(R.id.TvShows);
                                                     recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                                     recyclerView.setItemAnimator(new DefaultItemAnimator());
                                                     recyclerView.setAdapter(tvShowAdapter);
                                                 }
                                             }

                                             @Override
                                             public void onCancelled(@NonNull DatabaseError databaseError) {

                                             }
                                         });
//        for(int i=0;i<TvShows.length;i++)
//        {
//            ProvidersClass tvShow = new ProvidersClass();
//
//            tvShow.setProvidername(TvShows[i]);
//            tvShow.setImgProvider(TvShowImgs[i]);
//
//            tvShows.add(tvShow);
//        }


//        tvShowAdapter = new ProviderAdapter(tvShows);
//
//        recyclerView = (RecyclerView) view.findViewById(R.id.TvShows);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(tvShowAdapter);
    }
    }


