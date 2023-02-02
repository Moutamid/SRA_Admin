package com.moutamid.sra_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.sra_admin.databinding.ActivityTaskBinding;
import com.moutamid.sra_admin.models.RequestModel;
import com.moutamid.sra_admin.models.Tasks;
import com.moutamid.sra_admin.models.UserModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TaskActivity extends AppCompatActivity {
    ActivityTaskBinding binding;
    List<Tasks> list;
    List<Tasks> newList;
    RequestModel model;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        model = (RequestModel) getIntent().getSerializableExtra("model");

        list = new ArrayList<>();
        newList = new ArrayList<>();

        binding.requestedAmount.setText("$"+model.getIncome());
        binding.userID.setText(model.getUserID());
        binding.taskName.setText(model.getName());

        if (model.getStatus().equals("PEN")){
            binding.buttonLayout.setVisibility(View.VISIBLE);
        } else {
            binding.buttonLayout.setVisibility(View.GONE);
        }

        if (model.getStatus().equals("PEN")){
            binding.statusCard.setCardBackgroundColor(getResources().getColor(R.color.secondary_color));
            binding.status.setText("Pending");
            binding.buttonLayout.setVisibility(View.VISIBLE);
        } else if (model.getStatus().equals("COM")){
            binding.statusCard.setCardBackgroundColor(getResources().getColor(R.color.primary_color));
            binding.status.setText("Completed");
            binding.buttonLayout.setVisibility(View.GONE);
        } else {
            binding.statusCard.setCardBackgroundColor(getResources().getColor(R.color.red));
            binding.status.setText("Canceled");
            binding.buttonLayout.setVisibility(View.GONE);
        }

        Constants.databaseReference().child("users").child(model.getUserID()).get()
                .addOnSuccessListener(dataSnapshot -> {
                    progressDialog.dismiss();
                    UserModel user = dataSnapshot.getValue(UserModel.class);
                    binding.username.setText(user.getUsername());
                    String s = String.format("%.2f", user.getDeposit());
                    binding.userAmount.setText("$"+s);
                    float current = Float.parseFloat(binding.userAmount.getText().toString().substring(1));
                    String ss = String.format("%.2f", (current + model.getAmount()));
                    float t = Float.parseFloat(ss);
                    //Toast.makeText(this, ""+t, Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                });

        binding.declined.setOnClickListener(v -> {
            progressDialog.show();
            Date date = new Date();
            Map<String, Object> map = new HashMap<>();
            map.put("status", "CAN");
            map.put("timestamps", date.getTime());
            Constants.databaseReference().child("Request").child(model.getUserID()).child(model.getID())
                    .updateChildren(map).addOnSuccessListener(unused -> {
                        Toast.makeText(getApplicationContext(), "Request Declined", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        startActivity(new Intent(TaskActivity.this, MainActivity.class));
                        finish();
                    }).addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });

        binding.approved.setOnClickListener(v -> {
            progressDialog.show();
            //float current = Float.parseFloat(binding.userAmount.getText().toString().substring(1));
            //Toast.makeText(this, ""+current, Toast.LENGTH_SHORT).show();
            Map<String, Object> map = new HashMap<>();
            //Toast.makeText(this, ""+current+"  "+model.getAmount(), Toast.LENGTH_SHORT).show();
            // String s = String.format("%.2f", (current - model.getAmount()));
            //float t = Float.parseFloat(s);
            map.put("deposit", model.getAmount());
            Constants.databaseReference().child("users").child(model.getUserID())
                    .updateChildren(map).addOnSuccessListener(unused -> {
                        lockAll();
                    }).addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });


    }

    private void lockAll() {
        Constants.databaseReference().child("userTasks").child(model.getUserID())
                .get().addOnSuccessListener(snapshot -> {
                    if (snapshot.exists()) {
                        list.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Tasks model = dataSnapshot.getValue(Tasks.class);
                            list.add(model);
                        }
                        setData();
                    }
                }).addOnFailureListener(e -> {

                });
    }

    private void setData() {
        for (int i=0; i<list.size(); i++){
            Tasks model = list.get(i);
            model.setLock(true);
            newList.add(model);
        }

        for (int i =0; i<newList.size(); i++){
            Constants.databaseReference().child("userTasks").child(model.getUserID()).child(list.get(i).getUid())
                    .setValue(newList.get(i)).addOnSuccessListener(unused -> {
                        add();
                    }).addOnFailureListener(e -> {
                        e.printStackTrace();
                    });
        }

    }

    private void add() {
        Date date = new Date();
        Map<String, Object> status = new HashMap<>();
        status.put("status", "COM");
        status.put("timestamps", date.getTime());
        Map<String, Object> task = new HashMap<>();
        task.put("lock", false);

        Constants.databaseReference().child("userTasks").child(model.getUserID()).child(model.getUid())
                .updateChildren(task).addOnSuccessListener(unused1 -> {
                    Constants.databaseReference().child("Request").child(model.getUserID()).child(model.getID())
                            .updateChildren(status).addOnSuccessListener(unused2 -> {
                                Toast.makeText(getApplicationContext(), "Request Approved", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                startActivity(new Intent(TaskActivity.this, MainActivity.class));
                                finish();
                            }).addOnFailureListener(e -> {
                                progressDialog.dismiss();
                                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                }).addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                });

    }
}