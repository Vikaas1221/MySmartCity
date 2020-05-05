package com.example.mysmartcity.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.mysmartcity.Adapters.complaintAdapter;
import com.example.mysmartcity.Model.complaint_fragmentModel;
import com.example.mysmartcity.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AllComplaintsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<complaint_fragmentModel> arrayList;
    RecyclerView.Adapter adapter;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    CollectionReference reference=db.collection("Complaints");
    FirebaseAuth mauth;
    ImageView nocomplaintsImage;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_complaints);
        recyclerView=findViewById(R.id.comlaintRecyclerView);
        nocomplaintsImage=findViewById(R.id.noComplaints);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        arrayList=new ArrayList<>();
        mauth=FirebaseAuth.getInstance();
        String userid=mauth.getUid();
        Log.d("hhh",""+userid);

        reference.whereEqualTo("Usedid",userid)
                .addSnapshotListener(new EventListener<QuerySnapshot>()
                {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e)
                    {
                        for (QueryDocumentSnapshot queryDocumentSnapshot:queryDocumentSnapshots)
                        {
                            if (queryDocumentSnapshot.exists())
                            {
                                String category=queryDocumentSnapshot.getString("Category");
                                String Description=queryDocumentSnapshot.getString("Description");
                                String status=queryDocumentSnapshot.getString("Status");
                                String Date=queryDocumentSnapshot.getString("Date");
                                String id=queryDocumentSnapshot.getString("ComplaintId");
                                Log.d("mnbvc",""+category+Description+status+Date+id);
                                complaint_fragmentModel obj=new complaint_fragmentModel();
                                obj.setCategory(category);
                                obj.setDescription(Description);
                                obj.setStatus(status);
                                obj.setDate(Date);
                                obj.setComplaintId(id);
                                arrayList.add(obj);
                            }
                            if (arrayList.isEmpty())
                            {
                                nocomplaintsImage.setVisibility(View.VISIBLE);
                            }
                            else
                                {
                                    nocomplaintsImage.setVisibility(View.GONE);
                                adapter = new complaintAdapter(getApplicationContext(), arrayList);
                                    recyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                            }

                        }
                    }
                });
    }
}
