package net.dragora.bjsstest;

import android.app.Activity;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.view.View;
import android.widget.NumberPicker;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import net.dragora.bjsstest.commons.UltimateRecyclerViewAdapterBase;
import net.dragora.bjsstest.data.Item;
import net.dragora.bjsstest.ui.basket.BasketActivity_;
import net.dragora.bjsstest.ui.items.ItemView;
import net.dragora.bjsstest.utils.RecyclerViewClickAction;

import org.hamcrest.Matcher;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.runner.lifecycle.Stage.RESUMED;

/**
 * Created by nietzsche on 29/12/15.
 */
@RunWith(AndroidJUnit4.class)
public class BasketActivityTest {

    @Rule
    public ActivityTestRule<BasketActivity_> basketActivityRule = new ActivityTestRule<>(BasketActivity_.class);
    private Activity currentActivity;


    @BeforeClass
    public static void setUp() {

    }

    @AfterClass
    public static void tearDown() {

    }

    public static ViewAction setValue(int value) {
        return new ViewAction() {
            @Override
            public void perform(UiController uiController, View view) {
                NumberPicker np = (NumberPicker) view;
                np.setValue(value);

            }

            @Override
            public String getDescription() {
                return "Set the passed value into the NumberPicker";
            }

            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(NumberPicker.class);
            }
        };
    }

    /*
    Just a big use case. Not exhaustive but I have no more time
     */
    @Test
    public void testAddItems() {
        onView(withText(R.string.app_name)).check(matches(isDisplayed()));
        clearBasket();
        onView(withId(R.id.fab)).perform(click());
        onView(withText(R.string.items)).check(matches(isDisplayed())); //wait for Items Activity

        // add one item per type
        UltimateRecyclerView recyclerView = (UltimateRecyclerView) getActivityInstance().findViewById(R.id.recyclerView);
        UltimateRecyclerViewAdapterBase adapter = (UltimateRecyclerViewAdapterBase) recyclerView.getAdapter();

        for (int i = 0; i < adapter.items.size(); i++) {
            onView(withId(R.id.ultimate_list)).perform(RecyclerViewActions.actionOnItemAtPosition(i, RecyclerViewClickAction.clickChildViewWithId(R.id.addItem)));
            onView(withText(R.string.add)).perform(click());
        }

        //return to basket
        Espresso.pressBack();
        onView(withText(R.string.app_name)).check(matches(isDisplayed()));

        //edit all basket items
        recyclerView = (UltimateRecyclerView) getActivityInstance().findViewById(R.id.recyclerView);
        adapter = (UltimateRecyclerViewAdapterBase) recyclerView.getAdapter();
        for (int i = 0; i < adapter.items.size(); i++) {
            onView(withId(R.id.ultimate_list)).perform(RecyclerViewActions.actionOnItemAtPosition(i, RecyclerViewClickAction.clickChildViewWithId(R.id.editItem)));
            onView(withId(R.id.numberPicker)).perform(setValue(3));
            onView(withText(R.string.ok)).perform(click());
        }

        //Checkout
        onView(withText(R.string.action_checkout)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.changeCurrency)).check(matches(isDisplayed())).perform(click());

    }

    private void clearBasket() {
        UltimateRecyclerView recyclerView = (UltimateRecyclerView) getActivityInstance().findViewById(R.id.recyclerView);
        UltimateRecyclerViewAdapterBase<Item, ItemView> adapter = (UltimateRecyclerViewAdapterBase<Item, ItemView>) recyclerView.getAdapter();
        while (adapter.items.size() > 0) {
            onView(withId(R.id.ultimate_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, RecyclerViewClickAction.clickChildViewWithId(R.id.editItem)));
            onView(withText(R.string.delete)).perform(click());
        }

    }

    public Activity getActivityInstance() {
        getInstrumentation().runOnMainSync(new Runnable() {
            public void run() {
                Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(RESUMED);
                if (resumedActivities.iterator().hasNext()) {
                    currentActivity = (Activity) resumedActivities.iterator().next();
                }
            }
        });

        return currentActivity;
    }
}
