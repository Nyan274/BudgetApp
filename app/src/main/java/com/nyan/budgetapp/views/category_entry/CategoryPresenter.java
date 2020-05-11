package com.nyan.budgetapp.views.category_entry;

import com.nyan.budgetapp.db.AppDatabase;
import com.nyan.budgetapp.utils.ThreadExecutor;
import com.nyan.budgetapp.vo.Category;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class CategoryPresenter implements CategoryContract.Presenter {
    CategoryContract.View view;
    ThreadExecutor threadExecutor;
    AppDatabase db;
    CompositeDisposable compositeDisposable;

    public CategoryPresenter(CategoryContract.View view, ThreadExecutor threadExecutor, AppDatabase db) {
        this.view = view;
        this.threadExecutor = threadExecutor;
        this.db = db;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void save(Category category) {
        Disposable disposable = Single.fromCallable(() -> {
            db.categoryDao().insert(category);
            return "";
        }).subscribeOn(threadExecutor.io()).observeOn(threadExecutor.ui())
                .subscribe((v) -> {
                    view.finish();
                });
        compositeDisposable.add(disposable);

    }

    @Override
    public void dropView() {
        compositeDisposable.dispose();
    }
}
