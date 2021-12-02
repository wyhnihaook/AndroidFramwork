package me.goldze.mvvmhabit.helper.circularprogress;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import me.goldze.mvvmhabit.R;


/**
 * 正在加载中
 */
public class ModulePublicCircularProgressBar extends ProgressBar {

    public ModulePublicCircularProgressBar(Context context) {
        super(context, null);
    }

    public ModulePublicCircularProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs, R.attr.module_public_cpbStyle);
        initMeasure(context, attrs, R.attr.module_public_cpbStyle);
    }

    public ModulePublicCircularProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initMeasure(context, attrs, defStyle);

    }

    private void initMeasure(Context context, AttributeSet attrs, int defStyle){
        if (isInEditMode()) {
            setIndeterminateDrawable(new CircularProgressDrawable.Builder(context, true).build());
            return;
        }

        Resources res = context.getResources();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ModulePublicCircularProgressBar, defStyle, 0);


        final int color = a.getColor(R.styleable.ModulePublicCircularProgressBar_module_public_cpb_color, res.getColor(R.color.module_public_cpb_default_color));
        final float strokeWidth = a.getDimension(R.styleable.ModulePublicCircularProgressBar_module_public_cpb_stroke_width, res.getDimension(R.dimen.module_public_cpb_default_stroke_width));
        final float sweepSpeed = a.getFloat(R.styleable.ModulePublicCircularProgressBar_module_public_cpb_sweep_speed, Float.parseFloat(res.getString(R.string.module_public_cpb_default_sweep_speed)));
        final float rotationSpeed = a.getFloat(R.styleable.ModulePublicCircularProgressBar_module_public_cpb_rotation_speed, Float.parseFloat(res.getString(R.string.module_public_cpb_default_rotation_speed)));
        final int colorsId = a.getResourceId(R.styleable.ModulePublicCircularProgressBar_module_public_cpb_colors, 0);
        final int minSweepAngle = a.getInteger(R.styleable.ModulePublicCircularProgressBar_module_public_cpb_min_sweep_angle, res.getInteger(R.integer.module_public_cpb_default_min_sweep_angle));
        final int maxSweepAngle = a.getInteger(R.styleable.ModulePublicCircularProgressBar_module_public_cpb_max_sweep_angle, res.getInteger(R.integer.module_public_cpb_default_max_sweep_angle));
        a.recycle();

        int[] colors = null;
        //colors
        if (colorsId != 0) {
            colors = res.getIntArray(colorsId);
        }

        Drawable indeterminateDrawable;
        CircularProgressDrawable.Builder builder = new CircularProgressDrawable.Builder(context)
                .sweepSpeed(sweepSpeed)
                .rotationSpeed(rotationSpeed)
                .strokeWidth(strokeWidth)
                .minSweepAngle(minSweepAngle)
                .maxSweepAngle(maxSweepAngle);

        if (colors != null && colors.length > 0)
            builder.colors(colors);
        else
            builder.color(color);

        indeterminateDrawable = builder.build();
        setIndeterminateDrawable(indeterminateDrawable);
    }

    private CircularProgressDrawable checkIndeterminateDrawable() {
        Drawable ret = getIndeterminateDrawable();
        if (ret == null || !(ret instanceof CircularProgressDrawable))
            throw new RuntimeException("The drawable is not a CircularProgressDrawable");
        return (CircularProgressDrawable) ret;
    }

    public void progressiveStop() {
        checkIndeterminateDrawable().progressiveStop();
    }

    public void progressiveStop(CircularProgressDrawable.OnEndListener listener) {
        checkIndeterminateDrawable().progressiveStop(listener);
    }
}
