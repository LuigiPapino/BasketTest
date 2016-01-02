package net.dragora.bjsstest.data;

import android.support.annotation.Nullable;

import com.annimon.stream.Stream;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by nietzsche on 29/12/15.
 */
public class CurrenciesList {

    private Set<Map.Entry<String, String>> currenciesMap = new LinkedHashSet<>();
    private ArrayList<CharSequence> currenciesList;

    private JsonObject jsonObject;

    public CurrenciesList(@Nullable JsonObject jsonObject) {
        if (jsonObject == null)
            return;
        this.jsonObject = jsonObject;
        Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
        currenciesList = new ArrayList<>(entrySet.size());
        Stream.of(entrySet)
                .forEach(entry -> {
                    currenciesMap.add(new AbstractMap.SimpleImmutableEntry<>(entry.getKey(), entry.getValue().getAsString()));
                    currenciesList.add(String.format("%s - %s", entry.getKey(), entry.getValue()));
                });


    }

    public CharSequence[] getArray() {
        return currenciesList.toArray(new CharSequence[currenciesList.size()]);
    }

    public String getCurrency(int index) {
        return Stream.of(currenciesMap)
                .skip(index)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseGet(() -> "");
    }

    public boolean isEmpty() {
        return currenciesList == null || currenciesList.size() == 0;
    }
}
