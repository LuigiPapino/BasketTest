package net.dragora.bjsstest.ui.basket;

import android.content.Context;
import android.view.ViewGroup;

import net.dragora.bjsstest.commons.UltimateRecyclerViewAdapterBase;
import net.dragora.bjsstest.commons.ViewWrapper;
import net.dragora.bjsstest.data.BasketItem;
import net.dragora.bjsstest.data.Item;
import net.dragora.bjsstest.ui.items.ItemView;
import net.dragora.bjsstest.ui.items.ItemView_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

/**
 * Created by nietzsche on 27/12/15.
 */

@EBean
public class BasketItemsRecyclerAdapter extends UltimateRecyclerViewAdapterBase<BasketItem, BasketItemView> {

    @RootContext
    Context context;


    public void setItems(List<BasketItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewWrapper<BasketItemView> holder, int position) {
        BasketItemView view = holder.getView();
        BasketItem item                = items.get(position);
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
