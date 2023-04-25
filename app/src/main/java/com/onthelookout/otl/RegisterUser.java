package com.onthelookout.otl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.onthelookout.otl.ui.myaccount.MyAccountFragment;
import com.onthelookout.otl.ui.notifications.NotificationsFragment;
import com.onthelookout.otl.ui.notifications.NotificationsViewModel;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterUser extends AppCompatActivity {
    // creating objects for accessing number and initiating otp generation
    EditText enterfullname, enteremail, enterdob, enterpwd, enterconfirmpwd, enternumber;
    RadioGroup entergender;
    RadioButton selectgender;
    DatePickerDialog picker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register_user);


        //assigning values
        enterfullname = findViewById(R.id.registered_full_name);
        enteremail = findViewById(R.id.registered_email);
        enterdob = findViewById(R.id.registered_dob);
        enterpwd = findViewById(R.id.registered_password);
        enterconfirmpwd = findViewById(R.id.registered_confirm_password);
        enternumber = findViewById(R.id.input_mobile_number);

        entergender = findViewById(R.id.registered_gender);
        entergender.clearCheck();

        //setting up Datepicker on edittext
        enterdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                //Date Picker
                picker = new DatePickerDialog(RegisterUser.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        enterdob.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });

        Button getotpbutton = findViewById(R.id.get_otp_button);

        ProgressBar progressBar = findViewById(R.id.progessbar_sending_otp);

        //assigning what happens when 'Get OTP' button is clicked
        getotpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedGenderId = entergender.getCheckedRadioButtonId();
                selectgender = findViewById(selectedGenderId);
                //obtain gender data


                String textFullName = enterfullname.getText().toString();
                String textEmail = enteremail.getText().toString();
                String textDob = enterdob.getText().toString();
                String textPwd = enterpwd.getText().toString();
                String textConfirmPwd = enterconfirmpwd.getText().toString();
                String textNum = enternumber.getText().toString();
                String textGender = null;          //have to verify first if the button was selected before obtaining value

                //Validate Mobile Number using matcher and pattern
                String mobileRegex = "[6-9][0-9]{9}"; //First no. can be {6,7,8,9} and rest no. can be of ay no.
                Matcher mobileMatcher;
                Pattern mobilePattern = Pattern.compile(mobileRegex);
                mobileMatcher = mobilePattern.matcher(textNum);


                // TextUtils returns a boolean value
                if (TextUtils.isEmpty(textFullName)) {
                    Toast.makeText(RegisterUser.this, "Please enter your full name", Toast.LENGTH_SHORT).show();
                    enterfullname.setError("Full Name is required");
                    enterfullname.requestFocus();
                } else if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(RegisterUser.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    enteremail.setError("Email is required");
                    enteremail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(RegisterUser.this, "Please re-enter your email", Toast.LENGTH_SHORT).show();
                    enteremail.setError("Valid Email is required");
                    enteremail.requestFocus();
                } else if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(RegisterUser.this, "Please enter your Date of Birth", Toast.LENGTH_SHORT).show();
                    enterdob.setError("Email is required");
                    enterdob.requestFocus();
                } else if (entergender.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(RegisterUser.this, "Please select your gender", Toast.LENGTH_SHORT).show();
                    selectgender.setError("Gender is required");
                    selectgender.requestFocus();
                } else if (!mobileMatcher.find()) {
                    Toast.makeText(RegisterUser.this, "Please re-enter your mobile number", Toast.LENGTH_SHORT).show();
                    enternumber.setError("Mobile no. is not valid");
                    enternumber.requestFocus();
                } else if (TextUtils.isEmpty(textPwd)) {
                    Toast.makeText(RegisterUser.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    enterpwd.setError("Email is required");
                    enterpwd.requestFocus();
                } else if (textPwd.length() < 8) {
                    Toast.makeText(RegisterUser.this, "Password should be atleast of 8 digits", Toast.LENGTH_LONG).show();
                    enterpwd.setError("Password is too weak");
                    enterpwd.requestFocus();
                } else if (TextUtils.isEmpty(textConfirmPwd)) {
                    Toast.makeText(RegisterUser.this, "Please confirm your password", Toast.LENGTH_SHORT).show();
                    enterconfirmpwd.setError("Re-enter your password");
                    enterconfirmpwd.requestFocus();
                } else if (!textPwd.equals(textConfirmPwd)) {
                    Toast.makeText(RegisterUser.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                    enterconfirmpwd.setError("Confirmed password doesn't match");
                    enterconfirmpwd.requestFocus();
                    enterpwd.clearComposingText();
                    ;
                    enterconfirmpwd.clearComposingText();
                } else {
                    textGender = selectgender.getText().toString();
                }


                // if the enter mobile section is not empty
                if (!enternumber.getText().toString().trim().isEmpty()) {

                    // if the entered number is 10 digit long, button redirects to next activity
                    if ((enternumber.getText().toString().trim()).length() == 10) {
                        progressBar.setVisibility(View.VISIBLE);
                        getotpbutton.setVisibility(View.INVISIBLE);

                        String finalTextGender = textGender;
                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + enternumber.getText().toString(),
                                60,
                                TimeUnit.SECONDS,
                                RegisterUser.this,
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
                                        Toast.makeText(RegisterUser.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendOTP, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        progressBar.setVisibility(View.GONE);
                                        getotpbutton.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(getApplicationContext(), EnterOTP.class);
                                        intent.putExtra("Mobile", enternumber.getText().toString());
                                        intent.putExtra("backendOTP", backendOTP);
                                        intent.putExtra("FullName", textFullName);
                                        intent.putExtra("Email", textEmail);
                                        intent.putExtra("Dob", textDob);
                                        intent.putExtra("Gender", selectgender.getText().toString());
                                        intent.putExtra("Password", textPwd);
//                                        exception(textFullName,textEmail,textDob, finalTextGender,textNum,textPwd);
//                                        Intent i = new Intent(getApplicationContext(), NotificationsFragment.class);
//                                        i.putExtra("Mobile", enternumber.getText().toString());
//                                        i.putExtra("FullName", textFullName);
//                                        i.putExtra("Email", textEmail);

                                        startActivity(intent);
                                    }
                                }
                        );

//                        Intent intent = new Intent(getApplicationContext(),EnterOTP.class);
//
//                        //taking the number input as an intent to our next activity
//                        intent.putExtra("Mobile",enternumber.getText().toString());
//                        startActivity(intent);
                    } else {
                        Toast.makeText(RegisterUser.this, "Please enter the correct 10-digit Mobile Number", Toast.LENGTH_SHORT).show();
                        enternumber.setError("Mobile No. should be 10 digits");
                        enternumber.requestFocus();
                    }
                } else {
                    Toast.makeText(RegisterUser.this, "Enter Your Mobile Number", Toast.LENGTH_SHORT).show();
                    enternumber.setError("Mobile No. is required");
                    enternumber.requestFocus();
                }
            }
        });

    }
}



