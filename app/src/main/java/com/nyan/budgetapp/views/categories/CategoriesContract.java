package com.nyan.budgetapp.views.categories;

import com.nyan.budgetapp.vo.Category;
import com.nyan.budgetapp.views.BasePresenter;

import java.util.List;

public interface CategoriesContract {
    interface View {
        void show(List<Category> categoryList);
    }

    interface Presenter extends BasePresenter {
        void load();
    }
}
