package net.dragora.bjsstest.network;

import com.google.gson.JsonObject;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by nietzsche on 29/12/15.
 */
public interface JsonRatesAPIService {

    @GET("/currencies.json")
    Observable<JsonObject> getCurrencies();
}
