package com.nyan.budgetapp.views.category_entry;

import com.nyan.budgetapp.views.BasePresenter;
import com.nyan.budgetapp.vo.Category;

public interface CategoryContract {
    interface View {
        void finish();
    }

    interface Presenter extends BasePresenter {
        void save(Category category);
    }
}
