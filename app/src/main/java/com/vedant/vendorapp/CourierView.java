package com.vedant.vendorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.vedant.vendorapp.LocationFragments.AdminMapFragment;
import com.vedant.vendorapp.LocationFragments.adminViewTasks;

public class CourierView extends AppCompatActivity {
    ImageView employee_img;
    TextView location_emp,name_emp,task_title,task_date,task_time,task_stat;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier_view);

        database = FirebaseDatabase.getInstance();



        Fragment fragment= new AdminMapFragment();

        employee_img = (ImageView)findViewById(R.id.image_employee);
        name_emp = (TextView) findViewById(R.id.name_employee);
        location_emp = (TextView) findViewById(R.id.location_employee);

        fill_the_data();

        getSupportFragmentManager()
                .beginTransaction().replace(R.id.frameLayout_map,fragment)
                .commit();


        Fragment fragment_two= new adminViewTasks();

        getSupportFragmentManager()
                .beginTransaction().replace(R.id.frame_layout_tasks,fragment_two)
                .commit();
    }

    private void fill_the_data() {

        database = FirebaseDatabase.getInstance();

        database.getReference().child("Vendor").child("userimg")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String image = snapshot.getValue(String.class);
                        Picasso.get()
                                .load(image)
                                .into(employee_img);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        DatabaseReference mDatabaseRef = database.getReference();
        DatabaseReference mDatabaseRef_task = database.getReference();

        mDatabaseRef = FirebaseDatabase.getInstance()
                .getReference()
                .child("Vendor");

        mDatabaseRef_task = FirebaseDatabase.getInstance()
                .getReference()
                .child("Vendor").child("Vendor").child("Location_one").child("Task_one");

        mDatabaseRef_task.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                name_emp.setText(snapshot.child("name").getValue().toString());
                location_emp.setText(snapshot.child("location").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}