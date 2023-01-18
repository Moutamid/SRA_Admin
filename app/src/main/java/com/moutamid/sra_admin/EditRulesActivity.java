package com.moutamid.sra_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.moutamid.sra_admin.databinding.ActivityEditRulesBinding;

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

    }
}