package com.nyan.budgetapp.server;

import com.nyan.budgetapp.dto.ConversionRate;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CurrencyLayerRepositoryImpl implements CurrencyLayerRepository {
    private CurrencyLayerApi api;

    public CurrencyLayerRepositoryImpl(Retrofit retrofit) {
        this.api = retrofit.create(CurrencyLayerApi.class);
    }

    @Override
    public Observable<Response<ConversionRate>> rates(String accessKey, String currencies, int format) {
        return api.rates(accessKey, currencies, format);
    }
}
