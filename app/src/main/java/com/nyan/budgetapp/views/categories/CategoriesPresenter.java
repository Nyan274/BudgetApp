package com.nyan.budgetapp.views.categories;

import com.nyan.budgetapp.db.AppDatabase;
import com.nyan.budgetapp.utils.ThreadExecutor;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class CategoriesPresenter implements CategoriesContract.Presenter {
    private CategoriesContract.View view;
    private ThreadExecutor threadExecutor;
    private AppDatabase db;
    private CompositeDisposable compositeDisposable;

    CategoriesPresenter(CategoriesContract.View view, AppDatabase db, ThreadExecutor threadExecutor) {
        this.view = view;
        this.db = db;
        this.threadExecutor = threadExecutor;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void load() {
        Disposable disposable = Single.fromCallable(() -> db.categoryDao().getAll())
                .subscribeOn(threadExecutor.io())
                .observeOn(threadExecutor.ui())
                .subscribe(categories -> {
                    view.show(categories);
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void dropView() {
        compositeDisposable.dispose();
    }
}
