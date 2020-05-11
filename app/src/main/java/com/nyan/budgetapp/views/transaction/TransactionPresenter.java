package com.nyan.budgetapp.views.transaction;

import com.nyan.budgetapp.db.AppDatabase;
import com.nyan.budgetapp.utils.ThreadExecutor;
import com.nyan.budgetapp.vo.Transaction;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class TransactionPresenter implements TransactionContract.Presenter {
    private AppDatabase db;
    private TransactionContract.View view;
    private ThreadExecutor scheduleProvider;
    private CompositeDisposable compositeDisposable;

    public TransactionPresenter(AppDatabase db, TransactionContract.View view, ThreadExecutor scheduleProvider) {
        this.db = db;
        this.view = view;
        this.scheduleProvider = scheduleProvider;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void loadCategories() {
        Disposable disposable = Single.fromCallable(() -> db.categoryDao().getAll()).subscribeOn(scheduleProvider.io()).observeOn(scheduleProvider.ui())
                .subscribe(categories -> {
                    view.showCategories(categories);
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void save(Transaction transaction) {
        Disposable disposable = Single.fromCallable(() -> {
            db.transactionDao().insert(transaction);
            return "";
        }).subscribeOn(scheduleProvider.io()).observeOn(scheduleProvider.ui())
                .subscribe(v -> view.finish());
        compositeDisposable.add(disposable);

    }

    @Override
    public void dropView() {

    }
}
