package com.moutamid.sra_admin.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.moutamid.sra_admin.DepositRequestActivity;
import com.moutamid.sra_admin.R;
import com.moutamid.sra_admin.TaskActivity;
import com.moutamid.sra_admin.WithdrawRequestActivity;
import com.moutamid.sra_admin.models.RequestModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionVH> implements Filterable {
    Context context;
    ArrayList<RequestModel> list;
    ArrayList<RequestModel> listAll;

    public TransactionsAdapter(Context context, ArrayList<RequestModel> list) {
        this.context = context;
        this.list = list;
        this.listAll = new ArrayList<>(list);
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
        } else if (model.getType().equals("TASK")){
            holder.name.setText("TASK Open Request");
        } else {
            holder.name.setText("Withdraw Request");
        }

        if (model.getStatus().equals("PEN")){
            holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.secondary_color));
            holder.status.setText("Pending");
        } else if (model.getStatus().equals("COM")){
            holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.primary_color));
            holder.status.setText("Completed");
        } else {
            holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.red));
            holder.status.setText("Canceled");
        }
        holder.amount.setText("Amount : $" + model.getAmount());
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy, hh:mm aa");
        String date = format.format(model.getTimestamps());
        holder.time.setText("Date/Time : " + date);

        holder.itemView.setOnClickListener(v -> {
            if (model.getType().equals("WITH")){
                Intent o = new Intent(context, WithdrawRequestActivity.class);
                o.putExtra("model", model);
                context.startActivity(o);
            } else if (model.getType().equals("TASK")){
                Intent o = new Intent(context, TaskActivity.class);
                o.putExtra("model", model);
                context.startActivity(o);
            } else {
                Intent i = new Intent(context, DepositRequestActivity.class);
                i.putExtra("model", list.get(holder.getAdapterPosition()));
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<RequestModel> filterList = new ArrayList<>();
            if (charSequence.toString().isEmpty()){
                filterList.addAll(listAll);
            } else {
                for (RequestModel listModel : listAll){
                    if (listModel.getUserID().equals(charSequence.toString())){
                        filterList.add(listModel);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            list.clear();
            list.addAll((Collection<? extends RequestModel>) filterResults.values);
            notifyDataSetChanged();
        }
    };

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
