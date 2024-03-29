package com.onthelookout.otl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.onthelookout.otl.ui.myaccount.MyAccountFragment;
import com.onthelookout.otl.ui.notifications.NotificationsFragment;

import java.util.concurrent.TimeUnit;

public class EnterOTP extends AppCompatActivity {
    EditText inputnum1, inputnum2, inputnum3, inputnum4, inputnum5, inputnum6;
    String getotpbackend,textFullName, textEmail, textDob, textPwd, textGender, textNum, profilepic,numasid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        setContentView(R.layout.activity_enter_otp);

        Button otpverifybutton = findViewById(R.id.otp_submit_button);

        inputnum1 = findViewById(R.id.inputotp1);
        inputnum2 = findViewById(R.id.inputotp2);
        inputnum3 = findViewById(R.id.inputotp3);
        inputnum4 = findViewById(R.id.inputotp4);
        inputnum5 = findViewById(R.id.inputotp5);
        inputnum6 = findViewById(R.id.inputotp6);

        //took the number input and showing here
        TextView textview = findViewById(R.id.input_mobile_number_shown);
        textview.setText(String.format(
                "+91-%s", getIntent().getStringExtra("Mobile")
        ));

        getotpbackend = getIntent().getStringExtra("backendOTP");
        textFullName = getIntent().getStringExtra("FullName");
        textEmail = getIntent().getStringExtra("Email");
        textDob = getIntent().getStringExtra("Dob");
        textPwd= getIntent().getStringExtra("Password");
        textGender =getIntent().getStringExtra("Gender");
        textNum = "+91-"+getIntent().getStringExtra("Mobile");
        numasid = getIntent().getStringExtra("Mobile");
        profilepic = " ";


        final ProgressBar progressBarVerifyOTP = findViewById(R.id.progessbar_verifying_otp);


        otpverifybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!inputnum1.getText().toString().trim().isEmpty() && !inputnum2.getText().toString().trim().isEmpty() && !inputnum3.getText().toString().trim().isEmpty() && !inputnum4.getText().toString().trim().isEmpty() && !inputnum5.getText().toString().trim().isEmpty() && !inputnum6.getText().toString().trim().isEmpty()) {
                    String entercodeotp = inputnum1.getText().toString() +
                            inputnum2.getText().toString() +
                            inputnum3.getText().toString() +
                            inputnum4.getText().toString() +
                            inputnum5.getText().toString() +
                            inputnum6.getText().toString();

                    if (getotpbackend != null) {
                        progressBarVerifyOTP.setVisibility(View.VISIBLE);
                        otpverifybutton.setVisibility(View.INVISIBLE);

                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                                getotpbackend, entercodeotp
                        );
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressBarVerifyOTP.setVisibility(View.GONE);
                                        otpverifybutton.setVisibility(View.VISIBLE);

                                        if (task.isSuccessful()) {
                                            registerUser(textFullName,textEmail,textDob,textGender,textNum,textPwd,profilepic);
                                        } else {
                                            Toast.makeText(EnterOTP.this, "Enter the correct OTP", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                    } else {
                        Toast.makeText(EnterOTP.this, "Please check internet connection", Toast.LENGTH_SHORT).show();
                    }


//                    Toast.makeText(EnterOTP.this,"Verifying OTP", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EnterOTP.this, "Enter All 6-digit OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });

        numberotpmove();//function initializer

        TextView resendOTP = findViewById(R.id.resend_otp);
        resendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + getIntent().getStringExtra("Mobile"),
                        15,
                        TimeUnit.SECONDS,
                        EnterOTP.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(EnterOTP.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newbackendOTP, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                getotpbackend = newbackendOTP;
                                Toast.makeText(EnterOTP.this, "OTP sent succesfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });





    }

    // Register User using the credentials given
    private void registerUser(String textFullName, String textEmail, String textDob, String textGender, String textNum, String textPwd, String profilepic) {
    FirebaseAuth auth =FirebaseAuth.getInstance();
    
    auth.createUserWithEmailAndPassword(textEmail, textPwd).addOnCompleteListener(EnterOTP.this,
            new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){
                Toast.makeText(EnterOTP.this, "User registered successfully", Toast.LENGTH_LONG).show();
                FirebaseUser firebaseUser = auth.getCurrentUser();

//                //Update Display Name of user
                UserProfileChangeRequest profileChangeRequest =
                        new UserProfileChangeRequest.Builder().setDisplayName(textFullName).build();
                firebaseUser.updateProfile(profileChangeRequest);

                //Enter User Data into the Firebase Realtime Database
                ReadwriteUserDetails writeUserDetails = new ReadwriteUserDetails(textFullName,textEmail, textDob, textGender, textNum, profilepic);

                //Extracting user reference from Database for "Registered Users"
                DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");

                String uid = firebaseUser.getUid();

                referenceProfile.child(uid).setValue(writeUserDetails).addOnCompleteListener
                        (new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){
                            //Send verification Email
//                            firebaseUser.sendEmailVerification();

                            Toast.makeText(EnterOTP.this, "User registered successfully.",
                                    Toast.LENGTH_LONG).show();

                            //save mobile to memory
//                            MemoryData.saveUID(uid,EnterOTP.this);
//                            SharedPreferences sharedPreferences = getSharedPreferences("UID", Context.MODE_PRIVATE);
//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//                            editor.putInt(getString("UID"),uid);
//                            editor.apply();
//                            sharedPreferences.edit().putString("UID",uid).commit();


                            //Open User Profile after successful registration
                            Intent intent = new Intent(EnterOTP.this, Message.class);
                            intent.putExtra("UID", uid);

                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                    Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish(); //to close Register Activity
                        } else {
                            Toast.makeText(EnterOTP.this, "User registration failed. Please try again",
                                    Toast.LENGTH_LONG).show();
                        }



                    }
                });
            } else {

            }
        }
    });
    }

    //function for when you type a single digit in an OTP box, the input bar moves to the next one
    private void numberotpmove() {

        inputnum1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputnum2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputnum2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputnum3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputnum3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputnum4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputnum4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputnum5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputnum5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputnum6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


}