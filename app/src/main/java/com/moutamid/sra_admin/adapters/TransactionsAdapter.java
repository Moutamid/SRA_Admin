package com.moutamid.sra_admin.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.moutamid.sra_admin.R;
import com.moutamid.sra_admin.WithdrawRequestActivity;
import com.moutamid.sra_admin.models.RequestModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionVH> {
    Context context;
    ArrayList<RequestModel> list;

    public TransactionsAdapter(Context context, ArrayList<RequestModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TransactionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.transaction_card, parent, false);
        return new TransactionVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionVH holder, int position) {
        RequestModel model = list.get(holder.getAdapterPosition());
        if (model.getType().equals("DEP")){
            holder.name.setText("Deposit Request");
        } else {
            holder.name.setText("Withdraw Request");
        }
        holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.secondary_color));
        holder.status.setText("Pending");
        holder.amount.setText("Amount : $" + model.getAmount());
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy, hh:mm aa");
        String date = format.format(model.getTimestamps());
        holder.time.setText("Date/Time : " + date);

        holder.itemView.setOnClickListener(v -> {
            if (model.getType().equals("WITH")){
                Intent o = new Intent(context, WithdrawRequestActivity.class);
                o.putExtra("model", model);
                context.startActivity(o);
            } else {
                Intent i = new Intent(context, WithdrawRequestActivity.class);
                i.putExtra("model", model);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TransactionVH extends RecyclerView.ViewHolder{
        CardView card;
        TextView status, time, amount, name;

        public TransactionVH(@NonNull View itemView) {
            super(itemView);

            card = itemView.findViewById(R.id.statusCard);
            status = itemView.findViewById(R.id.status);
            time = itemView.findViewById(R.id.date);
            amount = itemView.findViewById(R.id.amount);
            name = itemView.findViewById(R.id.requestFor);

        }
    }
}
