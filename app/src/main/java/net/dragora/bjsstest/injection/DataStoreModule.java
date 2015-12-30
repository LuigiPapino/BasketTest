package net.dragora.bjsstest.injection;

import android.content.Context;

import net.dragora.bjsstest.data.BasketStore;
import net.dragora.bjsstest.data.BasketStorePref_;
import net.dragora.bjsstest.data.ItemsStore;
import net.dragora.bjsstest.data.ItemsStoreFromRes;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nietzsche on 27/12/15.
 */

@Module
public class DataStoreModule {

    @Singleton
    @Provides
    ItemsStore provideItemsStore(@ForApplication  Context context){
        return new ItemsStoreFromRes(context);
    }

    @Singleton
    @Provides
    BasketStore provideBasketStore(@ForApplication Context context){
        return BasketStorePref_.getInstance_(context);
    }
}
