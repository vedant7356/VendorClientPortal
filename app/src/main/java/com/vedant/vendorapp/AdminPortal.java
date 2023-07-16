package com.vedant.vendorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;
import com.vedant.vendorapp.LocationFragments.AdminMapFragment;
import com.vedant.vendorapp.LocationFragments.adminViewTasks;

public class AdminPortal extends AppCompatActivity {

    CardView one;

    FirebaseDatabase database;

    ImageView user_image;

    TextView name,location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_portal);

        database = FirebaseDatabase.getInstance();

        setUserData();

        user_image = (ImageView)findViewById(R.id.user_image);
        name = (TextView)findViewById(R.id.name_tv_show);
        location = (TextView)findViewById(R.id.location_tv_show);

        database = FirebaseDatabase.getInstance();

        database.getReference().child("Vendor").child("userimg")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String image = snapshot.getValue(String.class);
                        Picasso.get()
                                .load(image)
                                .into(user_image);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        one= (CardView) findViewById(R.id.person_one);

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),CourierView.class));
            }
        });

//        Fragment fragment= new AdminMapFragment();
//
//        getSupportFragmentManager()
//                .beginTransaction().replace(R.id.frameLayout_map,fragment)
//                .commit();
//
//
//        Fragment fragment_two= new adminViewTasks();
//
//        getSupportFragmentManager()
//                .beginTransaction().replace(R.id.frame_layout_tasks,fragment_two)
//                .commit();

    }

    private void setUserData() {
        DatabaseReference mDatabaseRef = database.getReference();

        mDatabaseRef = FirebaseDatabase.getInstance()
                .getReference()
                .child("Vendor");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                name.setText(snapshot.child("name").getValue().toString());
                location.setText(snapshot.child("location").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}