package net.dragora.bjsstest.data;

import net.dragora.bjsstest.commons.Tools;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by nietzsche on 27/12/15.
 */
@EBean
public class BasketStorePref implements BasketStore {


    @Pref
    MainPrefs_ mainPrefs;
    private Basket basket;
    private BehaviorSubject<Basket> subject = BehaviorSubject.create();

    @AfterInject
    protected void afterInject() {
        subject.onNext(getBasket());
    }
    @Override
    public Basket getBasket() {
        if (basket == null)
            basket = Tools.fromString(mainPrefs.basket().get(), Basket.class);
        if (basket == null) {
            basket = new Basket();
            saveAndNotify();
        }
        return basket;
    }

    @Override
    public void saveAndNotify() {
        mainPrefs.basket().put(Tools.toString(basket));
        subject.onNext(basket);
    }

    @Override
    public BasketItem removeItem(int index) {
        BasketItem item = getBasket().removeItem(index);
        saveAndNotify();
        return item;

    }

    @Override
    public void remove(BasketItem item) {
        getBasket().getItems().remove(item);
        saveAndNotify();
    }

    @Override
    public void add(int index, BasketItem item) {
        getBasket().getItems().add(index, item);
        saveAndNotify();
    }

    @Override
    public void add(Item item, int count) {
        getBasket().addItem(item, count);
        saveAndNotify();
    }

    @Override
    public Observable<Basket> getObservable() {
        return subject.asObservable();
    }
}
