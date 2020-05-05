package com.example.mysmartcity.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.example.mysmartcity.R;
import com.google.firebase.auth.FirebaseAuth;

public class splashScreen extends AppCompatActivity implements View.OnClickListener {
    Button register,login;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_register);
        register=findViewById(R.id.Register);
        login=findViewById(R.id.Login);
        login.setOnClickListener(this);
        register.setOnClickListener(this);

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseAuth mauth=FirebaseAuth.getInstance();
        if (mauth.getCurrentUser()!=null)
        {
            Intent intent=new Intent(splashScreen.this,HomeScreen.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.Register:
            {
                Intent intent=new Intent(splashScreen.this,ProfileDetails.class);
                startActivity(intent);


                break;
            }
            case R.id.Login:
            {
                Intent intent=new Intent(splashScreen.this,MainActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
