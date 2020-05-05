package com.example.mysmartcity.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mysmartcity.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class ProfileDetails extends AppCompatActivity
{
    EditText wardnumber, Name,Address,phnumber;
    RadioGroup radioGroup;
    RadioButton radioButton,radioButton1,radioButton2;
    TextView mobilenumber,Proceed;

    FirebaseAuth mauth;
    FirebaseUser currentuser;
    String currendId;
    ProgressBar progressBar;
    RelativeLayout relativeLayout;

    FirebaseFirestore db=FirebaseFirestore.getInstance();
    CollectionReference reference=db.collection("UserDetails");





    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);
        Name=findViewById(R.id.name);
        Address=findViewById(R.id.adress);
        radioGroup=findViewById(R.id.radioGroup);
        wardnumber=findViewById(R.id.wardNo);
        progressBar=findViewById(R.id.progress);
        relativeLayout=findViewById(R.id.relative);
      //  mobilenumber=findViewById(R.id.mobNo);
        Proceed=findViewById(R.id.proceed);
        phnumber=findViewById(R.id.mobNo);

        mauth=FirebaseAuth.getInstance();

       // currentuser=mauth.getCurrentUser();

        radioButton1=findViewById(R.id.yes);
        radioButton2=findViewById(R.id.no);

       // final String mobNo=getIntent().getExtras().getString("mobile");
       // mobilenumber.setText(mobNo);

        Resources resources=getResources();
        final String[] ar=resources.getStringArray(R.array.wardNumber);

        wardnumber.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                View view=getLayoutInflater().inflate(R.layout.ward_dialog_box,null);
                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(ProfileDetails.this,android.R.layout.simple_list_item_1,ar);
                ListView listView=view.findViewById(R.id.listview);
                AlertDialog.Builder builder=new AlertDialog.Builder(ProfileDetails.this);
                listView.setAdapter(arrayAdapter);
                builder.setView(view);
                final AlertDialog dialog=builder.create();
                dialog.show();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        wardnumber.setText(ar[position]);
                        dialog.dismiss();
                    }
                });



            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
               // int seectedid=group.getCheckedRadioButtonId();
                radioButton=findViewById(checkedId);
                String s=radioButton.getText().toString();
                if (s.equals("No"))
                {
                    wardnumber.setText("N/A");
                    radioButton1.setChecked(false);
                    wardnumber.setEnabled(false);
                }
                else
                {
                    wardnumber.setText("");
                    wardnumber.setHint("Ward Number");
                    wardnumber.setEnabled(true);
                }

            }
        });

        Proceed.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

              //  relativeLayout.setEnabled(false);
                String name=Name.getText().toString().trim();
                String address=Address.getText().toString().trim();
                String wardNum=wardnumber.getText().toString();
                String PhoneNum=phnumber.getText().toString();

                saveToFirebase(name,address,wardNum,PhoneNum);
            }
        });


    }
    public void saveToFirebase(final String name, final String address, final String wardNum, final String PhoneNum)
    {
        if (name.isEmpty())
        {
            Name.setError("Name is required");
            Name.requestFocus();
            return;
        }
        if (address.isEmpty())
        {
            Address.setError("Address is required");
            Address.requestFocus();
            return;
        }
        if (wardNum.isEmpty())
        {
            wardnumber.setError("Select ward Number");
            wardnumber.requestFocus();
            return;
        }
        if (PhoneNum.isEmpty())
        {
            phnumber.setError("Invalid");
            phnumber.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        // String id1=id.getUid();
        reference.whereEqualTo("Mobile",PhoneNum)
                .addSnapshotListener(new EventListener<QuerySnapshot>()
                {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e)
                    {
                        if (queryDocumentSnapshots.isEmpty())
                        {

                            String[] data=new String[4];
                            data[0]=name;
                            data[1]=address;
                            data[2]=wardNum;
                            data[3]=PhoneNum;
                            Intent intent=new Intent(ProfileDetails.this,MobileOtp.class);
                            Bundle b=new Bundle();
                            b.putStringArray("BUNDLE",data);
                            intent.putExtras(b);
                            // intent.putStringArrayListExtra()
                            startActivity(intent);
                            progressBar.setVisibility(View.GONE);

                            // write return statemnt here

                        }
                        else
                            {
                                progressBar.setVisibility(View.GONE);
                               // relativeLayout.setEnabled(true);
                            Toast.makeText(getApplicationContext(),"Number is Already Registered,Pease login",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

}
