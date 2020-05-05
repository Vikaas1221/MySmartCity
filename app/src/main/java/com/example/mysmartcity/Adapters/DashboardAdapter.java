package com.example.mysmartcity.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mysmartcity.Activity.About;
import com.example.mysmartcity.Activity.AllComplaintsActivity;
import com.example.mysmartcity.Activity.MainActivity;
import com.example.mysmartcity.Activity.categoryActivity;
import com.example.mysmartcity.Model.dashboardModel;
import com.example.mysmartcity.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.Viewolder>
{
    ArrayList<dashboardModel> arrayList;
    Context context;
    Context c1;





    public DashboardAdapter(Context context,ArrayList<dashboardModel> arrayList)
    {
        this.context=context;
        this.arrayList=arrayList;
    }


    @NonNull
    @Override
    public Viewolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_layout_dashboard,parent,false);
        Viewolder viewolder=new Viewolder(view);
        c1=parent.getContext();
        return viewolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewolder holder, final int position)
    {

        final dashboardModel model=arrayList.get(position);
        holder.icon_text.setText(model.getName());
        holder.progressBar.setVisibility(View.VISIBLE);
        Picasso.get().load(model.getImage()).into(holder.icon_img, new Callback()
        {
            @Override
            public void onSuccess()
            {
                holder.progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Exception e)
            {


            }
        });
        holder.relativeLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (model.getName().equals("About"))
                {
                    Intent intent=new Intent(c1, About.class);
                    c1.startActivity(intent);

                }
                else  if (model.getName().equals("Complaints"))
                {
                    Intent intent=new Intent(c1, AllComplaintsActivity.class);
                    c1.startActivity(intent);
                }
                else {
                    Intent intent = new Intent(c1, categoryActivity.class);
                    intent.putExtra("name", model.getName());
                    intent.putExtra("pos", position);
                    c1.startActivity(intent);
                }

            }
        });









    }

    @Override
    public int getItemCount()
    {
        return arrayList.size();
    }


    public class Viewolder extends RecyclerView.ViewHolder
    {
        TextView icon_text;
        ImageView icon_img;
        ProgressBar progressBar;
        RelativeLayout relativeLayout;

        public Viewolder(@NonNull View itemView)
        {
            super(itemView);
            icon_img=itemView.findViewById(R.id.icon_dashboard);
            icon_text=itemView.findViewById(R.id.text_dashboard);
            progressBar=itemView.findViewById(R.id.progress);
            relativeLayout=itemView.findViewById(R.id.relative);
        }
    }
}

