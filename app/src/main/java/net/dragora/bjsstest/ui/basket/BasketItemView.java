package net.dragora.bjsstest.ui.basket;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.dragora.bjsstest.R;
import net.dragora.bjsstest.data.BasketItem;

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

    public BasketItemView(Context context) {
        super(context);
    }

    public BasketItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BasketItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
}
