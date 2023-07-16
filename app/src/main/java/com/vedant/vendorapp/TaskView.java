package com.vedant.vendorapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.vedant.vendorapp.databinding.ActivityMainBinding;
import com.vedant.vendorapp.databinding.ActivityTaskViewBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskView extends AppCompatActivity {
    ActivityTaskViewBinding binding;
    ActivityResultLauncher<String> launcher;
    FirebaseDatabase database;
    FirebaseStorage firebaseStorage;
    RadioButton selectedRadioButton;
    RadioGroup radioGroup;
    Button b2;
    DatabaseReference dbr_title,dbr_location,dbr_updateData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();


        dbr_title = FirebaseDatabase.getInstance()
                        .getReference()
                            .child("Vendor").child("Location_one").child("Task_one");

        dbr_location = FirebaseDatabase.getInstance()
                .getReference()
                .child("Vendor").child("Location_one");

        dbr_title.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                binding.taskTitle.setText(snapshot.child("title").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dbr_location.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                binding.locationTv.setText(snapshot.child("landmark").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        addListenerOnButton();



        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        String currentDate = simpleDateFormat.format(new Date());

        binding.currentDate.setText(currentDate);

//        b2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                selectedRadioButton  = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
////                //get RadioButton text
////                String yourVote = selectedRadioButton.getText().toString();
////                // display it as Toast to the user
//                Toast.makeText(TaskView.this, "Selected Radio Button is:" + "Ved" , Toast.LENGTH_LONG).show();
//            }
//        });

//        binding.radiogroupStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                selectedRadioButton  = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
//                //get RadioButton text
//                String yourVote = selectedRadioButton.getText().toString();
//                // display it as Toast to the user
//                Toast.makeText(getApplicationContext(), "Selected Radio Button is:" + yourVote , Toast.LENGTH_LONG).show();
//            }
//        });


        SimpleDateFormat simpletimeFormat = new SimpleDateFormat("HH:mm:ss");

        String currentTime = simpletimeFormat.format(new Date());

        binding.timeCurrent.setText(currentTime);

        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                         binding.imgAdd.setImageURI(result);

                         final StorageReference reference= firebaseStorage.getReference()
                                 .child("Vendor").child("Tasks").child("location_one").child("task_img");

                         reference.putFile(result).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                             @Override
                             public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                  reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                      @Override
                                      public void onSuccess(Uri uri) {
                                          database.getReference().child("Vendor").child("Location_one").child("Task_one").child("img")
                                                  .setValue(uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                      @Override
                                                      public void onSuccess(Void unused) {
                                                          Toast.makeText(TaskView.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                                                      }
                                                  });
                                      }
                                  });
                             }
                         });
                    }
                });

        binding.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launcher.launch("image/*");
            }
        });
    }

    private void addListenerOnButton() {

        radioGroup = (RadioGroup)findViewById(R.id.radiogroup_status);
        b2=(Button)findViewById(R.id.button5);

        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                selectedRadioButton = (RadioButton) findViewById(selectedId);

//                Toast.makeText(TaskView.this,
//                        selectedRadioButton.getText(), Toast.LENGTH_SHORT).show();

                String date = binding.currentDate.getText().toString();
                String time = binding.timeCurrent.getText().toString();
                String status = selectedRadioButton.getText().toString();

                FirebaseDatabase  database = FirebaseDatabase.getInstance();
                DatabaseReference mDatabaseRef = database.getReference();

                mDatabaseRef.child("Vendor").child("Location_one").child("Task_one").child("current_date").setValue(date);
                mDatabaseRef.child("Vendor").child("Location_one").child("Task_one").child("current_time").setValue(time);
                mDatabaseRef.child("Vendor").child("Location_one").child("Task_one").child("task_status").setValue(status);
                mDatabaseRef.child("Vendor").child("Location_one").child("Task_one").child("current_location").setValue(binding.locationTv.getText().toString());
                mDatabaseRef.child("Vendor").child("Location_one").child("Task_one").child("description").setValue(binding.descMl.getText().toString());




            }

        });

    }
}