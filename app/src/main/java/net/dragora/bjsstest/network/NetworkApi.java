package net.dragora.bjsstest.network;

import com.google.gson.JsonObject;

import net.dragora.bjsstest.data.ExchangesList;

import rx.Observable;

/**
 * Created by nietzsche on 11/06/16.
 */
public interface NetworkApi {

    Observable<JsonObject> getCurrencies();

    Observable<ExchangesList> getExchangesList();



}
