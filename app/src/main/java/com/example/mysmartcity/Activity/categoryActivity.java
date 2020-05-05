package com.example.mysmartcity.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

//import com.example.mysmartcity.Adapters.categoryAdapter;
import com.example.mysmartcity.Adapters.categoryAdapter;
import com.example.mysmartcity.Model.AnnouncementsModel;
import com.example.mysmartcity.Model.CommonModel;
import com.example.mysmartcity.Model.dashboardModel;
import com.example.mysmartcity.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class categoryActivity extends AppCompatActivity
{
   // Object object=null;

    FirebaseAuth mauth;
    FirebaseFirestore db=FirebaseFirestore.getInstance();

    CollectionReference reference=db.collection("Mycity");

    CollectionReference reference1;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ArrayList<dashboardModel> arrayList;
    ArrayList<CommonModel> arrayList1;
    ProgressBar progressBar;

    public static String s=null;
    public static  int  i=0;
  //  public static int p;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        recyclerView=findViewById(R.id.categroyRecyclerview);
        progressBar=findViewById(R.id.progress);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);



        final Bundle b=getIntent().getExtras();
        s=b.getString("name");
      //  s=s.replaceAll(" ","");
        Log.d("dfssas",""+s);
        i=b.getInt("pos");
        arrayList=new ArrayList<>();

        Toast.makeText(getApplicationContext(),"name: "+s,Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.VISIBLE);

        reference.whereEqualTo("Name",s).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots)
            {
                for (final QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                {
                    String id=documentSnapshot.getId();
                    reference1=reference.document(id).collection(s);

                    reference1.get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                                {
                                    for (QueryDocumentSnapshot documentSnapshot1:queryDocumentSnapshots)
                                    {

                                            String newsHeading=documentSnapshot1.getString("newsheading");
                                            String Description=documentSnapshot1.getString("description");
                                            String newsevent=documentSnapshot1.getString("time");
                                            String phone=documentSnapshot1.getString("Number");
                                            String website=documentSnapshot1.getString("website");
                                            String rating=documentSnapshot1.getString("Rating");


                                        String addres=documentSnapshot1.getString("Address");
                                        Log.d("adresss",""+addres);
                                        String name=documentSnapshot1.getString("Name");

                                        String Image=documentSnapshot1.getString("Image");
                                        Log.d("heading",""+newsHeading);
//                                        DataModel obj=new DataModel();
                                        Log.d("img",""+Image);
                                        dashboardModel obj=new dashboardModel();
                                        obj.setRating(rating);
                                        obj.setWebsite(website);
                                        obj.setName(name);
                                        obj.setNewsEvent(newsevent);
                                        obj.setNewDescription(Description);
                                        obj.setNewsHeading(newsHeading);
                                        obj.setImage(Image);
                                        obj.setAdress(addres);
                                        obj.setPhonenum(phone);
                                        arrayList.add(obj);

                                    }
                                    adapter=new categoryAdapter(getApplicationContext(),arrayList);
                                    recyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                    progressBar.setVisibility(View.GONE);
                                }
                            });

                }
            }
        });




    }
}
