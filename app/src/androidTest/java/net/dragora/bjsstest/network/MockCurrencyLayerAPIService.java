package net.dragora.bjsstest.network;

import android.support.annotation.Nullable;

import net.dragora.bjsstest.data.ExchangesList;

import retrofit.MockRestAdapter;
import retrofit.RetrofitError;
import rx.Observable;

/**
 * Created by nietzsche on 02/01/16.
 */
public class MockCurrencyLayerAPIService implements CurrencyLayerAPIService {

    private ExchangesList exchangesList;

    public static void mock(NetworkApi networkApi, @Nullable ExchangesList jsonObject) {

        // Setup MockRestAdapter without delays or posible errors
        MockRestAdapter mockRestAdapter = MockRestAdapter.from(networkApi.restAdapterCurrencyLayer);
        mockRestAdapter.setDelay(0);
        mockRestAdapter.setErrorPercentage(0);
        mockRestAdapter.setVariancePercentage(0);


        // Create the mocked service and replace it in
        MockCurrencyLayerAPIService mockService = new MockCurrencyLayerAPIService();
        mockService.exchangesList = jsonObject;

        // Replace the service instance with the mocked one
        networkApi.currencyLayerAPIService = mockRestAdapter.create(CurrencyLayerAPIService.class, mockService);
    }


    @Override
    public Observable<ExchangesList> getExchangesList() {
        if (exchangesList == null)
            return Observable.error(RetrofitError.unexpectedError("Mock error", new Exception("Mock Exception")));
        else
            return Observable.just(exchangesList);
    }
}
