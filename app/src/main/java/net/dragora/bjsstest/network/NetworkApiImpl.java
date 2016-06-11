package net.dragora.bjsstest.network;

import android.support.annotation.VisibleForTesting;

import com.google.gson.JsonObject;

import net.dragora.bjsstest.BuildConfig;
import net.dragora.bjsstest.data.ExchangesList;

import retrofit.RestAdapter;
import rx.Observable;


public class NetworkApiImpl implements NetworkApi {

    public static final String BASE_HOST_JSONRATES = "https://openexchangerates.org/api/";
    public static final String BASE_HOST_CURRENCYLAYER = "http://apilayer.net/api/";
    public static final String ACCESS_KEY = "471fdf34334c9ad8b8e1c91b3b60a5cc";
    public JsonRatesAPIService jsonRatesAPI;
    public CurrencyLayerAPIService currencyLayerAPIService;

    @VisibleForTesting
    public RestAdapter restAdapterJsonRates, restAdapterCurrencyLayer;

    public NetworkApiImpl() {

        restAdapterJsonRates = new RestAdapter.Builder()
                .setEndpoint(BASE_HOST_JSONRATES)
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .build();

        restAdapterCurrencyLayer = new RestAdapter.Builder()
                .setEndpoint(BASE_HOST_CURRENCYLAYER)
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .setRequestInterceptor(request -> request.addEncodedQueryParam("access_key", ACCESS_KEY))
                .build();

        jsonRatesAPI = restAdapterJsonRates.create(JsonRatesAPIService.class);
        currencyLayerAPIService = restAdapterCurrencyLayer.create(CurrencyLayerAPIService.class);
    }

    @Override
    public Observable<JsonObject> getCurrencies() {
        return jsonRatesAPI.getCurrencies();
    }

    @Override
    public Observable<ExchangesList> getExchangesList() {
        return currencyLayerAPIService.getExchangesList();
    }
}