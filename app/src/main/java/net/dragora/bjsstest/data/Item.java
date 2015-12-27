package net.dragora.bjsstest.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import net.dragora.bjsstest.commons.Tools;

import java.text.DecimalFormat;

/**
 * Created by nietzsche on 26/12/15.
 *
 * Represent and item from the shop, with id, price, name and unit of mesure
 */
public class Item implements Parcelable {

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
    private int id;
    private int price;
    private String name = "";
    private String unit = "";

    protected Item(Parcel in) {
        id = in.readInt();
        price = in.readInt();
        name = in.readString();
        unit = in.readString();
    }

    public Item(int id, int price, String name, String unit) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        if (unit == null)
            this.unit = "";
        else
            this.unit = unit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getNameWithUnit() {
        if (!TextUtils.isEmpty(unit))
            return String.format("%s (%s)", name, unit);
        else
            return name;
    }

    public void setName(String name) {
        if (name == null)
            this.name = "";
        else
            this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(price);
        dest.writeString(name);
        dest.writeString(unit);
    }

    public String getPriceFormatted() {
        return Tools.formatPrice(price / 100.0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (id != item.id) return false;
        if (price != item.price) return false;
        if (!name.equals(item.name)) return false;
        return unit.equals(item.unit);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + price;
        result = 31 * result + name.hashCode();
        result = 31 * result + unit.hashCode();
        return result;
    }


}
