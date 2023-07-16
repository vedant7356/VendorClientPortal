package com.vedant.vendorapp.LocationFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;
import com.vedant.vendorapp.R;

public class adminViewTasks extends Fragment {
    FirebaseDatabase database;
    ImageView img_show;
    TextView task_title,task_date,task_time;
    DatabaseReference db1;
    ImageView task_stat;
   // TextView


    public adminViewTasks() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_admin_view_tasks, container, false);

        img_show = (ImageView)v.findViewById(R.id.view_img);
        database = FirebaseDatabase.getInstance();
        task_title = (TextView)v.findViewById(R.id.task_tv_one);
        task_date = (TextView)v.findViewById(R.id.task_date_tv);
        task_time = (TextView)v.findViewById(R.id.time_tv_task);

        task_stat = (ImageView) v.findViewById(R.id.task_status_iv);

        db1 = FirebaseDatabase.getInstance()
                .getReference()
                .child("Vendor").child("Location_one").child("Task_one");

        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               task_title.setText(snapshot.child("title").getValue().toString());
               task_date.setText(snapshot.child("current_date").getValue().toString());
               task_time.setText(snapshot.child("current_time").getValue().toString());

               String stats = snapshot.child("task_status").getValue().toString();

               if (stats.equals("Completed")){

                   task_stat.setImageResource(R.drawable.ic_baseline_assignment_turned_in_24);

               }

               else if (stats.equals("Incomplete")){

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        database.getReference().child("Vendor").child("Location_one").child("Task_one").child("img")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String image = snapshot.getValue(String.class);
                        Picasso.get()
                                .load(image)
                                .into(img_show);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



        return v;
    }
}