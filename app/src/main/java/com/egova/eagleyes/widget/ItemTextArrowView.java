package com.egova.eagleyes.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.egova.eagleyes.R;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

/**
 * 布局如下：
 * 标题   内容  右箭头
 */
public class ItemTextArrowView extends LinearLayout {

    private TextView titleTV, contentTV;
    private ImageView arrowImage;

    public ItemTextArrowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.item_text_arrow_view, this, true);
        titleTV = findViewById(R.id.title);
        contentTV = findViewById(R.id.content);
        arrowImage = findViewById(R.id.arrow);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemTextArrowView);

        String titleStr = typedArray.getString(R.styleable.ItemTextArrowView_itemTitle);
        String contentStr = typedArray.getString(R.styleable.ItemTextArrowView_itemContent);
        boolean showArrow = typedArray.getBoolean(R.styleable.ItemTextArrowView_showArrow, true);
        int color=typedArray.getColor(R.styleable.ItemTextArrowView_contentColor,contentTV.getCurrentTextColor());
        int itemColor=typedArray.getColor(R.styleable.ItemTextArrowView_itemColor,titleTV.getCurrentTextColor());

        titleTV.setTextColor(itemColor);
        contentTV.setTextColor(color);
        titleTV.setText(titleStr);
        contentTV.setText(contentStr);
        if (showArrow) {
            arrowImage.setVisibility(VISIBLE);
        } else {
            arrowImage.setVisibility(GONE);
        }
        typedArray.recycle();
    }

    public void setTitle(String title) {
        titleTV.setText(title);
    }

    public void setContent(String itemContent) {
        contentTV.setText(itemContent);
    }

    public void setTitleColor(int color){
        titleTV.setTextColor(color);
    }
    public void setContentColor(int color){
        contentTV.setTextColor(color);
    }

    public void showArrow(boolean visible) {
        if (visible) {
            arrowImage.setVisibility(VISIBLE);
        } else {
            arrowImage.setVisibility(GONE);
        }
    }

    @BindingAdapter("itemContent")
    public static void setItemContent(ItemTextArrowView view,String itemContent){
        view.setContent(itemContent);
    }

}
