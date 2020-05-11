package com.nyan.budgetapp.views.transactions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nyan.budgetapp.R;
import com.nyan.budgetapp.utils.CurrencyUtils;
import com.nyan.budgetapp.utils.DateUtils;
import com.nyan.budgetapp.vo.Transaction;

import java.util.List;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.ViewHolder> {
    private List<Transaction> transactionList;
    private OnItemClickedListener listener;

    public TransactionsAdapter(List<Transaction> categoryList) {
        this.transactionList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(transactionList.get(position));
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public void setOnItemClickedListener(OnItemClickedListener listener) {
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCategoryName;
        TextView tvTimeStamp;
        TextView tvAmount;
        View vColor;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            tvTimeStamp = itemView.findViewById(R.id.tvTimeStamp);
            vColor = itemView.findViewById(R.id.vColor);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            itemView.setOnClickListener(view -> listener.onItemClicked(transactionList.get(getAdapterPosition())));
        }

        void bind(Transaction transaction) {
            tvAmount.setText(CurrencyUtils.format(transaction.amount, transaction.currencyISO));
            tvTimeStamp.setText(DateUtils.getDateTimeString(transaction.timeStamp));
            tvCategoryName.setText(transaction.category.name);
            vColor.setBackgroundColor(transaction.category.colorHex);
        }
    }

    public interface OnItemClickedListener {
        void onItemClicked(Transaction item);
    }
}
