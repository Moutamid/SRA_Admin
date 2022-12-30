package com.moutamid.sra_admin.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.sra_admin.Constants;
import com.moutamid.sra_admin.R;
import com.moutamid.sra_admin.adapters.TransactionsAdapter;
import com.moutamid.sra_admin.databinding.FragmentWithdrawBinding;
import com.moutamid.sra_admin.models.RequestModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class WithdrawFragment extends Fragment {

    FragmentWithdrawBinding binding;
    Context context;
    TransactionsAdapter adapter;
    ArrayList<RequestModel> list;

    public WithdrawFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWithdrawBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        context = view.getContext();

        list = new ArrayList<>();

        binding.recycler.setLayoutManager(new LinearLayoutManager(context));
        binding.recycler.setHasFixedSize(false);

        Constants.databaseReference().child("Request").addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    list.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        for (DataSnapshot ds : dataSnapshot.getChildren()){
                            RequestModel model = ds.getValue(RequestModel.class);
                            if (model.getType().equals("WITH")){
                                list.add(model);
                            }
                            Collections.sort(list, Comparator.comparing(RequestModel::getTimestamps));
                            Collections.reverse(list);
                            adapter = new TransactionsAdapter(context, list);
                            binding.recycler.setAdapter(adapter);
                            adapter.notifyItemInserted(list.size()-1);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}