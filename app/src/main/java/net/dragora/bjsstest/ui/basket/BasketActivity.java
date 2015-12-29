package net.dragora.bjsstest.ui.basket;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import net.dragora.bjsstest.MyApplication;
import net.dragora.bjsstest.R;
import net.dragora.bjsstest.data.BasketItem;
import net.dragora.bjsstest.data.BasketStore;
import net.dragora.bjsstest.data.MainPrefs_;
import net.dragora.bjsstest.network.NetworkApi;
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
    @Inject
    NetworkApi networkApi;

    @Bean
    BasketItemsRecyclerAdapter itemsAdapter;

    @Pref
    MainPrefs_ mainPrefs;
    private ItemTouchHelper itemTouchHelper;

    ItemTouchHelper.Callback swipeDismissCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            final int index = viewHolder.getAdapterPosition();
            final BasketItem basketItem = basketStore.getBasket().removeItem(index);
            basketStore.save();
            itemsAdapter.setItems(basketStore.getBasket().getItems());
            Snackbar.make(recyclerView, getString(R.string.basket_item_deleted, basketItem.getItem().getName()), Snackbar.LENGTH_LONG)
                    .setAction(R.string.undo, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            basketStore.getBasket().getItems().add(index, basketItem);
                            basketStore.save();
                            itemsAdapter.setItems(basketStore.getBasket().getItems());
                        }
                    })
                    .show();

        }
    };

    @AfterViews
    protected void setup() {
        MyApplication.getInstance().getGraph().inject(this);
        setSupportActionBar(toolbar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemsAdapter);
        recyclerView.setEmptyView(R.layout.basket_empty_view);


        itemTouchHelper = new ItemTouchHelper(swipeDismissCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView.mRecyclerView);

        networkApi.jsonRatesAPI.getCurrencies().subscribe();
    }

    @Override
    protected void onResume() {
        super.onResume();
        itemsAdapter.setItems(basketStore.getBasket().getItems());
    }

    @OptionsItem(R.id.action_checkout)
    protected void checkout() {

    }

    @Click
    protected void fab() {
        ItemsActivity_.intent(this).start();
    }

    @Override
    public void onItemEdited(BasketItem basketItem) {
        basketStore.save();
        itemsAdapter.setItems(basketStore.getBasket().getItems());

    }

    @Override
    public void onItemDeleted(BasketItem basketItem) {
        final int index = basketStore.getBasket().getItems().indexOf(basketItem);
        basketStore.getBasket().getItems().remove(basketItem);
        basketStore.save();
        itemsAdapter.setItems(basketStore.getBasket().getItems());

        Snackbar.make(recyclerView, getString(R.string.basket_item_deleted, basketItem.getItem().getName()), Snackbar.LENGTH_LONG)
                .setAction(R.string.undo, v -> {
                    basketStore.getBasket().getItems().add(index, basketItem);
                    basketStore.save();
                    itemsAdapter.setItems(basketStore.getBasket().getItems());
                })
                .show();
    }
}
