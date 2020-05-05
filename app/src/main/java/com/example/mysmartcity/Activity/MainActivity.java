package com.example.mysmartcity.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mysmartcity.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity
{
    EditText phonenumber;
    TextView getOtpButton;
    FirebaseAuth mauth;
    ProgressBar progressBar;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    CollectionReference reference=db.collection("UserDetails");


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phonenumber=findViewById(R.id.mobileNumber);
        getOtpButton=findViewById(R.id.sendOtp);
        progressBar=findViewById(R.id.progress);
        mauth=FirebaseAuth.getInstance();


        getOtpButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try {
                    progressBar.setVisibility(View.VISIBLE);

                    //Thread.sleep(1000);
                    final String mobilenumber=phonenumber.getText().toString().trim();
                    if (mobilenumber.isEmpty())
                    {
                        phonenumber.setError("Required field");
                        phonenumber.requestFocus();
                        return;
                    }
                    if (mobilenumber.length()!=10)
                    {
                        phonenumber.setError("Invalid number");
                        phonenumber.requestFocus();
                        return;
                    }

                    getOtpButton.setEnabled(false);

                    reference.whereEqualTo("Mobile",mobilenumber)
                            .addSnapshotListener(new EventListener<QuerySnapshot>()
                            {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e)
                                {
                                    if (!queryDocumentSnapshots.isEmpty())
                                    {
                                        progressBar.setVisibility(View.GONE);
                                        getOtpButton.setEnabled(true);
                                        Intent intent=new Intent(MainActivity.this, MobileOtp.class);
                                        intent.putExtra("mobileNumber",mobilenumber);
                                        startActivity(intent);
                                    }
                                    else {
                                        progressBar.setVisibility(View.GONE);
                                        getOtpButton.setEnabled(true);
                                        Toast.makeText(getApplicationContext(),"Number is not register! Please Register it first",Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            });


                } catch (Exception e)
                {
                    progressBar.setVisibility(View.GONE);
                    getOtpButton.setEnabled(true);
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseAuth mauth=FirebaseAuth.getInstance();
        if (mauth.getCurrentUser()!=null)
        {
            Intent intent=new Intent(MainActivity.this,HomeScreen.class);
            startActivity(intent);
            finish();
        }
    }
    public void checkForLogin(String mob)
    {

    }
}
