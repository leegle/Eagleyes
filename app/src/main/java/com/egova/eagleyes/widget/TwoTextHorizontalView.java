package com.egova.eagleyes.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.egova.eagleyes.R;

import androidx.annotation.Nullable;

public class TwoTextHorizontalView extends LinearLayout {
    private TextView titleTV, valueTV;

    public TwoTextHorizontalView(Context context) {
        this(context, null);
    }

    public TwoTextHorizontalView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TwoTextHorizontalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.item_two_horizontal_text, this, true);
        titleTV = findViewById(R.id.title);
        valueTV = findViewById(R.id.value);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TwoTextView);
        String titleStr = typedArray.getString(R.styleable.TwoTextView_title);
        String valueStr = typedArray.getString(R.styleable.TwoTextView_value);
        int titleColor = typedArray.getColor(R.styleable.TwoTextView_titleTextColor, titleTV.getCurrentTextColor());
        int valueColor = typedArray.getColor(R.styleable.TwoTextView_valueTextColor, valueTV.getCurrentTextColor());

        titleTV.setText(titleStr);
        valueTV.setText(valueStr);
        titleTV.setTextColor(titleColor);
        valueTV.setTextColor(valueColor);
        typedArray.recycle();
    }

    public void setTitle(String title) {
        titleTV.setText(title);
    }

    public void setValue(String itemContent) {
        valueTV.setText(itemContent);
    }
}
