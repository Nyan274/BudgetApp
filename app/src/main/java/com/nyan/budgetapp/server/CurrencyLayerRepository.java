package com.nyan.budgetapp.server;

import com.nyan.budgetapp.dto.ConversionRate;

import io.reactivex.Observable;
import retrofit2.Response;

public interface CurrencyLayerRepository {
    Observable<Response<ConversionRate>> rates(String accessKey, String currencies, int format);
}
