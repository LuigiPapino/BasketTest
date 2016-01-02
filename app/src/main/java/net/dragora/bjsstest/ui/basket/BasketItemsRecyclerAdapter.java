package net.dragora.bjsstest.ui.basket;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import net.dragora.bjsstest.commons.UltimateRecyclerViewAdapterBase;
import net.dragora.bjsstest.commons.ViewWrapper;
import net.dragora.bjsstest.data.Basket;
import net.dragora.bjsstest.data.BasketItem;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

import rx.Observable;
import rx.Subscription;

/**
 * Created by nietzsche on 27/12/15.
 */

@EBean
public class BasketItemsRecyclerAdapter extends UltimateRecyclerViewAdapterBase<BasketItem, BasketItemView> {

    @RootContext
    Context context;
    private Subscription subscription;

    private void setItems(List<BasketItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setBasketObservable(@Nullable Observable<Basket> observable) {
        if (subscription != null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
        if (observable != null)
            subscription = observable.subscribe(basket -> setItems(basket.getItems()));
    }

    @Override
    public void onBindViewHolder(ViewWrapper<BasketItemView> holder, int position) {
        BasketItemView view = holder.getView();
        BasketItem item = items.get(position);
        view.bind(item);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    protected BasketItemView onCreateItemView(ViewGroup parent, int viewType) {
        return BasketItemView_.build(context);
    }

}
