package com.moutamid.sra_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.moutamid.sra_admin.databinding.ActivityTaskBinding;
import com.moutamid.sra_admin.models.RequestModel;
import com.moutamid.sra_admin.models.UserModel;

public class TaskActivity extends AppCompatActivity {
    ActivityTaskBinding binding;

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


    }
}