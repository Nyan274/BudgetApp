package com.nyan.budgetapp.views.transaction;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nyan.budgetapp.vo.Category;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {
    private List<Category> values;

    CategoryAdapter(@NonNull Context context, int resource, @NonNull List<Category> values) {
        super(context, resource, values);
        this.values = values;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Category getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getPosition(Category category) {
        for (int i = 0; i < values.size(); i++) {
            if (category.name.equals(values.get(i))) return i;
        }
        return 0;
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setText(values.get(position).name);
        return label;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setText(values.get(position).name);
        return label;
    }
}
