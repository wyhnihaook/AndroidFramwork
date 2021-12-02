package com.app.lib_widget.line;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.app.lib_widget.R;

/**
 * @author wengyiheng
 * @date 2021/9/17.
 * description：阴影分割线
 */
public class ShadowView extends View {

    private Drawable mDrawable;

    private boolean asc;

    private int startColor;

    private int middleColor;

    private int endColor;

    public ShadowView(Context context) {
        super(context,null);
    }

    public ShadowView(Context context, AttributeSet attrs) {
        super(context, attrs,0);

        initialize(context, attrs);

    }

    public ShadowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initialize(context, attrs);
    }


    private void initialize(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ShadowView);

        asc = a.getBoolean(R.styleable.ShadowView_asc, true);

        startColor = a.getColor(R.styleable.ShadowView_startColor, 0x80000000);
        middleColor = a.getColor(R.styleable.ShadowView_middleColor, 0x40000000);
        endColor = a.getColor(R.styleable.ShadowView_endColor, 0x00000000);

        a.recycle();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDrawable == null) {

            int colors[] = {startColor, middleColor, endColor};//分别为开始颜色，中间颜色，结束颜色
            mDrawable = new GradientDrawable(asc?GradientDrawable.Orientation.BOTTOM_TOP:GradientDrawable.Orientation.TOP_BOTTOM, colors);
        }
        mDrawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
        mDrawable.draw(canvas);
    }
}
