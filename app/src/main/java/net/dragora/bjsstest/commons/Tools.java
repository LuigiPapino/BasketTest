package net.dragora.bjsstest.commons;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DecimalFormat;

/**
 * Created by nietzsche on 26/12/15.
 */
public class Tools {


    private static Gson gson = new GsonBuilder().create();
    private static DecimalFormat decimalFormat = new DecimalFormat("#.00");

    public static String formatPrice(double price){
        return decimalFormat.format(price);
    }
    public static String toString(Object item) {
        if (item == null)
            return "";
        else
            return gson.toJson(item);

    }

    public static <T> T fromString(String json, Class<T> tClass) {
        return gson.fromJson(json, tClass);
    }
}
