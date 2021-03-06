package net.dragora.bjsstest.injection;

import net.dragora.bjsstest.network.NetworkApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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