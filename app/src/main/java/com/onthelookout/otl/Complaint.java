package com.onthelookout.otl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.onthelookout.otl.ui.home.HomeFragment;

import java.util.HashMap;

public class Complaint extends AppCompatActivity {
EditText info_complaint;
Button submit_complaint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        info_complaint = findViewById(R.id.info_complaint);
        submit_complaint = findViewById(R.id.submit_complaint);

        String type = getIntent().getStringExtra("type");
        submit_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String ,Object> m = new HashMap<String, Object>();
                m.put("Information",info_complaint.getText().toString());
                m.put("Type",type);
                FirebaseDatabase.getInstance().getReference().child("Complaints").push().setValue(m);

                Intent intent = new Intent(Complaint.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}