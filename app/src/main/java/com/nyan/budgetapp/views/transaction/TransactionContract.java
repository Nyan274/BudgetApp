package com.nyan.budgetapp.views.transaction;

import com.nyan.budgetapp.views.BasePresenter;
import com.nyan.budgetapp.views.BaseView;
import com.nyan.budgetapp.vo.Category;
import com.nyan.budgetapp.vo.Transaction;

import java.util.List;

public interface TransactionContract {
    interface View extends BaseView {
        void finish();

        void showCategories(List<Category> categories);
    }

    interface Presenter extends BasePresenter {
        void loadCategories();

        void save(Transaction transaction);
    }
}
