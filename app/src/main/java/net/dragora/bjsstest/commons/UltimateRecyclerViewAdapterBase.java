package net.dragora.bjsstest.commons;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nietzsche on 27/12/15.
 */
public abstract class UltimateRecyclerViewAdapterBase<T, V extends View> extends UltimateViewAdapter<ViewWrapper<V>> {

    public List<T> items = new ArrayList<T>();


    public boolean isEmpty() {
        return items != null && items.size() == 0;
    }



    protected abstract V onCreateItemView(ViewGroup parent, int viewType);

    // additional methods to manipulate the items


    @Override
    public long generateHeaderId(int i) {
        return 0;
    }

    @Override
    public ViewWrapper<V> getViewHolder(View view) {
        return null;
    }

    @Override
    public ViewWrapper<V> onCreateViewHolder(ViewGroup viewGroup) {
        return new ViewWrapper<V>(onCreateItemView(viewGroup, 0));
    }

    @Override
    public int getAdapterItemCount() {
        return items.size();
    }

    @Override
    public abstract void onBindViewHolder(ViewWrapper<V> holder, int position);



    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

    }
}