package com.moutamid.sra_admin.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moutamid.sra_admin.R;
import com.moutamid.sra_admin.databinding.FragmentWithdrawBinding;

public class WithdrawFragment extends Fragment {

    FragmentWithdrawBinding binding;

    public WithdrawFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_withdraw, container, false);
    }
}