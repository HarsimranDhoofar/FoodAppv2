package com.example.foodapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ProviderDescriptionAdapter  extends RecyclerView.Adapter<ProviderDescriptionAdapter.ViewHolder>{
    FirebaseFirestore db;
    List<ProviderDescriptionClass> providerDescriptionList;
    Context context;
    ArrayList<String> uId=new ArrayList<String>();

    public ProviderDescriptionAdapter(ArrayList<ProviderDescriptionClass> providerDescription) {
        this.providerDescriptionList = providerDescription;
    }

    @Override
    public ProviderDescriptionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_package_description,parent,false);
        ProviderDescriptionAdapter.ViewHolder viewHolder = new ProviderDescriptionAdapter.ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProviderDescriptionAdapter.ViewHolder holder, int position) {
        ProviderDescriptionClass providerDescriptionClass = providerDescriptionList.get(position);
        holder.packageName.setText(providerDescriptionClass.getPackageName());
        holder.packagePrice.setText("$ 200");
        holder.mondayDescription.setText(providerDescriptionClass.getMonday());
        holder.tuesdayDescription.setText(providerDescriptionClass.getTuesday());
        holder.wednesdayDescription.setText(providerDescriptionClass.getWednesday());
        holder.thursdayDescription.setText(providerDescriptionClass.getThursday());
        holder.fridayDescription.setText(providerDescriptionClass.getFriday());
        holder.saturdayDescription.setText(providerDescriptionClass.getSaturday());
        holder.sundayDescription.setText(providerDescriptionClass.getSunday());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent i = new Intent(context, PaypalPaymentActivity.class);
//                ProvidersClass eventInfo = providerList.get(position);
//                i.putExtra("KEY_EVENT", eventInfo); // passing the clicked event details as intent-extra
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return providerDescriptionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        ImageView packageImg;
        TextView packageName;
        TextView packagePrice;
        TextView mondayDescription;
        TextView tuesdayDescription;
        TextView wednesdayDescription;
        TextView thursdayDescription;
        TextView fridayDescription;
        TextView saturdayDescription;
        TextView sundayDescription;
        CardView cv;

        public ViewHolder(View itemView)
        {
            super(itemView);
            packageImg = (ImageView)itemView.findViewById(R.id.packageImg);
            packageName = (TextView)itemView.findViewById(R.id.packageName);
            packagePrice=( TextView) itemView.findViewById(R.id.package_price);
            mondayDescription = (TextView) itemView.findViewById(R.id.monday_description);
            tuesdayDescription = (TextView) itemView.findViewById(R.id.tuesday_description);
            wednesdayDescription = (TextView) itemView.findViewById(R.id.wednesday_description);
            thursdayDescription = (TextView) itemView.findViewById(R.id.thursday_description);
            fridayDescription = (TextView) itemView.findViewById(R.id.friday_description);
            saturdayDescription = (TextView) itemView.findViewById(R.id.saturday_description);
            sundayDescription = (TextView) itemView.findViewById(R.id.sunday_description);
            cv = (CardView)itemView.findViewById(R.id.packageDescriptionCardView);
        }

    }
}
