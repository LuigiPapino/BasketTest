package net.dragora.bjsstest.network;

import android.support.annotation.Nullable;

import com.google.gson.JsonObject;

import retrofit.MockRestAdapter;
import retrofit.RetrofitError;
import rx.Observable;

/**
 * Created by nietzsche on 02/01/16.
 */
public class MockJsonRatesAPIService implements JsonRatesAPIService {

    private JsonObject jsonObject;

    public static void mock(NetworkApi networkApi, @Nullable JsonObject jsonObject) {

        // Setup MockRestAdapter without delays or posible errors
        MockRestAdapter mockRestAdapter = MockRestAdapter.from(networkApi.restAdapterJsonRates);
        mockRestAdapter.setDelay(0);
        mockRestAdapter.setErrorPercentage(0);
        mockRestAdapter.setVariancePercentage(0);


        // Create the mocked service and replace it in
        MockJsonRatesAPIService mockService = new MockJsonRatesAPIService();
        mockService.jsonObject = jsonObject;

        // Replace the service instance with the mocked one
        networkApi.jsonRatesAPI = mockRestAdapter.create(JsonRatesAPIService.class, mockService);
    }


    @Override
    public Observable<JsonObject> getCurrencies() {
        if (jsonObject == null)
            return Observable.error(RetrofitError.unexpectedError("Mock error", new Exception("Mock Exception")));
        else
            return Observable.just(jsonObject);
    }
}
