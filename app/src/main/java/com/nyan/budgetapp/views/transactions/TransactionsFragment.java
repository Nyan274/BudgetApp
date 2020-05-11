package com.nyan.budgetapp.views.transactions;

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
import com.nyan.budgetapp.server.CurrencyLayerRepositoryImpl;
import com.nyan.budgetapp.utils.RetrofitBuilder;
import com.nyan.budgetapp.utils.ThreadExecutorImpl;
import com.nyan.budgetapp.views.transaction.TransactionActivity;
import com.nyan.budgetapp.vo.Transaction;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;

public class TransactionsFragment extends Fragment implements TransactionsContract.View {
    TransactionsContract.Presenter presenter;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private TransactionsAdapter adapter;
    private List<Transaction> transactionList = new ArrayList<>();

    public static Fragment newInstance() {
        TransactionsFragment fragment = new TransactionsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Retrofit retrofit = RetrofitBuilder.build(getString(R.string.baseUrl), getActivity());
        presenter = new TransactionsPresenter(this, new ThreadExecutorImpl(), AppDatabase.getInstance(getActivity()), new CurrencyLayerRepositoryImpl(retrofit));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_transaction_list, container, false);
        recyclerView = v.findViewById(R.id.recyclerView);
        fab = v.findViewById(R.id.fab);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new TransactionsAdapter(transactionList);
        recyclerView.setAdapter(adapter);
        fab.setOnClickListener(v -> TransactionActivity.start(getActivity()));
        adapter.setOnItemClickedListener(transactionList -> TransactionActivity.start(getActivity(), transactionList));
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.load();
    }

    @Override
    public void show(List<Transaction> transactionList) {
        this.transactionList.clear();
        this.transactionList.addAll(transactionList);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dropView();
    }
}
