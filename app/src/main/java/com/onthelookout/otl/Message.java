package com.onthelookout.otl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onthelookout.otl.R;
import com.onthelookout.otl.databinding.FragmentNotificationsBinding;
import com.onthelookout.otl.messages.MessagesAdapter;
import com.onthelookout.otl.messages.MessagesList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Message extends AppCompatActivity {
    private  final List<MessagesList> messagesLists = new ArrayList<>();
    Activity context;
    private String mobile,email,name,uid;
    private RecyclerView messagesRecyclerView;

    private ImageView userProfilePict;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        userProfilePict = findViewById(R.id.userProfilePic);
        messagesRecyclerView = findViewById(R.id.messageRecyclerView);

//        name = getActivity().getIntent().getStringExtra("FullName");
        uid = "ECUIMHBFW6P8kWCyVSwAhtfRdl03";
//        SharedPreferences sharedPreferences = context.getSharedPreferences("UID", Context.MODE_PRIVATE);
//        uid = sharedPreferences.getString("UID","Default");
//        uid = getActivity().getIntent().getStringExtra("UID");
//        mobile = "+91"+getActivity().getIntent().getStringExtra("Mobile");

        messagesRecyclerView.setHasFixedSize(true);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://otl-otp-default-rtdb.firebaseio.com/").child("Registered Users");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



//                final String profilePicUrl = "https://w0.peakpx.com/wallpaper/979/89/HD-wallpaper-purple-smile-design-eye-smily-profile-pic-face.jpg";
                final String profilePicUrl = snapshot.child(uid).child("profilepicture").getValue(String.class);
                final String name = snapshot.child(uid).child("name").getValue(String.class);
                final String email = snapshot.child(uid).child("email").getValue(String.class);
                final String mobile = snapshot.child(uid).child("mobile").getValue(String.class);




                if(profilePicUrl!= null && !profilePicUrl.isEmpty()){
//                    Bitmap bmp = BitmapFactory.decodeFile(new java.net.URL(profilePicUrl).openStream());
//                    imageview.setImageBitmap(bmp);
//                    Glide.with(context).load(profilePicUrl).into(userProfilePict);
                    Picasso.get().load(profilePicUrl).into(userProfilePict);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                messagesLists.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    final String getUid = dataSnapshot.getKey();

                    if (!getUid.equals(uid)){
                        final String getName = dataSnapshot.child("name").getValue(String.class);
                        final String getMobile = dataSnapshot.child("mobile").getValue(String.class);
                        final String getProfile = dataSnapshot.child("profilepicture").getValue(String.class);

                        MessagesList messagesList = new MessagesList(getName, getMobile, "", getProfile, 0);
                        messagesLists.add(messagesList);


                    }
                }

                messagesRecyclerView.setAdapter(new MessagesAdapter(messagesLists, Message.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}