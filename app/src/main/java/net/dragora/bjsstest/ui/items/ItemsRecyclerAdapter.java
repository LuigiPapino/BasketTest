package net.dragora.bjsstest.ui.items;

import android.content.Context;
import android.view.ViewGroup;

import net.dragora.bjsstest.commons.UltimateRecyclerViewAdapterBase;
import net.dragora.bjsstest.commons.ViewWrapper;
import net.dragora.bjsstest.data.Item;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

/**
 * Created by nietzsche on 27/12/15.
 */

@EBean
public class ItemsRecyclerAdapter extends UltimateRecyclerViewAdapterBase<Item, ItemView> {

    @RootContext
    Context context;


    public void update() {

    }



    public void setItems(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewWrapper<ItemView> holder, int position) {
        ItemView view = holder.getView();
        Item item                = items.get(position);
        view.bind(item);
    }



    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    protected ItemView onCreateItemView(ViewGroup parent, int viewType) {
        return ItemView_.build(context);
    }
}
