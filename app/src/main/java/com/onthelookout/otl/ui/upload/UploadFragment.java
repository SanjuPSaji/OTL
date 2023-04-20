package com.onthelookout.otl.ui.upload;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.onthelookout.otl.Complaint;
import com.onthelookout.otl.R;

import com.onthelookout.otl.databinding.FragmentUploadBinding;

public class UploadFragment extends Fragment {
    Activity context;
    private FragmentUploadBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();

        UploadViewModel uploadViewModel =
                new ViewModelProvider(this).get(UploadViewModel.class);

        binding = FragmentUploadBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textUpload;
        uploadViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;



    }
    public void onStart(){
        super.onStart();
        Button buttonfire = (Button) context.findViewById(R.id.button_fire);
        Button buttonearthquake = (Button) context.findViewById(R.id.button_earthquake);
        Button buttonkidnapping = (Button) context.findViewById(R.id.button_kidnapping);
        Button buttonassult = (Button) context.findViewById(R.id.button_assult);
        Button buttonrobbery = (Button) context.findViewById(R.id.button_robbery);
        Button buttonaccident = (Button) context.findViewById(R.id.button_accident);
        buttonfire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Complaint.class);
                intent.putExtra("type","Fire");
                startActivity(intent);
            }
        });

        buttonearthquake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Complaint.class);
                intent.putExtra("type","Earthquake");
                startActivity(intent);
            }
        });

        buttonkidnapping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Complaint.class);
                intent.putExtra("type","Kidnapping");
                startActivity(intent);
            }
        });

        buttonassult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Complaint.class);
                intent.putExtra("type","Assult");
                startActivity(intent);
            }
        });

        buttonrobbery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Complaint.class);
                intent.putExtra("type","Robbery");
                startActivity(intent);
            }
        });

        buttonaccident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Complaint.class);
                intent.putExtra("type","Accident");
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