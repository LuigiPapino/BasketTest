package net.dragora.bjsstest.ui.checkout;


import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import net.dragora.bjsstest.MyApplication;
import net.dragora.bjsstest.R;
import net.dragora.bjsstest.commons.Tools;
import net.dragora.bjsstest.data.CurrenciesList;
import net.dragora.bjsstest.data.ExchangesList;
import net.dragora.bjsstest.network.NetworkApi;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import rx.internal.util.SubscriptionList;


@EFragment(R.layout.checkout_dialog_fragment)
public class CheckoutDialogFragment extends DialogFragment {

    public static final String DEFAULT_CURRENCY = "GBP";
    private static final String TAG = CheckoutDialogFragment.class.getSimpleName();
    @Inject
    @VisibleForTesting
    public NetworkApi networkApi;
    @VisibleForTesting
    public CurrenciesList currenciesList;
    @FragmentArg
    int total;
    @ViewById
    TextView priceTotal;
    @ViewById
    Button changeCurrency;
    @ViewById
    ImageView updateExchanges;
    @ViewById
    ProgressBar progressBar;
    @ViewById
    TextView statusLabel;
    @SystemService
    ConnectivityManager connectivityManager;
    private ExchangesList exchangesList;
    private SubscriptionList subscriptionList = new SubscriptionList();
    //track if both api call was terminated
    private AtomicInteger networkCallCompleted = new AtomicInteger(0);

    @AfterViews
    protected void setup() {
        MyApplication.getInstance().getGraph().inject(this);
        //backgroundTint doesn't work on Lollipop 5.0
        changeCurrency.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);

        updatePrice(DEFAULT_CURRENCY);
        updateExchanges();

    }

    private void updatePrice(String currency) {
        double totalExchanged = total;
        if (!currency.contentEquals(DEFAULT_CURRENCY)) {
            totalExchanged *= exchangesList.getExchange(DEFAULT_CURRENCY, currency);
        }

        priceTotal.setText(
                Html.fromHtml(
                        getString(R.string.checkout_total, Tools.formatPrice(totalExchanged / 100.0), currency)
                ));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        subscriptionList.unsubscribe();
        subscriptionList.clear();
        MyApplication.getInstance().getRefWatcher().watch(this);

    }

    @Click
    protected void changeCurrency() {

        new AlertDialog.Builder(getContext(), R.style.MyDialog)
                .setItems(currenciesList.getArray(), (dialog, which) -> {
                    updatePrice(currenciesList.getCurrency(which));
                })
                .show();
    }

    @Click
    protected void updateExchanges() {
        if (!checkConnection())
            return;

        subscriptionList.unsubscribe();
        subscriptionList.clear();
        subscriptionList = new SubscriptionList();
        networkCallCompleted.set(0);

        changeCurrency.animate().alpha(0).start();
        updateExchanges.animate().alpha(0).start();
        progressBar.setVisibility(View.VISIBLE);
        statusLabel.setText(R.string.updating_currency);

        subscriptionList.add(
                networkApi.jsonRatesAPI.getCurrencies()
                        .subscribe(json -> {
                                    currenciesList = new CurrenciesList(json);
                                    if (currenciesList.isEmpty())
                                        setNetworkError(null);
                                    else if (networkCallCompleted.incrementAndGet() == 2)
                                        exchangesUpdated();
                                },
                                this::setNetworkError

                        )
        );
        subscriptionList.add(
                networkApi.currencyLayerAPIService.getExchangesList()
                        .subscribe(value -> {
                                    if (value != null) {
                                        exchangesList = value;
                                        if (networkCallCompleted.incrementAndGet() == 2)
                                            exchangesUpdated();
                                    } else
                                        setNetworkError(null);
                                },
                                this::setNetworkError)
        );
    }

    private boolean checkConnection() {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isAvailable()) {
            statusLabel.setText(R.string.no_connection);
            progressBar.setVisibility(View.GONE);
            changeCurrency.setVisibility(View.INVISIBLE);
            return false;
        }
        return true;
    }

    @UiThread
    @android.support.annotation.UiThread
    protected void setNetworkError(Throwable throwable) {
        if (throwable != null)
            throwable.printStackTrace();

        networkCallCompleted.set(0);
        subscriptionList.unsubscribe();
        changeCurrency.animate().alpha(0).start();
        updateExchanges.animate().alpha(1).start();
        progressBar.setVisibility(View.GONE);

        statusLabel.setText(R.string.network_error_exchanges);
    }

    @UiThread
    @android.support.annotation.UiThread
    protected void exchangesUpdated() {
        changeCurrency.animate().alpha(1).start();
        updateExchanges.animate().alpha(1).start();
        progressBar.setVisibility(View.GONE);
        statusLabel.setText(getString(R.string.currency_update_to, exchangesList.getTimestampFormatted()));
        subscriptionList.unsubscribe();
    }
}
