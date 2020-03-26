package com.example.foodapp;

import android.content.Context;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.ui.main.NewCustomer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Provider;
import java.util.ArrayList;
import java.util.List;
public class ProviderAdapter extends RecyclerView.Adapter<ProviderAdapter.ViewHolder> {
    FirebaseFirestore db;
    List<ProvidersClass> providerList;
    Context context;
    ArrayList<String> uId=new ArrayList<String>();
    int postionsOfAdapter;
    Bitmap imageofAdapter;

    public ProviderAdapter(List<ProvidersClass> ProviderList)
    {
        this.providerList = ProviderList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_providerstab,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        postionsOfAdapter = position;

        ProvidersClass providersClass = providerList.get(position);
          holder.providerName.setText(providersClass.getServiceName());
          holder.providerAddress.setText(providersClass.getAddress());
          holder.providerImg.setImageBitmap(imageofAdapter);

//        holder.textTvShow.setText(tvShow.getProvidername());
        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.BLACK)
                .borderWidthDp(0)
                .cornerRadiusDp(30)
                .oval(false)
                .build();
        Picasso.get()
                .load(providersClass.getAvatarImage())
                .resize(70, 70)
                .centerCrop()
                .transform(transformation)
                .into(holder.providerImg);
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent i = new Intent(context, ProviderDescriptionActivity.class);
                ProvidersClass eventInfo = providerList.get(position);
                i.putExtra("KEY_EVENT", eventInfo); // passing the clicked event details as intent-extra
                context.startActivity(i);
            }
        });
    }



    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public int getItemCount() {
        return providerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        ImageView providerImg;
        TextView providerName;
        TextView providerAddress;
        CardView cv;

        public ViewHolder(View itemView)
        {
            super(itemView);
            providerImg = (ImageView)itemView.findViewById(R.id.providerImg);
            providerName = (TextView)itemView.findViewById(R.id.provider_name);
            providerAddress=( TextView) itemView.findViewById(R.id.provider_address);
            cv = (CardView)itemView.findViewById(R.id.cv);
        }

    }
}

