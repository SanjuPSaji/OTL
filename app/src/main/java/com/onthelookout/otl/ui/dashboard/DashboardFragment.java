package com.onthelookout.otl.ui.dashboard;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.onthelookout.otl.Complaint;
import com.onthelookout.otl.R;
import com.onthelookout.otl.databinding.FragmentDashboardBinding;

//public class DashboardFragment extends Fragment {
//    Activity context;
//    myadapter adapter;
//    private FragmentDashboardBinding binding;
//
//
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        context = getActivity();
//
//        DashboardViewModel dashboardViewModel =
//                new ViewModelProvider(this).get(DashboardViewModel.class);
//
//        binding = FragmentDashboardBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//
////        RecyclerView recview_dasboard = (RecyclerView) context.findViewById(R.id.recview_dasboard);
////        recview_dasboard.setLayoutManager(new LinearLayoutManager(getContext()));
////
////        FirebaseRecyclerOptions<model_dashboard> options =
////                new FirebaseRecyclerOptions.Builder<model_dashboard>()
////                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Complaints"), model_dashboard.class)
////                        .build();
////
////        adapter = new myadapter(options);
////        recview_dasboard.setAdapter(adapter);
//
////        final TextView textView = binding.textDashboard;
////        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
//        return root;
//    }
//
//    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        RecyclerView recview_dasboard = (RecyclerView) context.findViewById(R.id.recview_dasboard);
//        recview_dasboard.setLayoutManager(new LinearLayoutManager(context));                                            //possible error
//
//        FirebaseRecyclerOptions<model_dashboard> options =
//                new FirebaseRecyclerOptions.Builder<model_dashboard>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Complaints"), model_dashboard.class)
//                        .build();
//
//        adapter = new myadapter(options);
//        recview_dasboard.setAdapter(adapter);
//
//
//    }
//
//@Override
//    public void onStart(){
//        super.onStart();
////        RecyclerView recview_dasboard = (RecyclerView) context.findViewById(R.id.recview_dasboard);
////        recview_dasboard.setLayoutManager(new LinearLayoutManager(context));                                            //possible error
////
////        FirebaseRecyclerOptions<model_dashboard> options =
////                new FirebaseRecyclerOptions.Builder<model_dashboard>()
////                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Complaints"), model_dashboard.class)
////                        .build();
////
////        adapter = new myadapter(options);
////        recview_dasboard.setAdapter(adapter);
////
//        adapter.startListening();
//
//    }
//
//    @Override
//    public void onStop(){
//        super.onStop();
//        if(adapter != null) {
//            adapter.stopListening();
//        }
//    }
//
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//      //  adapter.stopListening();
//        binding = null;
//    }
//}

public class DashboardFragment extends Fragment {
    Activity context;
    myadapter adapter;
    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();

        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recview_dasboard = root.findViewById(R.id.recview_dasboard); // Update to use root view
        recview_dasboard.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<model_dashboard> options =
                new FirebaseRecyclerOptions.Builder<model_dashboard>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Complaints"), model_dashboard.class)
                        .build();

        adapter = new myadapter(options);
        recview_dasboard.setAdapter(adapter);

//        final TextView textView = binding.textDashboard;
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart(){
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop(){
        super.onStop();
        if(adapter != null) {
            adapter.stopListening();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
