package com.onthelookout.otl.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.onthelookout.otl.R;


public class myadapter extends FirebaseRecyclerAdapter<model_dashboard,myadapter.myviewholder> {

    public myadapter(@NonNull FirebaseRecyclerOptions<model_dashboard> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull model_dashboard model)
    {
        holder.typetext.setText(model.getType());
        holder.informationtext.setText(model.getInformation());
        Glide.with(holder.img1.getContext()).load(model.getUrl()).into(holder.img1);
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView img1;
        TextView typetext,informationtext;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            img1=(ImageView)itemView.findViewById(R.id.img1);
            typetext=(TextView)itemView.findViewById(R.id.typetext);
            informationtext=(TextView)itemView.findViewById(R.id.informationtext);
        }
    }
}
