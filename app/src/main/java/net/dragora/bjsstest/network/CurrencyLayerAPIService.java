package net.dragora.bjsstest.network;

import net.dragora.bjsstest.data.ExchangesList;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by nietzsche on 29/12/15.
 */
public interface CurrencyLayerAPIService {

    @GET("/live")
    Observable<ExchangesList> getExchangesList();
}
