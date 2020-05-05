package com.example.mysmartcity.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mysmartcity.Activity.MainActivity;
import com.example.mysmartcity.Activity.complaints;
import com.example.mysmartcity.Adapters.DashboardAdapter;
import com.example.mysmartcity.Model.dashboardModel;
import com.example.mysmartcity.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DashboardFragment extends Fragment
{
    RecyclerView recyclerView;
    ArrayList<dashboardModel> arrayList;
    RecyclerView.Adapter adapter;

    ProgressBar progressBar;



    FirebaseAuth mauth;
    public static FirebaseFirestore db=FirebaseFirestore.getInstance();
    public static CollectionReference reference=db.collection("Mycity");

    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view=getLayoutInflater().inflate(R.layout.dashboard_fragment,null);
        progressBar=view.findViewById(R.id.progress);

        Toolbar toolbar=view.findViewById(R.id.toolbar);
        AppCompatActivity activity=(AppCompatActivity)getActivity();
        assert activity != null;
        activity.setSupportActionBar(toolbar);

        recyclerView=view.findViewById(R.id.dashboardRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(container.getContext(),3));
        recyclerView.setHasFixedSize(true);
        arrayList=new ArrayList<>();
        setHasOptionsMenu(true);
        progressBar.setVisibility(View.VISIBLE);
        reference.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
                {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                    {

                        for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                        {
                            dashboardModel obj=new dashboardModel();
                            obj.setImage(documentSnapshot.getString("Image"));
                            obj.setName(documentSnapshot.getString("Name"));
                            arrayList.add(obj);
                        }
                        adapter=new DashboardAdapter(getContext(),arrayList);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(),e.getMessage().toString(),Toast.LENGTH_SHORT).show();
                    }
                });



        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.addcomplaint,menu);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {

        int id=item.getItemId();
        if (id== R.id.newcomplaint)
        {
            startActivity(new Intent(getContext(), complaints.class));
        }
        return super.onOptionsItemSelected(item);

    }
}
