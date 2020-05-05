package com.example.mysmartcity.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mysmartcity.Activity.MainActivity;
import com.example.mysmartcity.Activity.splashScreen;
import com.example.mysmartcity.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

public class ProfileFragment extends Fragment
{
    TextView p_name,p_address,p_mobile,p_wardnumber;
    Button logout;

    String currentuser;
    ProgressBar progressBar;



    FirebaseAuth mauth;
    FirebaseUser firebaseUser;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    CollectionReference reference=db.collection("UserDetails");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.profile_fragment,null);

        p_name=view.findViewById(R.id.name);
        p_mobile=view.findViewById(R.id.mobileno);
        p_address=view.findViewById(R.id.address);
        p_wardnumber=view.findViewById(R.id.ward_num);
        progressBar=view.findViewById(R.id.progress);
        logout=view.findViewById(R.id.logout);

        mauth=FirebaseAuth.getInstance();
        firebaseUser=mauth.getCurrentUser();

        currentuser=firebaseUser.getUid();

        reference.whereEqualTo("Userid",currentuser)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
        {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots)
            {
                for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                {
                    String name=documentSnapshot.getString("Name");
                    String addres=documentSnapshot.getString("Address");
                    String mobile=documentSnapshot.getString("Mobile");
                    String ward=documentSnapshot.getString("Ward");

                    Log.d("name",""+name+"/"+addres+"/"+mobile);

                            p_name.setText(name);
                            p_mobile.setText(mobile);
                            p_address.setText(addres);
                            p_wardnumber.setText(ward);
                }
            }
        })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Log.d("erroref",""+e.getMessage().toString());
                    }
                });

        logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                progressBar.setVisibility(View.VISIBLE);
                try {
                    Thread.sleep(1000);
                    mauth.signOut();
                    Intent intent=new Intent(getActivity(), splashScreen.class);
                    startActivity(intent);
                    getActivity().finish();

                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                }

            }
        });







        return view;
    }
}
