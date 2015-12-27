package net.dragora.bjsstest.injection;

import android.app.Application;

import net.dragora.bjsstest.ui.basket.BasketActivity;
import net.dragora.bjsstest.ui.items.ItemView;
import net.dragora.bjsstest.ui.items.ItemsActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, DataStoreModule.class, })
public interface Graph {


    void inject(ItemsActivity itemsActivity);

    void inject(BasketActivity basketActivity);


    final class Initializer {

        public static Graph init(Application application) {
            return DaggerGraph.builder()
                    .applicationModule(new ApplicationModule(application))
                    .build();
        }

    }
}