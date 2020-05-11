package com.nyan.budgetapp.views.transactions;

import com.nyan.budgetapp.views.BasePresenter;
import com.nyan.budgetapp.views.BaseView;
import com.nyan.budgetapp.vo.Transaction;

import java.util.List;

public interface TransactionsContract {
    interface View extends BaseView {
        void show(List<Transaction> transactionList);
    }

    interface Presenter extends BasePresenter {
        void load();
    }
}
