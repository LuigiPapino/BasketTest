package net.dragora.bjsstest.network;

import android.support.annotation.NonNull;

import net.dragora.bjsstest.BuildConfig;

import retrofit.RestAdapter;
import retrofit.client.Client;


public class NetworkApi {

    public static final String BASE_HOST_JSONRATES = "http://jsonrates.com/";


    private RestAdapter restAdapter;

    public JsonRatesAPIService jsonRatesAPI;


    public NetworkApi() {



        restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_HOST_JSONRATES)
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .build();


        jsonRatesAPI = restAdapter.create(JsonRatesAPIService.class);
    }

}