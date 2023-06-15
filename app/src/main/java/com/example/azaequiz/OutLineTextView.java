package com.example.azaequiz;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

public class OutLineTextView extends androidx.appcompat.widget.AppCompatTextView {
    private boolean stroke = false;
    private float strokeWidth = 0.0f;
    private int strokeColor;

    public OutLineTextView (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs);
    }

    public OutLineTextView (Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public OutLineTextView (Context context) {
        super(context);
    }

    public void initView (Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.OutLineTextView);
        stroke = a.getBoolean(R.styleable.OutLineTextView_textStroke, false);
        strokeWidth = a.getFloat(R.styleable.OutLineTextView_textStrokeWidth, 0.0f);
        strokeColor = a.getColor(R.styleable.OutLineTextView_textStrokeColor, Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (stroke) {
            ColorStateList states = getTextColors();
            getPaint().setStyle(Paint.Style.STROKE);
            getPaint().setStrokeWidth(strokeWidth);
            setTextColor(strokeColor);
            super.onDraw(canvas);
            getPaint().setStyle(Paint.Style.FILL);
            setTextColor(states);
        }
        super.onDraw(canvas);
    }
}
