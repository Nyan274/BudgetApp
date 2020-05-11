package com.nyan.budgetapp.views.categories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nyan.budgetapp.R;
import com.nyan.budgetapp.db.AppDatabase;
import com.nyan.budgetapp.utils.ThreadExecutorImpl;
import com.nyan.budgetapp.views.category_entry.CategoryActivity;
import com.nyan.budgetapp.vo.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment implements CategoriesContract.View {
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private CategoriesAdapter adapter;
    private CategoriesContract.Presenter presenter;
    private List<Category> categoryList = new ArrayList<>();

    public static Fragment newInstance() {
        CategoriesFragment fragment = new CategoriesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new CategoriesAdapter(categoryList);
        recyclerView.setAdapter(adapter);
        fab = view.findViewById(R.id.fab);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new CategoriesPresenter(this, AppDatabase.getInstance(getActivity()), new ThreadExecutorImpl());
        fab.setOnClickListener(v -> {
            CategoryActivity.start(getActivity());
        });
        adapter.setOnItemClickedListener(category -> {
            CategoryActivity.start(getActivity(), category);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.load();
    }

    @Override
    public void show(List<Category> categoryList) {
        this.categoryList.clear();
        this.categoryList.addAll(categoryList);
        adapter.notifyDataSetChanged();
    }
}
