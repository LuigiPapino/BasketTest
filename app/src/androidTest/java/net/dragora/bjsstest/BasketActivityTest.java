package net.dragora.bjsstest;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.Html;
import android.widget.ListView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import net.dragora.bjsstest.commons.UltimateRecyclerViewAdapterBase;
import net.dragora.bjsstest.data.Item;
import net.dragora.bjsstest.ui.basket.BasketActivity_;
import net.dragora.bjsstest.ui.checkout.CheckoutDialogFragment;
import net.dragora.bjsstest.ui.items.ItemView;
import net.dragora.bjsstest.utils.RecyclerViewClickAction;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;

/**
 * Created by nietzsche on 29/12/15.
 */
@RunWith(AndroidJUnit4.class)
public class BasketActivityTest {

    @Rule
    public ActivityTestRule<BasketActivity_> basketActivityRule = new ActivityTestRule<>(BasketActivity_.class);


    @BeforeClass
    public static void setUp() {

    }

    @AfterClass
    public static void tearDown() {

    }


    public static void changeCurrency(String currency) {
        onView(withId(R.id.changeCurrency)).perform(click());
        onData(hasToString(startsWith(currency)))
                .inAdapterView(instanceOf(ListView.class))
                .atPosition(0)
                .perform(click());
        onView(withText(endsWith(currency.substring(0, 3)))).check(matches(isDisplayed()));
    }

    public static void editItem(int index, int count) {

        onView(withId(R.id.ultimate_list)).perform(RecyclerViewActions.actionOnItemAtPosition(index, RecyclerViewClickAction.clickChildViewWithId(R.id.editItem)));
        onView(withId(R.id.numberPicker)).perform(Tools.setValue(count));
        onView(withText(R.string.ok)).perform(click());
    }

    /**
     * Swipe out all items in basket
     */
    public static void clearBasket() {
        UltimateRecyclerView recyclerView = (UltimateRecyclerView) Tools.getActivityInstance().findViewById(R.id.recyclerView);
        UltimateRecyclerViewAdapterBase<Item, ItemView> adapter = (UltimateRecyclerViewAdapterBase<Item, ItemView>) recyclerView.getAdapter();
        while (adapter.items.size() > 0) {
            onView(withId(R.id.ultimate_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, RecyclerViewClickAction.clickChildViewWithId(R.id.editItem)));
            onView(withText(R.string.delete)).perform(click());
        }
    }

    /**
     * Add one item per type
     *
     * @param count
     */
    public static void addItems(int count) {
        onView(withId(R.id.fab)).perform(click());
        onView(withText(R.string.items)).check(matches(isDisplayed())); //wait for Items Activity

        // add one item per type
        UltimateRecyclerView recyclerView = (UltimateRecyclerView) Tools.getActivityInstance().findViewById(R.id.recyclerView);
        UltimateRecyclerViewAdapterBase adapter = (UltimateRecyclerViewAdapterBase) recyclerView.getAdapter();

        for (int i = 0; i < adapter.items.size(); i++) {
            onView(withId(R.id.ultimate_list)).perform(RecyclerViewActions.actionOnItemAtPosition(i, RecyclerViewClickAction.clickChildViewWithId(R.id.addItem)));
            onView(withId(R.id.numberPicker)).perform(Tools.setValue(count));
            onView(withText(R.string.add)).perform(click());
        }

        //return to basket
        Espresso.pressBack();
        onView(withText(R.string.app_name)).check(matches(isDisplayed()));

    }

    @Test
    public void testCheckoutZeroItems() {
        onView(withText(R.string.app_name)).check(matches(isDisplayed()));
        clearBasket();

        //Checkout
        onView(withText(R.string.action_checkout)).check(matches(isDisplayed())).perform(click());
        String totalZero = Html.fromHtml(basketActivityRule.getActivity().getString(R.string.checkout_total, net.dragora.bjsstest.commons.Tools.formatPrice(0.0), CheckoutDialogFragment.DEFAULT_CURRENCY)).toString();
        onView(withText(totalZero)).check(matches(isDisplayed()));

        //Change Currency to EUR
        String currency = "EUR";
        onView(withId(R.id.changeCurrency)).perform(click());
        onData(hasToString(startsWith(currency)))
                .inAdapterView(instanceOf(ListView.class)).atPosition(0)
                .perform(click());
        totalZero = Html.fromHtml(basketActivityRule.getActivity().getString(R.string.checkout_total, net.dragora.bjsstest.commons.Tools.formatPrice(0.0), currency.subSequence(0, 3))).toString();
        onView(withText(totalZero)).check(matches(isDisplayed()));

    }

    @Test
    public void testUndoDelete() {
        onView(withText(R.string.app_name)).check(matches(isDisplayed()));
        clearBasket();
        addItems(1);
        clearBasket();
        //undo last delete items
        onView(withText(R.string.undo)).perform(click());
        //check list not empty
        editItem(0, 10);
    }

    @Test
    public void testEmptyList() {
        onView(withText(R.string.app_name)).check(matches(isDisplayed()));
        clearBasket();

        onView(withText(R.string.empty_basket)).check(matches(isDisplayed()));
        addItems(5);
        onView(withText(R.string.empty_basket)).check(matches(not(isDisplayed())));
    }

    /**
     * Just a big use case. Not exhaustive
     */
    @Test
    public void testAddAndEditItems() {
        onView(withText(R.string.app_name)).check(matches(isDisplayed()));
        clearBasket();
        addItems(4);

        //edit all basket items
        UltimateRecyclerView recyclerView = (UltimateRecyclerView) Tools.getActivityInstance().findViewById(R.id.recyclerView);
        UltimateRecyclerViewAdapterBase adapter = (UltimateRecyclerViewAdapterBase) recyclerView.getAdapter();

        for (int i = 0; i < adapter.items.size(); i++) {
            editItem(i, i + 3);
        }

        //Checkout
        onView(withText(R.string.action_checkout)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.priceTotal)).check(matches(isDisplayed()));

        //Change Currency to EUR
        changeCurrency("EUR");
        //Change Currency to USD
        changeCurrency("USD");

    }


}
