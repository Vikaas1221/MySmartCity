package com.example.mysmartcity.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mysmartcity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.concurrent.TimeUnit.SECONDS;

public class MobileOtp extends AppCompatActivity
{
    EditText OTP;
    TextView login;
    TextView counter,setMobile;
    RelativeLayout resendCode;

    String[] data=null;

    String codeSent;
    String mobileNumber;

    int count=60;

    FirebaseAuth mauth;

    FirebaseFirestore db=FirebaseFirestore.getInstance();
    CollectionReference ref=db.collection("UserDetails");
    FirebaseUser user;

    PhoneAuthProvider.ForceResendingToken resendingToken;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_otp);
        mauth=FirebaseAuth.getInstance();
        OTP=findViewById(R.id.otp);
        setMobile=findViewById(R.id.mobileotp);
        login=findViewById(R.id.loginButton);
        resendCode=findViewById(R.id.resendCodeLayout);
        counter=findViewById(R.id.timecounter);

      //  setMobile.setText("Enter your OTP code here sent to "+mobileNumber);
        data=getIntent().getExtras().getStringArray("BUNDLE");

        if (data==null)
        {
            mobileNumber=getIntent().getExtras().getString("mobileNumber");
            sendVerificationCode(mobileNumber);
        }
        else {
            sendVerificationCode(data[3]);
        }





        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String code=OTP.getText().toString();
                if (code.isEmpty()||code.length()<6)
                {
                    OTP.setError("wrong");
                    OTP.requestFocus();
                    return;
                }
                verifyVerificationCoe(code);
            }
        });

               resendCode.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Toast.makeText(getApplicationContext(),"Code sent",Toast.LENGTH_SHORT).show();
                        sendVerificationCode(mobileNumber);

                    }
                });






    }

    private void sendVerificationCode(String mobileNumber)
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+mobileNumber,60, SECONDS,this,mCallbacks);
    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential)
        {

            String code=phoneAuthCredential.getSmsCode();
            if (code!=null)
            {
                OTP.setText(code);
                verifyVerificationCoe(code);


            }
            else
            {
                signInWithCredintial(phoneAuthCredential);
            }

        }

        @Override
        public void onVerificationFailed(FirebaseException e)
        {
            Toast.makeText(MobileOtp.this,e.getMessage().toString(),Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken)
        {
            super.onCodeSent(s, forceResendingToken);
            codeSent=s;
            resendingToken=forceResendingToken;
        }

        @Override
        public void onCodeAutoRetrievalTimeOut(String s)
        {
            super.onCodeAutoRetrievalTimeOut(s);

        }
    };
    public void verifyVerificationCoe(String codeSent2)
    {
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(codeSent,codeSent2);
        signInWithCredintial(credential);
    }

    private void signInWithCredintial(PhoneAuthCredential credential)
    {
        if (data==null)
        {
            mauth.signInWithCredential(credential)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(),"sucessed",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(MobileOtp.this, HomeScreen.class);
                                intent.putExtra("mobile",mobileNumber);
                                startActivity(intent);
                                finish();
                            }
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
        else
        {
            mauth.signInWithCredential(credential)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                user=mauth.getCurrentUser();

                                String currendId=user.getUid();
                                Map<String,String> map=new LinkedHashMap<>();
                                map.put("Name",data[0]);
                                map.put("Address",data[1]);
                                map.put("Ward",data[2]);
                                map.put("Mobile",data[3]);
                                map.put("Userid",currendId);
                                ref.add(map)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                                        {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference)
                                            {
                                                Toast.makeText(getApplicationContext(),"Added sucessfuly",Toast.LENGTH_SHORT).show();
                                                Intent intent=new Intent(MobileOtp.this,HomeScreen.class);
                                                startActivity(intent);
                                                finish();
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
//
//                                Toast.makeText(getApplicationContext(),"sucessed",Toast.LENGTH_SHORT).show();
//                                Intent intent=new Intent(MobileOtp.this, HomeScreen.class);
//                                intent.putExtra("mobile",mobileNumber);
//                                startActivity(intent);
//                                finish();
                            }
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

    }

}
