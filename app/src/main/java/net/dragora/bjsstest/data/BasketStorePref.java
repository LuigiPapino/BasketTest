package net.dragora.bjsstest.data;

import android.content.Context;

import net.dragora.bjsstest.commons.Tools;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * Created by nietzsche on 27/12/15.
 */
@EBean
public class BasketStorePref implements BasketStore {


    private Basket basket;

    @Pref
    MainPrefs_ mainPrefs;

    @Override
    public Basket getBasket() {
        if (basket == null)
            basket = Tools.fromString(mainPrefs.basket().get(), Basket.class);
        if (basket == null){
            basket = new Basket();
            save();
        }
        return basket;
    }

    @Override
    public void save() {
        mainPrefs.basket().put(Tools.toString(basket));
    }
}
