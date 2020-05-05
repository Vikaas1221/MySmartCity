package com.example.mysmartcity.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mysmartcity.Model.complaintModel;
import com.example.mysmartcity.Model.complaint_fragmentModel;
import com.example.mysmartcity.R;

import java.util.ArrayList;

public class complaintAdapter extends RecyclerView.Adapter<complaintAdapter.ViewHolder>
{
    ArrayList<complaint_fragmentModel> arrayList;
    Context context;
    public complaintAdapter(Context context, ArrayList<complaint_fragmentModel> arrayList)
    {
        this.context=context;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public complaintAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.complaint_status_layout,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull complaintAdapter.ViewHolder holder, int position)
    {
        complaint_fragmentModel model=arrayList.get(position);
        holder.categroy.setText(model.getCategory());
        holder.desc.setText(model.getDescription());
        holder.status.setText(model.getStatus());
        holder.complaint_id.setText(model.getComplaintId());
        holder.date.setText(model.getDate());
    }

    @Override
    public int getItemCount()
    {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView categroy,desc,status,date,complaint_id;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            categroy=itemView.findViewById(R.id.category_type);
            desc=itemView.findViewById(R.id.description_type);
            status=itemView.findViewById(R.id.status_type);
            date=itemView.findViewById(R.id.date);
            complaint_id=itemView.findViewById(R.id.requestID);

        }
    }
}
