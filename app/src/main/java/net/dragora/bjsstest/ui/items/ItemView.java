package net.dragora.bjsstest.ui.items;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.dragora.bjsstest.R;
import net.dragora.bjsstest.data.Item;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by nietzsche on 27/12/15.
 */
@EViewGroup(R.layout.item_view)
public class ItemView extends RelativeLayout {
    @ViewById
    TextView price;
    @ViewById
    TextView name;
    @ViewById
    ImageView addItem;
    private Item item;

    public ItemView(Context context) {
        super(context);
    }

    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public ItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        if (!(context instanceof OnItemAddedCallback))
            throw new ClassCastException("This activity Must implement ItemView.OnItemAdded");

    }

    @AfterViews
    protected void setup() {
        setOnClickListener(v -> addItem());
    }

    @Click
    protected void addItem() {
        ItemAddView view = ItemAddView_.build(getContext());
        view.bind(item);
        new AlertDialog.Builder(getContext(), R.style.MyDialog)
                .setView(view)
                .setPositiveButton(R.string.add, (dialog, which) -> {

                    OnItemAddedCallback callback = (OnItemAddedCallback) getContext();
                    callback.onItemAdded(item, view.getCount());
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    public void bind(Item item) {
        this.item = item;
        price.setText(getContext().getString(R.string.money, item.getPriceFormatted()));
        name.setText(item.getNameWithUnit());

    }

    public interface OnItemAddedCallback {
        void onItemAdded(Item item, int count);
    }
}
