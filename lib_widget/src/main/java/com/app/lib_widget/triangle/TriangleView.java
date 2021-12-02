package com.app.lib_widget.triangle;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;

import com.app.lib_widget.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author wengyiheng
 * @date 2021/1/26.
 * description：三角形
 */
public class TriangleView extends View {

    private Paint paint;
    private Path path;
    private int color;
    private int mode;
    private boolean fill;
    private boolean complete;

    private final int DEFAULT_WIDTH=48;
    private final int DEFAULT_HEIGHT=24;

    private int width = 0;
    private int height =0;

    /**
     * 倒三角
     */
    public static final int INVERTED = 0;
    /**
     * 正三角
     */
    public static final int REGULAR = 1;

    @IntDef({INVERTED, REGULAR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ShapeMode {}

    public TriangleView(Context context) {
        this(context,null);
    }

    public TriangleView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public TriangleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TriangleView);
        color = typedArray.getColor(R.styleable.TriangleView_tlv_color, Color.BLACK);
        mode = typedArray.getInt(R.styleable.TriangleView_tlv_mode, INVERTED);

        fill = typedArray.getBoolean(R.styleable.TriangleView_tlv_fill, true);
        complete = typedArray.getBoolean(R.styleable.TriangleView_tlv_complete, true);

        typedArray.recycle();

        paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        if(!fill){
            paint.setStyle(Paint.Style.STROKE);
        }else{
            paint.setStyle(Paint.Style.FILL);
        }


        path= new Path();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = measureSize(widthMeasureSpec, DEFAULT_WIDTH);
        height = measureSize(heightMeasureSpec, DEFAULT_HEIGHT);
        setMeasuredDimension(width, height);
    }

    private int measureSize(int measureSpec, int defaultSize) {
        int newSize = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.AT_MOST:
                newSize = Math.min(size, defaultSize);
                break;
            case MeasureSpec.EXACTLY:
                newSize = size;
                break;
            case MeasureSpec.UNSPECIFIED:
                newSize = defaultSize;
                break;
        }
        return newSize;
    }

    public void setColor(int color){
        this.color=color;
        paint.setColor(color);
        invalidate();
    }

    public void setMode(@ShapeMode int mode){
        this.mode=mode;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTriangle(canvas);

    }

    private void drawTriangle(Canvas canvas) {
        if(mode==INVERTED) {
            path.moveTo(width , 0);
            path.lineTo(width / 2.0f, height);
            path.lineTo(0, 0);

        }
        else {
            path.moveTo(width , height);
            path.lineTo(width / 2.0f, 0);
            path.lineTo(0, height);
        }

        if(complete){
            path.close();
        }

        canvas.drawPath(path, paint);
    }
}
