package net.dragora.bjsstest.data;

import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nietzsche on 29/12/15.
 */
public class ExchangesList {

    private String sources;
    private JsonObject quotes;
    private long timestamp;

    public double getExchange(String from, String to) {
        // Quotes are not available directly from one currency to another.
        // We only have the quotes from USD to all, indeed we need an indirect conversion, first to USD and then to the final currency
        double USD2from = getQuote("USD" + from);
        if (USD2from == 0d) // not exist
            return 0d;
        double from2USD = 1 / USD2from;
        double USD2to = getQuote("USD" + to);
        if (USD2to == 0d)
            return 0d;

        return from2USD * USD2to;
    }

    private double getQuote(String key) {
        if (quotes.has(key))
            return quotes.get(key).getAsDouble();
        else
            return 0d;

    }

    public String getTimestampFormatted() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        Date date = new Date(timestamp * 1000);
        return simpleDateFormat.format(date);
    }
}
