package net.dragora.bjsstest.ui.basket;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import net.dragora.bjsstest.MyApplication;
import net.dragora.bjsstest.R;
import net.dragora.bjsstest.data.BasketItem;
import net.dragora.bjsstest.data.BasketStore;
import net.dragora.bjsstest.data.MainPrefs_;
import net.dragora.bjsstest.ui.checkout.CheckoutDialogFragment_;
import net.dragora.bjsstest.ui.items.ItemsActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import javax.inject.Inject;

@EActivity(R.layout.basket_activity)
@OptionsMenu(R.menu.menu_basket)
public class BasketActivity extends AppCompatActivity implements BasketItemView.OnItemEditedCallback {


    @ViewById
    Toolbar toolbar;
    @ViewById
    FloatingActionButton fab;
    @ViewById
    UltimateRecyclerView recyclerView;

    @Inject
    BasketStore basketStore;

    @Bean
    BasketItemsRecyclerAdapter itemsAdapter;

    @Pref
    MainPrefs_ mainPrefs;

    ItemTouchHelper.Callback swipeDismissCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            final int index = viewHolder.getAdapterPosition();
            final BasketItem basketItem = basketStore.removeItem(index);
            Snackbar.make(recyclerView, getString(R.string.basket_item_deleted, basketItem.getItem().getName()), Snackbar.LENGTH_LONG)
                    .setAction(R.string.undo, v -> basketStore.add(index, basketItem))
                    .show();
        }
    };
    private ItemTouchHelper itemTouchHelper;

    @AfterViews
    protected void setup() {
        MyApplication.getInstance().getGraph().inject(this);
        setSupportActionBar(toolbar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemsAdapter);
        recyclerView.setEmptyView(R.layout.basket_empty_view);


        itemTouchHelper = new ItemTouchHelper(swipeDismissCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView.mRecyclerView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        itemsAdapter.setBasketObservable(basketStore.getObservable());
    }

    @Override
    protected void onPause() {
        super.onPause();
        itemsAdapter.setBasketObservable(null);
    }

    @OptionsItem(R.id.action_checkout)
    protected void checkout() {
        CheckoutDialogFragment_.builder()
                .total(basketStore.getBasket().getTotal())
                .build()
                .show(getSupportFragmentManager(), "dialog");
    }

    @Click
    protected void fab() {
        ItemsActivity_.intent(this).start();
    }

    @Override
    public void onItemEdited(BasketItem basketItem) {
        basketStore.saveAndNotify();

    }

    @Override
    public void onItemDeleted(BasketItem basketItem) {
        final int index = basketStore.getBasket().getItems().indexOf(basketItem);
        basketStore.remove(basketItem);

        Snackbar.make(recyclerView, getString(R.string.basket_item_deleted, basketItem.getItem().getName()), Snackbar.LENGTH_LONG)
                .setAction(R.string.undo, v -> basketStore.add(index, basketItem))
                .show();
    }
}
