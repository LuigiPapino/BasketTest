package net.dragora.bjsstest.injection;

import com.squareup.okhttp.OkHttpClient;

import net.dragora.bjsstest.network.NetworkApi;
import net.dragora.bjsstest.network.NetworkInstrumentation;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.client.Client;
import retrofit.client.OkClient;

/**
 * Created by Pawel Polanski on 5/16/15.
 */
@Module
public final class NetworkModule {

    @Provides
    @Singleton
    public NetworkApi provideNetworkApi() {
        return new NetworkApi();
    }



}