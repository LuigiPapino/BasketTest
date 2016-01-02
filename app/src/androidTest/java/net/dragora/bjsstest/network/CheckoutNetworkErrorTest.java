package net.dragora.bjsstest.network;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import net.dragora.bjsstest.BasketActivityTest;
import net.dragora.bjsstest.R;
import net.dragora.bjsstest.ui.basket.BasketActivity_;
import net.dragora.bjsstest.ui.checkout.CheckoutDialogFragment_;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by nietzsche on 02/01/16.
 */
@RunWith(AndroidJUnit4.class)

public class CheckoutNetworkErrorTest {
    @Rule
    public ActivityTestRule<BasketActivity_> basketActivityRule = new ActivityTestRule<>(BasketActivity_.class);


    @Test
    public void jsonRatesNetworkError() {
        BasketActivityTest.clearBasket();
        BasketActivityTest.addItems(8);
        onView(withText(R.string.action_checkout)).perform(click());
        CheckoutDialogFragment_ dialog = (CheckoutDialogFragment_) basketActivityRule.getActivity().getSupportFragmentManager().findFragmentByTag("dialog");
        onView(withId(R.id.changeCurrency)).check(matches(isDisplayed()));
        MockJsonRatesAPIService.mock(dialog.networkApi, null);
        onView(withId(R.id.updateExchanges)).perform(click());
        onView(withText(R.string.network_error_exchanges)).check(matches(isDisplayed()));
    }

    @Test
    public void currencyLayerNetworkError() {
        BasketActivityTest.clearBasket();
        BasketActivityTest.addItems(6);
        onView(withText(R.string.action_checkout)).perform(click());
        CheckoutDialogFragment_ dialog = (CheckoutDialogFragment_) basketActivityRule.getActivity().getSupportFragmentManager().findFragmentByTag("dialog");
        MockCurrencyLayerAPIService.mock(dialog.networkApi, null);
        onView(withId(R.id.updateExchanges)).perform(click());
        onView(withText(R.string.network_error_exchanges)).check(matches(isDisplayed()));
    }
}
