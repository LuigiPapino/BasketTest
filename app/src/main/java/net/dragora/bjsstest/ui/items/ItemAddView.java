package net.dragora.bjsstest.ui.items;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import net.dragora.bjsstest.R;
import net.dragora.bjsstest.data.Item;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.text.DecimalFormat;

/**
 * Created by nietzsche on 27/12/15.
 */
@EViewGroup(R.layout.item_add_view)
public class ItemAddView extends LinearLayout {

    @ViewById
    TextView description;
    @ViewById
    NumberPicker numberPicker;
    @ViewById
    TextView total;

    public ItemAddView(Context context) {
        super(context);
    }

    public ItemAddView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemAddView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ItemAddView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @AfterViews
    protected void setup() {
        numberPicker.setMaxValue(99);
        numberPicker.setMinValue(1);
    }

    private Item item;

    public void bind(final Item item) {
        this.item = item;

        description.setText(getItemDescription());
        numberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> total.setText(Html.fromHtml(getContext()
                .getString(R.string.item_add_total, decimalFormat.format((newVal * item.getPrice()) / 100.0))
        )));
    }

    public int getCount() {
        return numberPicker.getValue();
    }

    private String getItemDescription() {
        if (!TextUtils.isEmpty(item.getUnit()))
            return getContext().getString(R.string.item_description_with_unit, item.getName(), item.getPriceFormatted(), item.getUnit());
        else
            return getContext().getString(R.string.item_description, item.getName(), item.getPriceFormatted());
    }

    private static DecimalFormat decimalFormat = new DecimalFormat("#.00");
}
