package com.moutamid.sra_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.moutamid.sra_admin.databinding.ActivityEditRulesBinding;

import java.util.HashMap;
import java.util.Map;

public class EditRulesActivity extends AppCompatActivity {
    ActivityEditRulesBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditRulesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.back.setOnClickListener(v -> {
            onBackPressed();
            finish();
        });

        Constants.databaseReference().child("rules").get().addOnSuccessListener(dataSnapshot -> {
            String s = dataSnapshot.child("rules").getValue().toString();
            binding.rules.setText(s);
        }).addOnFailureListener(e -> {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        });


        binding.save.setOnClickListener(v -> {
            Map<String, Object> map= new HashMap<>();
            map.put("rules", binding.rules.getText().toString());

            Constants.databaseReference().child("rules").setValue(map).addOnSuccessListener(unused -> {
                Toast.makeText(getApplicationContext(), "Rules Changes", Toast.LENGTH_SHORT).show();
                onBackPressed();
                finish();
            }).addOnFailureListener(e -> {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        });
    }
}