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

        NetworkApiImpl networkApiImpl = (NetworkApiImpl) networkApi;
        // Setup MockRestAdapter without delays or possible errors
        MockRestAdapter mockRestAdapter = MockRestAdapter.from(networkApiImpl.restAdapterCurrencyLayer);
        mockRestAdapter.setDelay(0);
        mockRestAdapter.setErrorPercentage(0);
        mockRestAdapter.setVariancePercentage(0);


        // Create the mocked service and replace it in
        MockCurrencyLayerAPIService mockService = new MockCurrencyLayerAPIService();
        mockService.exchangesList = jsonObject;

        // Replace the service instance with the mocked one
        networkApiImpl.currencyLayerAPIService = mockRestAdapter.create(CurrencyLayerAPIService.class, mockService);
    }


    @Override
    public Observable<ExchangesList> getExchangesList() {
        if (exchangesList == null)
            return Observable.error(RetrofitError.unexpectedError("Mock error", new Exception("Mock Exception")));
        else
            return Observable.just(exchangesList);
    }
}
