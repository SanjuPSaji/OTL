package com.onthelookout.otl.ui.notifications;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.onthelookout.otl.R;
import com.onthelookout.otl.databinding.FragmentNotificationsBinding;
import com.onthelookout.otl.Message;
import com.onthelookout.otl.ui.upload.UploadViewModel;

public class NotificationsFragment extends Fragment {
    private FragmentNotificationsBinding binding;
    Activity context;


//    private  final List<MessagesList> messagesLists = new ArrayList<>();
//    private String mobile,email,name,uid;
//    private RecyclerView messagesRecyclerView;

//    private ImageView userProfilePict;
//    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://otl-otp-default-rtdb.firebaseio.com/");

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        context = getActivity();

        UploadViewModel uploadViewModel =
                new ViewModelProvider(this).get(UploadViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//
//        userProfilePict = (ImageView) rootView.findViewById(R.id.userProfilePic);
//        messagesRecyclerView = (RecyclerView) rootView.findViewById(R.id.messageRecyclerView);
//
//
////        name = getActivity().getIntent().getStringExtra("FullName");
////        uid = "JniHf4mJoxOm0L7xaVZVvwy2n2M2";
//        SharedPreferences sharedPreferences = context.getSharedPreferences("UID", Context.MODE_PRIVATE);
//        uid = sharedPreferences.getString("UID","Default");
//        System.out.println(uid);
////        uid = getActivity().getIntent().getStringExtra("UID");
////        mobile = "+91"+getActivity().getIntent().getStringExtra("Mobile");
//
//        messagesRecyclerView.setHasFixedSize(true);
//        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(context));
//
//
//
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                final String profilePicUrl = snapshot.child("Registered Users").child("uid").child("profilepicture").getValue(String.class);
////                final String profilePicUrl = "https://w0.peakpx.com/wallpaper/979/89/HD-wallpaper-purple-smile-design-eye-smily-profile-pic-face.jpg";
//
//                final String name = snapshot.child("Registered Users").child("uid").child("name").getValue(String.class);
//                final String email = snapshot.child("Registered Users").child("uid").child("email").getValue(String.class);
//                final String mobile = snapshot.child("Registered Users").child("uid").child("mobile").getValue(String.class);
//
//
//
//
//                if(profilePicUrl!= null && !profilePicUrl.isEmpty()){
////                    Bitmap bmp = BitmapFactory.decodeFile(new java.net.URL(profilePicUrl).openStream());
////                    imageview.setImageBitmap(bmp);
//                    Glide.with(context).load(profilePicUrl).into(userProfilePict);
////                    Picasso.get().load(profilePicUrl).into(userProfilePict);
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                messagesLists.clear();
//
//                for (DataSnapshot dataSnapshot : snapshot.child("Registered Users").getChildren()){
//
//                    final String getUid = dataSnapshot.getKey();
//
//                    if (!getUid.equals(uid)){
//                        final String getName = dataSnapshot.child("name").getValue(String.class);
//                        final String getMobile = dataSnapshot.child("mobile").getValue(String.class);
//                        final String getProfile = dataSnapshot.child("profilepic").getValue(String.class);
//
//                        MessagesList messagesList = new MessagesList(getName, getMobile, "", getProfile, 0);
//                        messagesLists.add(messagesList);
//
//
//                    }
//                }
//
//                messagesRecyclerView.setAdapter(new MessagesAdapter(messagesLists, context));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
        return root;
    }

    public void onStart(){
        super.onStart();
        Button startChat = (Button) context.findViewById(R.id.startChatting);


        startChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Button", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, Message.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}