package net.dragora.bjsstest.data;

import rx.Observable;

/**
 * Created by nietzsche on 27/12/15.
 */
public interface BasketStore {

    Basket getBasket();

    void saveAndNotify();

    BasketItem removeItem(int index);

    void remove(BasketItem item);

    void add(int index, BasketItem item);

    void add(Item item, int count);

    Observable<Basket> getObservable();
}
