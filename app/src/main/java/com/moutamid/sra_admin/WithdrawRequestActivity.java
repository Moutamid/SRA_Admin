package com.moutamid.sra_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.moutamid.sra_admin.databinding.ActivityWithdrawRequestBinding;
import com.moutamid.sra_admin.models.RequestModel;
import com.moutamid.sra_admin.models.UserModel;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WithdrawRequestActivity extends AppCompatActivity {
    ActivityWithdrawRequestBinding binding;
    RequestModel model;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWithdrawRequestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        model = (RequestModel) getIntent().getSerializableExtra("model");

        binding.requestedAmount.setText("$"+model.getAmount());
        binding.hashKey.setText(model.getHashKey());
        binding.userID.setText(model.getUserID());

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
                    String s = String.format("%.2f", user.getAssets());
                    binding.userAmount.setText("$"+s);

                }).addOnFailureListener(e -> {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                });

        binding.hashKey.setOnClickListener(v -> {
            String str = model.getHashKey();
            ((ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("Copied Text", str));
            Toast.makeText(this, "Copied To Clipboard", Toast.LENGTH_SHORT).show();
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
                        startActivity(new Intent(WithdrawRequestActivity.this, MainActivity.class));
                        finish();
                    }).addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });

        binding.approved.setOnClickListener(v -> {
            progressDialog.show();
            Date date = new Date();
            float current = Float.parseFloat(binding.userAmount.getText().toString().substring(1));
            Map<String, Object> map = new HashMap<>();
            map.put("assets", current-model.getAmount());
            Map<String, Object> status = new HashMap<>();
            status.put("status", "COM");
            status.put("timestamps", date.getTime());
            Constants.databaseReference().child("users").child(model.getUserID())
                    .updateChildren(map).addOnSuccessListener(unused -> {
                        Constants.databaseReference().child("Request").child(model.getUserID()).child(model.getID())
                                .updateChildren(status).addOnSuccessListener(unused1 -> {
                                    Toast.makeText(getApplicationContext(), "Request Approved", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    startActivity(new Intent(WithdrawRequestActivity.this, MainActivity.class));
                                    finish();
                                }).addOnFailureListener(e -> {
                                    progressDialog.dismiss();
                                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    }).addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }
}