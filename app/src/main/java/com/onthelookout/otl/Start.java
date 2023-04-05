package com.onthelookout.otl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Start extends AppCompatActivity {

    private FirebaseAuth authProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //Set the title
        getSupportActionBar().setTitle("On The Lookout");
        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        //Open login Activity
        Button buttonLogin = findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Start.this, Login.class);
                startActivity(intent);
            }
        });

        Button buttonRegister = findViewById(R.id.button_register);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Start.this, RegisterUser.class);
                startActivity(intent);
            }
        });


    }

    //checking if User is already logged in. if yes, main activity start
    @Override
    protected void onStart() {
        super.onStart();

        if(authProfile.getCurrentUser() !=null){

            //Start the userProfileActivity
            startActivity(new Intent(Start.this, MainActivity.class));
            finish();

        }
    }
}