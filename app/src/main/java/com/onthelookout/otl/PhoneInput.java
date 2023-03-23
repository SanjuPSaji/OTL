package com.onthelookout.otl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneInput extends AppCompatActivity {
    // creating objects for accessing number and initiating otp generation
    EditText enternumber;
    Button getotpbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_input);

        //assigning values
        enternumber = findViewById(R.id.input_mobile_number);
        getotpbutton = findViewById(R.id.get_otp_button);

        ProgressBar progressBar = findViewById(R.id.progessbar_sending_otp);

        //assigning what happens when 'Get OTP' button is clicked
        getotpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // if the enter mobile section is not empty
                if(!enternumber.getText().toString().trim().isEmpty()){

                    // if the entered number is 10 digit long, button redirects to next activity
                    if((enternumber.getText().toString().trim()).length() == 10){
                        progressBar.setVisibility(View.VISIBLE);
                        getotpbutton.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + enternumber.getText().toString(),
                                60,
                                TimeUnit.SECONDS,
                                PhoneInput.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progressBar.setVisibility(View.GONE);
                                        getotpbutton.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressBar.setVisibility(View.GONE);
                                        getotpbutton.setVisibility(View.VISIBLE);
                                        Toast.makeText(PhoneInput.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendOTP, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        progressBar.setVisibility(View.GONE);
                                        getotpbutton.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(getApplicationContext(),EnterOTP.class);
                                        intent.putExtra("Mobile",enternumber.getText().toString());
                                        intent.putExtra("backendOTP",backendOTP);
                                        startActivity(intent);
                                    }
                                }
                        );

//                        Intent intent = new Intent(getApplicationContext(),EnterOTP.class);
//
//                        //taking the number input as an intent to our next activity
//                        intent.putExtra("Mobile",enternumber.getText().toString());
//                        startActivity(intent);
                    }else{
                        Toast.makeText(PhoneInput.this,"Please enter the correct 10-digit Mobile Number",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(PhoneInput.this,"Enter Your Mobile Number",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}