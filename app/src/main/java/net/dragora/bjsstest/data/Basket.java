package net.dragora.bjsstest.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.annimon.stream.Stream;

import net.dragora.bjsstest.commons.Tools;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nietzsche on 26/12/15.
 * <p>
 * Represent the basket a collection of BasketItem
 */
public class Basket implements Parcelable {

    public static final Creator<Basket> CREATOR = new Creator<Basket>() {
        @Override
        public Basket createFromParcel(Parcel in) {
            return new Basket(in);
        }

        @Override
        public Basket[] newArray(int size) {
            return new Basket[size];
        }
    };
    private ArrayList<BasketItem> items = new ArrayList<>(5);

    protected Basket(Parcel in) {
        items = in.readArrayList(BasketItem.class.getClassLoader());
    }

    public Basket() {

    }

    public static Basket fromJson(String json) {
        return Tools.fromString(json, Basket.class);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(items);
    }

    @Override
    public String toString() {
        return Tools.toString(this);
    }

    public void addItem(@Nullable Item item, int count) {
        if (item == null || count <= 0)
            return;

        BasketItem basketItem = Stream.of(items)
                .filter(value -> value.getItem().getId() == item.getId())
                .findFirst()
                .orElse(new BasketItem(item, 0));

        basketItem.addCount(count);
    }

    public ArrayList<BasketItem> getItems() {
        return items;
    }
}