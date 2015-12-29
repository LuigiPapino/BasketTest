package net.dragora.bjsstest.ui.basket;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.dragora.bjsstest.R;
import net.dragora.bjsstest.data.BasketItem;
import net.dragora.bjsstest.data.Item;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by nietzsche on 27/12/15.
 */
@EViewGroup(R.layout.basket_item_view)
public class BasketItemView extends RelativeLayout {
    @ViewById
    TextView priceTotal;
    @ViewById
    TextView price;
    @ViewById
    TextView count;
    @ViewById
    TextView name;

    public interface OnItemEditedCallback {
        void onItemEdited(BasketItem basketItem);
        void onItemDeleted(BasketItem basketItem);
    }
    public BasketItemView(Context context) {
        super(context);
    }

    public BasketItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BasketItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!(context instanceof OnItemEditedCallback))
            throw new ClassCastException("This activity Must implement BasketItemView.OnItemEditedCallback");
    }

    public BasketItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private BasketItem basketItem;
    public void bind(BasketItem basketItem){
        this.basketItem = basketItem;

        name.setText(basketItem.getItem().getName());
        price.setText(getContext().getString(R.string.money, basketItem.getItem().getPriceFormatted()));
        priceTotal.setText(getContext().getString(R.string.money, basketItem.getTotalPriceFormatted()));
        count.setText(String.valueOf(basketItem.getCount()));
    }

    @Click
    protected void editItem(){
        BasketItemEditView view = BasketItemEditView_.build(getContext());
        view.bind(basketItem);
        new AlertDialog.Builder(getContext(), R.style.MyDialog)
                .setView(view)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    OnItemEditedCallback callback = (OnItemEditedCallback) getContext();
                    basketItem.setCount(view.getCount());
                    callback.onItemEdited(basketItem);
                })
                .setNegativeButton(R.string.delete, (dialog, which) -> {
                    OnItemEditedCallback callback = (OnItemEditedCallback) getContext();
                    callback.onItemDeleted(basketItem);
                })
                .setNeutralButton(R.string.cancel, null)
                .show();
    }
}
