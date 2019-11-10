package com.example.foodapp;

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;
public class ProviderAdapter extends RecyclerView.Adapter<ProviderAdapter.ViewHolder>{

    List<ProvidersClass> TvShowList;
    Context context;

    public ProviderAdapter(List<ProvidersClass>TvShowList)
    {
        this.TvShowList = TvShowList;
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
        ProvidersClass tvShow = TvShowList.get(position);

        holder.textTvShow.setText(tvShow.getProvidername());
        Picasso.get()
                .load(tvShow.getImgProvider())
                .resize(50, 50)
                .centerCrop()
                .into(holder.imgTvShow);
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProviderDescriptionActivity.class);
                view.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return TvShowList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        ImageView imgTvShow;
        TextView textTvShow;
        CardView cv;

        public ViewHolder(View itemView)
        {
            super(itemView);
            imgTvShow = (ImageView)itemView.findViewById(R.id.imgTvshow);
            textTvShow = (TextView)itemView.findViewById(R.id.textTvshow);
            cv = (CardView)itemView.findViewById(R.id.cv);
        }

    }
}

