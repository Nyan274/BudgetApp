package com.nyan.budgetapp.server;

import com.nyan.budgetapp.dto.ConversionRate;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CurrencyLayerApi {
    @GET("live")
    Observable<Response<ConversionRate>> rates(@Query("access_key") String accessKey, @Query("currencies") String currencies, @Query("format") int format);

}
