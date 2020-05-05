package com.example.mysmartcity.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mysmartcity.Model.tourismModel;
import com.example.mysmartcity.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TourismAdapter extends RecyclerView.Adapter<TourismAdapter.ViewHolder>
{
    Context context;
    Context c1;
    ArrayList<tourismModel> arrayList;
    public TourismAdapter(Context context, ArrayList<tourismModel> arrayList)
    {
        this.context=context;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tourism_single_layout,null,false);
        c1=parent.getContext();
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        final tourismModel obj=arrayList.get(position);
//        holder.
        holder.t_name.setText(obj.getName());
        holder.t_phone.setText(obj.getNumber());
        holder.ratingBar.setRating(Float.parseFloat(obj.getRating()));
        holder.t_address.setText(obj.getAddress());
        holder.t_website.setText(obj.getWebsite());
        Picasso.get().load(obj.getImage()).into(holder.t_img);
        holder.t_website.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://"+obj.getWebsite()));
                c1.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView t_img;
        TextView t_name,t_address,t_website,t_phone;
        RatingBar ratingBar;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            t_img=itemView.findViewById(R.id.tourism_img);
            t_name=itemView.findViewById(R.id.tourism_name);
            t_address=itemView.findViewById(R.id.address);
            t_website=itemView.findViewById(R.id.website);
            t_phone=itemView.findViewById(R.id.phone);
            ratingBar=itemView.findViewById(R.id.rating);
        }
    }
}
