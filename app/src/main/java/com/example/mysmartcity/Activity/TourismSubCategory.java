package com.example.mysmartcity.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.mysmartcity.Adapters.TourismAdapter;
import com.example.mysmartcity.Model.tourismModel;
import com.example.mysmartcity.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class TourismSubCategory extends AppCompatActivity
{
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    CollectionReference reference=db.collection("Mycity");
    CollectionReference reference1,reference2;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ArrayList<tourismModel> arrayList;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourism_sub_category);
        final String name = getIntent().getExtras().getString("name");
        final String catergoryName=getIntent().getExtras().getString("c_name");
        Log.d("lkj",""+catergoryName);
        recyclerView=findViewById(R.id.tourismRecyclerview);
        progressBar=findViewById(R.id.progress);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        arrayList=new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);

        reference.whereEqualTo("Name",catergoryName)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
        {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots)
            {
                for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                {
                    reference1=reference.document(documentSnapshot.getId()).collection(catergoryName);
                    reference1.whereEqualTo("Name",name).get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
                            {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                                {
                                    for (QueryDocumentSnapshot documentSnapshot1:queryDocumentSnapshots)
                                    {
                                        reference2=reference1.document(documentSnapshot1.getId()).collection(name);
                                        reference2.addSnapshotListener(new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e)
                                            {
                                                for (QueryDocumentSnapshot documentSnapshot2:queryDocumentSnapshots)
                                                {
                                                    String name=documentSnapshot2.getString("Name");
                                                    String Address=documentSnapshot2.getString("Address");
                                                    String Rating=documentSnapshot2.getString("Rating");
                                                    String Website=documentSnapshot2.getString("Website");
                                                    String phone=documentSnapshot2.getString("Number");
                                                    String img=documentSnapshot2.getString("Image");
                                                    tourismModel obj=new tourismModel();
                                                    obj.setName(name);
                                                    obj.setAddress(Address);
                                                    obj.setRating(Rating);
                                                    obj.setNumber(phone);
                                                    obj.setWebsite(Website);
                                                    obj.setImage(img);
                                                    arrayList.add(obj);
                                                }
                                                adapter=new TourismAdapter(getApplicationContext(),arrayList);
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
        });
    }

}
