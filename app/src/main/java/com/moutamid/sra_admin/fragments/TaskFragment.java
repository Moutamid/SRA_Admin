package com.moutamid.sra_admin.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.sra_admin.Constants;
import com.moutamid.sra_admin.R;
import com.moutamid.sra_admin.adapters.TransactionsAdapter;
import com.moutamid.sra_admin.databinding.FragmentTaskBinding;
import com.moutamid.sra_admin.models.RequestModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TaskFragment extends Fragment {
    FragmentTaskBinding binding;
    Context context;
    TransactionsAdapter adapter;
    ArrayList<RequestModel> list;
    public TaskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTaskBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        context = view.getContext();

        list = new ArrayList<>();

        binding.recycler.setLayoutManager(new LinearLayoutManager(context));
        binding.recycler.setHasFixedSize(false);

        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                adapter.getFilter().filter(editable.toString());
            }
        });

        Constants.databaseReference().child("Request").addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        for (DataSnapshot ds : dataSnapshot.getChildren()){
                            RequestModel model = ds.getValue(RequestModel.class);
                            if (model.getType().equals("TASK")) {
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