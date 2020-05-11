package com.nyan.budgetapp.views.categories;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nyan.budgetapp.R;
import com.nyan.budgetapp.utils.CurrencyUtils;
import com.nyan.budgetapp.vo.Category;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    private List<Category> categoryList;
    private OnItemClickedListener listener;

    public CategoriesAdapter(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(categoryList.get(position));
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public void setOnItemClickedListener(OnItemClickedListener listener) {
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        View vColor;
        TextView tvName;
        TextView tvMonthlyBudget;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            vColor = itemView.findViewById(R.id.vColor);
            tvName = itemView.findViewById(R.id.tvName);
            tvMonthlyBudget = itemView.findViewById(R.id.tvMonthlyBudget);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null)
                        listener.onItemClicked(categoryList.get(getAdapterPosition()));
                }
            });
        }

        void bind(Category category) {
            tvName.setText(category.name);
            tvMonthlyBudget.setText(CurrencyUtils.format(category.monthlyBudget, category.currencyISO));
            vColor.setBackgroundColor(category.colorHex);
        }
    }

    public interface OnItemClickedListener {
        void onItemClicked(Category category);
    }
}
