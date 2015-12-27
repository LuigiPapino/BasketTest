package net.dragora.bjsstest.data;

import android.os.Parcel;
import android.os.Parcelable;

import net.dragora.bjsstest.commons.Tools;

/**
 * Created by nietzsche on 26/12/15.
 *
 * Represent and item add to the basket. Incapsulate an Item object and add the parameter count
 */
public class BasketItem implements Parcelable {

    public static final Creator<BasketItem> CREATOR = new Creator<BasketItem>() {
        @Override
        public BasketItem createFromParcel(Parcel in) {
            return new BasketItem(in);
        }

        @Override
        public BasketItem[] newArray(int size) {
            return new BasketItem[size];
        }
    };
    private Item item;
    private int count;

    public int getTotalPrice(){
        return item.getPrice() * count;
    }

    public String getTotalPriceFormatted(){
        return Tools.formatPrice(getTotalPrice());
    }
    protected BasketItem(Parcel in) {
        item = in.readParcelable(Item.class.getClassLoader());
        count = in.readInt();
    }

    public BasketItem(Item item, int count) {
        this.item = item;
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BasketItem that = (BasketItem) o;

        if (count != that.count) return false;
        return item != null ? item.equals(that.item) : that.item == null;

    }

    @Override
    public int hashCode() {
        int result = item != null ? item.hashCode() : 0;
        result = 31 * result + count;
        return result;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(item, 0);
        dest.writeInt(count);
    }

    public int addCount(int count) {
        this.count += count;
        return this.count;
    }
}
