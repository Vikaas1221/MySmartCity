package com.example.mysmartcity.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class ComplaintsFragment extends Fragment
{
    RecyclerView recyclerView;
    ArrayList<complaint_fragmentModel> arrayList;
    RecyclerView.Adapter adapter;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    CollectionReference reference=db.collection("Complaints");
    FirebaseAuth mauth;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.complaints_fragment,null);
        recyclerView=view.findViewById(R.id.comlaintRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
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
                            adapter=new complaintAdapter(getContext(),arrayList);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback=new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed()
            {
                Fragment fragment=new DashboardFragment();
                FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.framelayout,fragment);
                //transaction.addToBackStack(null);
                transaction.commit();
               // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,fragment).commit();


            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this,callback);
    }
}
