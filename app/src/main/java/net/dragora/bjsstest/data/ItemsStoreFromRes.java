package net.dragora.bjsstest.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import net.dragora.bjsstest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nietzsche on 27/12/15.
 */
public class ItemsStoreFromRes implements ItemsStore {

    private Context context;

    private ArrayList<Item> items = new ArrayList<>();

    public ItemsStoreFromRes(@NonNull Context context) {
        this.context = context;
        String[] names = context.getResources().getStringArray(R.array.items_name);
        String[] units = context.getResources().getStringArray(R.array.items_unit);
        int[] prices = context.getResources().getIntArray(R.array.items_price);

        if (names.length != prices.length || names.length != units.length ||  names.length == 0)
            throw new IllegalArgumentException("Items array length for names, prices or units not equal or zero");

        for (int i = 0; i < prices.length; i++) {
            Item item = new Item(i, prices[i], names[i], units[i]);
            items.add(i, item);
        }
    }

    @Override
    public List<Item> getAll() {
        return items;
    }

    @Override
    public List<Item> get(String filter) {
        if (TextUtils.isEmpty(filter))
            return  items;
        String filterLower = filter.toLowerCase();
        ArrayList<Item> itemsFiltered = new ArrayList<>(5);
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if (item.getName().toLowerCase().contains(filterLower))
                itemsFiltered.add(item);
        }
        return itemsFiltered;
    }
}
