

package com.moutamid.sra_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.moutamid.sra_admin.adapters.ViewPagerAdapter;
import com.moutamid.sra_admin.databinding.ActivityMainBinding;
import com.moutamid.sra_admin.fragments.DepositFragment;
import com.moutamid.sra_admin.fragments.WithdrawFragment;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        binding.viewpager.setAdapter(viewPagerAdapter);

        binding.edit.setOnClickListener(v -> {
            startActivity(new Intent(this, EditRulesActivity.class));
        });

        binding.tablayout.setupWithViewPager(binding.viewpager);

    }

}