package com.example.mysmartcity.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mysmartcity.Activity.TourismSubCategory;
import com.example.mysmartcity.Model.AnnouncementsModel;
import com.example.mysmartcity.Model.dashboardModel;
import com.example.mysmartcity.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.mysmartcity.Activity.categoryActivity.s;

public class categoryAdapter extends RecyclerView.Adapter
{
    Context context;
    Context c1;
    ArrayList<dashboardModel> dataModel;
    public categoryAdapter(Context context,ArrayList<dashboardModel> dataModel)
    {
        this.context=context;
        this.dataModel=dataModel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        c1=parent.getContext();
        View view;
        if (viewType==0)
        {
            view= LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.secondlayout,parent,false);
            viewholderone ob=new viewholderone(view);
            return ob;

        }
        else if (viewType==1)
        {
            view= LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.health,parent,false);
            viewholdertwo ob=new viewholdertwo(view);
            return ob;
        }
        else if (viewType==2)
        {
            view= LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.directory_layout,parent,false);
            viewholderthree ob=new viewholderthree(view);
            return ob;
        }
        else if (viewType==3)
        {
            view= LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.emergency_layout,parent,false);
            viewholderFour ob=new viewholderFour(view);
            return ob;
        }
        else if (viewType==4)
        {
            view= LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.tourism_directory,parent,false);
            viewholderFive ob=new viewholderFive(view);
            return ob;
        }
        else if (viewType==5)
        {
            view=LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.tourist_places,parent,false);
            viewholderSix ob=new viewholderSix(view);
            return ob;

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position)
    {
        final dashboardModel obj=dataModel.get(position);
        if (s.equals("Announcements"))
        {
            viewholderone obj1=(viewholderone) holder;
            obj1.t1.setText(obj.getName());
            obj1.t2.setText(obj.getNewDescription());
            obj1.t3.setText(obj.getNewsEvent());
            obj1.parentLayout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                }
            });


        }
        else if (s.equals("Health"))
        {
            viewholdertwo obj2=(viewholdertwo)holder;
            obj2.t_name.setText(obj.getName());
            obj2.t_phone.setText(obj.getPhonenum());
            obj2.ratingBar.setRating(Float.parseFloat(obj.getRating()));
            obj2.t_address.setText(obj.getAdress());
            obj2.t_website.setText(obj.getWebsite());
            obj2.t_specilisst.setText(obj.getNewDescription());
            Picasso.get().load(obj.getImage()).into(obj2.t_img);

        }
        else if (s.equals("Tourist Places"))
        {
            final viewholderSix obj6=(viewholderSix)holder;
            Picasso.get().load(obj.getImage()).into(obj6.i1);
            obj6.t1.setText(obj.getName());
            obj6.t2.setText(obj.getNewDescription());
            final LinearLayout layout=obj6.linearLayout;
            obj6.i2.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (layout.getVisibility()==View.GONE)
                    {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                        {
                            TransitionManager.beginDelayedTransition(obj6.cardView,new AutoTransition());
                            layout.setVisibility(View.VISIBLE);
                            obj6.i2.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                        }
                    }
                    else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            TransitionManager.beginDelayedTransition(obj6.cardView, new AutoTransition());
                            layout.setVisibility(View.GONE);
                            obj6.i2.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                        }

                    }
                }
            });
        }
        else if (s.equals("Directory"))
        {
            viewholderthree obj3=(viewholderthree) holder;
            obj3.t1.setText(obj.getName());
            obj3.parentLayout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                }
            });
        }
        else if (s.equals("Emergency"))
        {
            viewholderFour obj4=(viewholderFour) holder;
            obj4.t1.setText(obj.getName());
            obj4.t2.setText(obj.getNewDescription());
            obj4.parentLayout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    String number=obj.getPhonenum().toString();

                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:"+number));//change the number
                    c1.startActivity(callIntent);


                }
            });
        }
        else if (s.equals("Tourism")||s.equals("Transport"))
        {
            String s1="";
            viewholderFive obj5=(viewholderFive) holder;
            obj5.t1.setText(obj.getName());
            obj5.t2.setText(obj.getNewDescription());
            Log.d("ghfd",""+obj.getImage());
            if (s.equals("Tourism"))
            {
                s1="Tourism";
            }
            else
            {
                s1="Transport";
            }
          Picasso.get().load(obj.getImage()).into(obj5.i1);
            final String finalS = s1;
            obj5.parentLayout.setOnClickListener(new View.OnClickListener()
          {
              @Override
              public void onClick(View v)
              {

                  String s=obj.getName();
                  Log.d("zaq",""+s);
                  Intent intent=new Intent(c1, TourismSubCategory.class);
                  intent.putExtra("name",s);
                  intent.putExtra("c_name", finalS);
                  c1.startActivity(intent);
              }
          });
        }

    }

    @Override
    public int getItemCount()
    {
        return dataModel.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        if (s.equals("Announcements"))
        {
            return 0;
        }
        else if (s.equals("Health"))
        {
            return 1;
        }
        else if (s.equals("Directory"))
        {
            return 2;
        }
        else if (s.equals("Emergency"))
        {
            return 3;
        }
        else if (s.equals("Tourism")||s.equals("Transport"))
        {
            return 4;
        }
        else if (s.equals("Tourist Places"))
        {
            return 5;
        }

        return -1;
    }


    public class viewholderone extends RecyclerView.ViewHolder
    {
        LinearLayout parentLayout;
        TextView t1,t2,t3;


        public viewholderone(@NonNull View itemView)
        {
            super(itemView);
            t1=itemView.findViewById(R.id.newsHeading);
            t2=itemView.findViewById(R.id.newsdescription);
            t3=itemView.findViewById(R.id.eventDate);
            parentLayout=itemView.findViewById(R.id.newsParentLayout);
        }
    }
    public class viewholdertwo extends RecyclerView.ViewHolder
    {
        RelativeLayout parentLayout;
        ImageView t_img;
        TextView t_name,t_address,t_website,t_phone,t_specilisst;
        RatingBar ratingBar;
        public viewholdertwo(@NonNull View itemView)
        {
            super(itemView);
            t_img=itemView.findViewById(R.id.hospital_img);
            t_name=itemView.findViewById(R.id.hospital_name);
            t_specilisst=itemView.findViewById(R.id.specilist);
            t_address=itemView.findViewById(R.id.address);
            t_website=itemView.findViewById(R.id.website);
            t_phone=itemView.findViewById(R.id.phone);
            ratingBar=itemView.findViewById(R.id.rating);
            parentLayout=itemView.findViewById(R.id.healthParentLayout);
        }
    }
    public class viewholderthree extends RecyclerView.ViewHolder
    {
        RelativeLayout parentLayout;
        TextView t1;
        public viewholderthree(@NonNull View itemView)
        {
            super(itemView);
            t1=itemView.findViewById(R.id.category_name);
            parentLayout=itemView.findViewById(R.id.directoryParentLayout);
        }
    }
    public class viewholderFour extends RecyclerView.ViewHolder
    {
        RelativeLayout parentLayout;
        TextView t1;
        TextView t2;
        public viewholderFour(@NonNull View itemView)
        {
            super(itemView);
            t1=itemView.findViewById(R.id.category_name);
            t2=itemView.findViewById(R.id.desc);
            parentLayout=itemView.findViewById(R.id.emergencyParentLayout);
        }
    }
    public class viewholderFive extends RecyclerView.ViewHolder
    {
        RelativeLayout parentLayout;
        ImageView i1;
        TextView t1,t2;

        public viewholderFive(@NonNull View itemView)
        {
            super(itemView);
             i1=itemView.findViewById(R.id.category_image);
            t1=itemView.findViewById(R.id.category_name);
            t2=itemView.findViewById(R.id.category_address);
            parentLayout=itemView.findViewById(R.id.tourismParentLayout);
        }
    }
    public class viewholderSix extends RecyclerView.ViewHolder
    {
        ImageView i1,i2;
        TextView t1,t2;
        LinearLayout linearLayout;
        CardView cardView;

        public viewholderSix(@NonNull View itemView)
        {
            super(itemView);
            i1=itemView.findViewById(R.id.hodpitalImage);
            t1=itemView.findViewById(R.id.hospitalName);
            t2=itemView.findViewById(R.id.description);
            i2=itemView.findViewById(R.id.arrow_down);
            linearLayout=itemView.findViewById(R.id.layout);
            cardView=itemView.findViewById(R.id.card);
        }
    }


}
