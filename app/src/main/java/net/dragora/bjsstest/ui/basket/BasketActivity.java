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
import com.marshalchen.ultimaterecyclerview.itemTouchHelper.SimpleItemTouchHelperCallback;

import net.dragora.bjsstest.MyApplication;
import net.dragora.bjsstest.R;
import net.dragora.bjsstest.data.BasketStore;
import net.dragora.bjsstest.data.MainPrefs_;
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
public class BasketActivity extends AppCompatActivity {


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
    private ItemTouchHelper mItemTouchHelper;

    @AfterViews
    protected void setup() {
        MyApplication.getInstance().getGraph().inject(this);
        setSupportActionBar(toolbar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemsAdapter);
        recyclerView.setEmptyView(R.layout.basket_empty_view);

        ItemTouchHelper.Callback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                basketStore.getBasket().removeItem(viewHolder.getAdapterPosition());
                basketStore.save();
                itemsAdapter.setItems(basketStore.getBasket().getItems());


            }
        };
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView.mRecyclerView);

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

}
