package net.dragora.bjsstest;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import net.dragora.bjsstest.ui.basket.BasketActivity_;
import net.dragora.bjsstest.ui.checkout.CheckoutDialogFragment;
import net.dragora.bjsstest.ui.checkout.CheckoutDialogFragment_;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.endsWith;

/**
 * Created by nietzsche on 02/01/16.
 */
@RunWith(AndroidJUnit4.class)
public class CheckoutAllCurrenciesTest {

    @Rule
    public ActivityTestRule<BasketActivity_> basketActivityRule = new ActivityTestRule<>(BasketActivity_.class);

    @Test
    public void checkoutAllCurrency() {
        onView(withText(R.string.app_name)).check(matches(isDisplayed()));
        BasketActivityTest.clearBasket();
        BasketActivityTest.addItems(6);

        //Checkout
        onView(withText(R.string.action_checkout)).check(matches(isDisplayed())).perform(click());
        onView(withText(endsWith(CheckoutDialogFragment.DEFAULT_CURRENCY))).check(matches(isDisplayed()));

        CheckoutDialogFragment_ dialog = (CheckoutDialogFragment_) basketActivityRule.getActivity().getSupportFragmentManager().findFragmentByTag("dialog");

        for (CharSequence currency : dialog.currenciesList.getArray()) {
            BasketActivityTest.changeCurrency(currency.toString());
        }
    }
}
