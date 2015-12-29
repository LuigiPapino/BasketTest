package net.dragora.bjsstest.ui.basket;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import net.dragora.bjsstest.R;
import net.dragora.bjsstest.data.BasketItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.text.DecimalFormat;

/**
 * Created by nietzsche on 27/12/15.
 */
@EViewGroup(R.layout.basket_item_edit_view)
public class BasketItemEditView extends LinearLayout {

    private static DecimalFormat decimalFormat = new DecimalFormat("#.00");
    @ViewById
    TextView description;
    @ViewById
    NumberPicker numberPicker;
    @ViewById
    TextView total;
    private BasketItem basketItem;

    public BasketItemEditView(Context context) {
        super(context);
    }

    public BasketItemEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BasketItemEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BasketItemEditView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @AfterViews
    protected void setup() {
        numberPicker.setMaxValue(99);
        numberPicker.setMinValue(1);
    }

    public void bind(final BasketItem basketItem) {
        this.basketItem = basketItem;
        if (basketItem.getCount() < 99)
            numberPicker.setValue(basketItem.getCount());
        description.setText(getItemDescription());
        numberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> total.setText(Html.fromHtml(getContext()
                .getString(R.string.item_add_total, decimalFormat.format((newVal * basketItem.getItem().getPrice()) / 100.0))
        )));
    }

    public int getCount() {
        return numberPicker.getValue();
    }

    private String getItemDescription() {
        if (!TextUtils.isEmpty(basketItem.getItem().getUnit()))
            return getContext().getString(R.string.item_description_with_unit, basketItem.getItem().getName(), basketItem.getItem().getPriceFormatted(), basketItem.getItem().getUnit());
        else
            return getContext().getString(R.string.item_description, basketItem.getItem().getName(), basketItem.getItem().getPriceFormatted());
    }
}
