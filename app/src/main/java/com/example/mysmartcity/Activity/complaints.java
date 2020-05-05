package com.example.mysmartcity.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mysmartcity.Model.complaintModel;
import com.example.mysmartcity.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class complaints extends AppCompatActivity implements View.OnClickListener
{
    EditText Name,MobileNumber,WardNumber,Category,SubCategory,Address,Description;
    ImageView addImage;
    TextView lodgeFile,added;
    String[] category,wardnumber;

    Uri imageUri;

    FirebaseAuth mauth;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    CollectionReference complaints=db.collection("Complaints");

    StorageReference reference;
    Toolbar toolbar;
    ImageView backbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);
        toolbar=findViewById(R.id.toolbar);
        Name=findViewById(R.id.name);
        MobileNumber=findViewById(R.id.mobileNumber);
        added=findViewById(R.id.addedsucessfully);
        WardNumber=findViewById(R.id.wardNo);
        Category=findViewById(R.id.category);
        Address=findViewById(R.id.adress);
        Description=findViewById(R.id.description);
        addImage=findViewById(R.id.addImageIcon);
        lodgeFile=findViewById(R.id.fileComplaint);
        Resources resources=getResources();
        category=resources.getStringArray(R.array.category);
        wardnumber=resources.getStringArray(R.array.wardNumber);
        backbutton=findViewById(R.id.back);


        mauth=FirebaseAuth.getInstance();

        reference= FirebaseStorage.getInstance().getReference("complaintsImage").child("complaintImg"+ Timestamp.now().getSeconds());


        lodgeFile.setOnClickListener(this);
        WardNumber.setOnClickListener(this);
        Category.setOnClickListener(this);
        addImage.setOnClickListener(this);
        backbutton.setOnClickListener(this);





    }

    @Override
    public void onClick(View v)
    {
        int id=v.getId();
        switch (id)
        {
            case R.id.wardNo:
            {
                String s="wardNo";
                showDialog(wardnumber,s);

                break;
            }
            case R.id.category:
            {
                String s="category";
                showDialog(category,s);
                break;
            }
            case R.id.addImageIcon:
            {
                selectImage();
                break;
            }
            case R.id.fileComplaint:
            {
                saveToDatabase();
                break;
            }
//            case R.id.back:
//            {
//                startActivity(new Intent(com.example.mysmartcity.Activity.complaints.this,HomeScreen.class));
//                finish();
//            }

        }
    }
    public void showDialog(final String[] ar, final String s)
    {
        View view=getLayoutInflater().inflate(R.layout.ward_dialog_box,null);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(complaints.this,android.R.layout.simple_list_item_1,ar);
        ListView listView=view.findViewById(R.id.listview);
        AlertDialog.Builder builder=new AlertDialog.Builder(complaints.this);
        listView.setAdapter(arrayAdapter);
        builder.setView(view);
        final AlertDialog dialog=builder.create();
        dialog.show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (s.equals("wardNo"))
                {
                    WardNumber.setText(ar[position]);
                    dialog.dismiss();
                }
                else
                {
                    Category.setText(ar[position]);
                    dialog.dismiss();
                }
            }
        });

    }

    public void selectImage()
    {
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,123);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==123&&resultCode==RESULT_OK)
        {
            if (data!=null)
            {
                imageUri=data.getData();
                String s=imageUri.getPath().toString();
                Log.d("uri",s);
                addImage.setVisibility(View.INVISIBLE);
                added.setVisibility(View.VISIBLE);
            }
            else
            {
                added.setVisibility(View.INVISIBLE);
                addImage.setVisibility(View.VISIBLE);
            }
        }
    }
    public void saveToDatabase()
    {
        final String name=Name.getText().toString().trim();
        final String Mobile=MobileNumber.getText().toString().trim();
        final String ward=WardNumber.getText().toString().trim();
        final String category=Category.getText().toString().trim();
        final String address=Address.getText().toString().trim();
        final String description=Description.getText().toString().trim();
        if (name.isEmpty())
        {
            Name.setError("Name is required");
            Name.requestFocus();
            return;
        }
        if (Mobile.isEmpty())
        {
            MobileNumber.setError("Mobile Number is required");
            MobileNumber.requestFocus();
            return;
        }
        if (Mobile.length()<10)
        {
            MobileNumber.setError("Mobile Number wrong");
            MobileNumber.requestFocus();
            return;
        }
        if (ward.isEmpty())
        {
            WardNumber.setError("Ward Number is required");
            WardNumber.requestFocus();
            return;
        }
        if (category.isEmpty())
        {
            Category.setError("Category is required");
            Category.requestFocus();
            return;
        }
        if (address.isEmpty())
        {
            Address.setError("Address is required");
            Address.requestFocus();
            return;
        }
        if (description.isEmpty())
        {
            Description.setError("Descrition is required");
            Description.requestFocus();
            return;
        }
        final String dept=department(category);
        Random random=new Random();
         int r=random.nextInt(1000000);
        final String complaintid=String.valueOf(r);


        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        final String thisDate = currentDate.format(todayDate);

        reference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        String img=taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                        String currentid=mauth.getUid();


                        Map<String,String> map=new HashMap<>();
                        map.put("Name",name);
                        map.put("Mobile",Mobile);
                        map.put("WardNo",ward);
                        map.put("Category",category);
                        map.put("Address",address);
                        map.put("Description",description);
                        map.put("ImgUrl",img);
                        map.put("Usedid",currentid);
                        map.put("Status","Pending");
                        map.put("ComplaintId",complaintid);
                        map.put("Date",thisDate);
                        map.put("Department",dept);

                        complaints.add(map)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                                {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference)
                                    {
                                        Toast.makeText(getApplicationContext(),"Complaint Forwaded sucessufully",Toast.LENGTH_SHORT).show();


                                    }
                                })
                                .addOnFailureListener(new OnFailureListener()
                                {
                                    @Override
                                    public void onFailure(@NonNull Exception e)
                                    {
                                        Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });

    }
    public String department(String category)
    {
        String Dept="";
        if (category.equals("Water Supply")||category.equals("Street Light")||category.equals("Water Drain")||category.equals("Pollution Control")||category.equals("Roads and Traffic")||category.equals("Health"))
        {
            Dept="PWD";
        }
        else if (category.equals("Garbage")||category.equals("Sewage")||category.equals("Cleaning and Sweeping")||category.equals("Animal Issues"))
        {
            Dept="Nagar Nigam";
        }
        else if (category.equals("Pest Control")||category.equals("Horiculture"))
        {
            Dept="Agriculture";
        }
        else if (category.equals("Fire and Saftey"))
        {
            Dept="Fire Depatment";
        }
        else
        {
            Dept="Electricity";
        }
        return Dept;
    }
}
