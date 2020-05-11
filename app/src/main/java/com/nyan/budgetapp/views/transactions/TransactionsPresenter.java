package com.nyan.budgetapp.views.transactions;

import com.nyan.budgetapp.R;
import com.nyan.budgetapp.db.AppDatabase;
import com.nyan.budgetapp.dto.ConversionRate;
import com.nyan.budgetapp.server.CurrencyLayerRepository;
import com.nyan.budgetapp.utils.ThreadExecutor;
import com.nyan.budgetapp.vo.Transaction;

import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

public class TransactionsPresenter implements TransactionsContract.Presenter {

    private TransactionsContract.View view;
    private ThreadExecutor threadExecutor;
    private AppDatabase db;
    private CompositeDisposable compositeDisposable;
    CurrencyLayerRepository repo;
    ConversionRate conversionRate;

    public TransactionsPresenter(TransactionsContract.View view, ThreadExecutor threadExecutor, AppDatabase db, CurrencyLayerRepository repo) {
        this.view = view;
        this.threadExecutor = threadExecutor;
        this.db = db;
        compositeDisposable = new CompositeDisposable();
        this.repo = repo;
    }

    private void loadExchangeRate() {
        repo.rates(view.getString(R.string.accessKey), "NZD,USD", 1)
                .subscribeOn(threadExecutor.io())
                .observeOn(threadExecutor.ui())
                .subscribe(new Observer<Response<ConversionRate>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Response<ConversionRate> response) {
                        if (response.isSuccessful()) {
                            conversionRate = response.body();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        loadTransactions();
                    }
                });
    }

    @Override
    public void load() {
        if (conversionRate == null)
            loadExchangeRate();
        else {
            loadTransactions();
        }
    }

    private void loadTransactions() {
        Disposable disposable = Single.fromCallable(() -> db.transactionDao().getAll())
                .subscribeOn(threadExecutor.io())
                .observeOn(threadExecutor.ui())
                .map(records -> {
                    for (Transaction transaction : records) {
                        //TODO This will lose the currency code and amount of USD and changed to USD
                        //FIXME
                        if (transaction.currencyISO.equals("USD")) {
                            transaction.amount = conversionRate.getQuotes().get("USDNZD") * transaction.amount;
                            transaction.currencyISO = "NZD";
                        }
                    }
                    return records;
                })
                .subscribe(records -> {
                    view.show(records);
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void dropView() {
        compositeDisposable.dispose();
    }
}
