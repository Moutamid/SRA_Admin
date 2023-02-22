package com.moutamid.sra_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.moutamid.sra_admin.databinding.ActivityDepositRequestBinding;
import com.moutamid.sra_admin.models.RequestModel;
import com.moutamid.sra_admin.models.UserModel;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DepositRequestActivity extends AppCompatActivity {
    ActivityDepositRequestBinding binding;
    RequestModel model;
    UserModel user;
    ProgressDialog progressDialog;
    String promotion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDepositRequestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        model = (RequestModel) getIntent().getSerializableExtra("model");

        binding.requestedAmount.setText("$"+model.getAmount());
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
                    user = dataSnapshot.getValue(UserModel.class);
                    binding.username.setText(user.getUsername());
                    String s = String.format("%.2f", user.getAssets());
                    binding.userAmount.setText("$"+s);
                    promotion = String.format("%.2f", user.getPromotionValue());
                    float current = Float.parseFloat(binding.userAmount.getText().toString().substring(1));
                    String ss = String.format("%.2f", (current + model.getAmount()));
                    float t = Float.parseFloat(ss);
                    //Toast.makeText(this, ""+t, Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                });

        binding.hashKey.setOnClickListener(v -> {
            showDialog();
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
                        startActivity(new Intent(DepositRequestActivity.this, MainActivity.class));
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
            float bonus = 0F;
            //Toast.makeText(this, ""+current, Toast.LENGTH_SHORT).show();
            if (!binding.bonus.getText().toString().isEmpty()) {
                current = current + Float.parseFloat(binding.bonus.getText().toString());
                bonus = Float.parseFloat(binding.bonus.getText().toString());
            }
            Map<String, Object> map = new HashMap<>();
            //Toast.makeText(this, ""+current+"  "+model.getAmount(), Toast.LENGTH_SHORT).show();
            String s = String.format("%.2f", (current + model.getAmount()));
            float t = Float.parseFloat(s);
            map.put("assets", t);
            map.put("promotionValue", (bonus + Float.parseFloat(promotion)));
            Map<String, Object> status = new HashMap<>();
            status.put("status", "COM");
            status.put("timestamps", date.getTime());
            Constants.databaseReference().child("users").child(model.getUserID())
                    .updateChildren(map).addOnSuccessListener(unused -> {
                        Constants.databaseReference().child("Request").child(model.getUserID()).child(model.getID())
                                .updateChildren(status).addOnSuccessListener(unused1 -> {
                                    updateReferal(user.getInvitationCode());
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

    private void updateReferal(String invitationCode) {
        Map<String, Object> update = new HashMap<>();
        try {

            Constants.databaseReference().child("users").child(model.getUserID())
                    .get().addOnSuccessListener(dataSnapshot -> {
                        if (dataSnapshot.exists()){
                            UserModel current = dataSnapshot.getValue(UserModel.class);
                            if (!current.isReceivePrice()){
                                Constants.databaseReference().child("users").child(invitationCode)
                                        .get().addOnSuccessListener(dataSnapshot2 -> {
                                            if (dataSnapshot2.exists()) {
                                                UserModel model = dataSnapshot2.getValue(UserModel.class);
                                                    double assets = 0;
                                                    double promotionValue = 0;
                                                    try {
                                                        assets = model.getAssets();
                                                        promotionValue = model.getPromotionValue();
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                    update.put("assets", assets + 5);
                                                    update.put("promotionValue", promotionValue + 5);
                                                    Constants.databaseReference().child("users").child(invitationCode)
                                                            .updateChildren(update).addOnSuccessListener(unused -> {
                                                                Map<String, Object> map = new HashMap<>();
                                                                map.put("receivePrice", true);
                                                                Constants.databaseReference().child("users").child(DepositRequestActivity.this.model.getUserID())
                                                                        .updateChildren(map).addOnSuccessListener(unused1 -> {
                                                                            Toast.makeText(getApplicationContext(), "Request Approved", Toast.LENGTH_SHORT).show();
                                                                            progressDialog.dismiss();
                                                                            startActivity(new Intent(DepositRequestActivity.this, MainActivity.class));
                                                                            finish();
                                                                        }).addOnFailureListener(e -> {
                                                                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                            progressDialog.dismiss();
                                                                        });
                                                            }).addOnFailureListener(e -> {
                                                                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                progressDialog.dismiss();
                                                            });
                                            }
                                        }).addOnFailureListener(e -> {
                                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        });
                            } else {
                                Toast.makeText(getApplicationContext(), "Request Approved", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                startActivity(new Intent(DepositRequestActivity.this, MainActivity.class));
                                finish();
                            }
                        }
                    }).addOnFailureListener(e -> {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    });
        } catch (Exception e){
            progressDialog.dismiss();
            e.printStackTrace();
        }
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.show_image_dialog);

        TextView close = (TextView) dialog.findViewById(R.id.close_image);
        ImageView imageView = (ImageView) dialog.findViewById(R.id.preview_image);

        Glide.with(DepositRequestActivity.this).load(model.getImage()).into(imageView);

        close.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.CENTER);
    }
}