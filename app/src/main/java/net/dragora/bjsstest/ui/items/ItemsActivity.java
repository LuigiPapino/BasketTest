package net.dragora.bjsstest.ui.items;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import net.dragora.bjsstest.MyApplication;
import net.dragora.bjsstest.R;
import net.dragora.bjsstest.data.Basket;
import net.dragora.bjsstest.data.BasketStore;
import net.dragora.bjsstest.data.Item;
import net.dragora.bjsstest.data.ItemsStore;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

@EActivity(R.layout.items_activity)
@OptionsMenu(R.menu.menu_items)
public class ItemsActivity extends AppCompatActivity implements ItemView.OnItemAddedCallback {

    @ViewById
    Toolbar toolbar;
    @ViewById
    UltimateRecyclerView recyclerView;

    @Bean
    ItemsRecyclerAdapter itemsAdapter;

    @OptionsMenuItem
    MenuItem actionSearch;

    @Inject
    ItemsStore itemsStore;
    @Inject
    BasketStore basketStore;
    SearchView searchView;

    @AfterViews
    protected void setup() {
        MyApplication.getInstance().getGraph().inject(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemsAdapter);
        itemsAdapter.setItems(itemsStore.getAll());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        searchView = (SearchView) actionSearch.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                itemsAdapter.setItems(itemsStore.get(newText));
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onItemAdded(Item item, int count) {
        Basket basket = basketStore.getBasket();
        basket.addItem(item, count);
        basketStore.save();
        Snackbar.make(recyclerView, getString(R.string.item_added, count, item.getName()), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        searchView.setOnQueryTextListener(null);
        super.onDestroy();

    }
}
