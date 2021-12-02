package com.app.lib_widget.guide;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wengyiheng
 * @date 2021/7/16.
 * description：引导页面
 */
public class GuestCoverView extends View {

    /**
     * 圆形镂空
     */
    public static final int TYPE_CIRCLE=1;

    /**
     * 矩形镂空(正方形/长方形)
     */
    public static final int TYPE_RECT=2;

    /**
     * 椭圆形镂空
     */
    public static final int TYPE_OVAL=3;


    /**
     * 圆角矩形镂空
     */
    public static final int TYPE_ROUND_RECT=4;



    public Paint getPaint() {
        return paint;
    }

    private Paint paint;

    public GuestCoverView(Context context) {
        super(context);
        init(context);
    }

    public GuestCoverView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    protected void init(Context context) {
        setVisibility(VISIBLE);

        paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);


        paint.setColor(Color.parseColor("#CC0C0B09"));//透明度百分之80
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(LAYER_TYPE_SOFTWARE, null);//关闭硬件加速
        }


    }

    public GuestCoverView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);

        Xfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);//从绘制模式图片得知 这种模式会导致挖空的区域消失
        paint.setXfermode(mXfermode);
        for (int i = 0; i < holeInfos.size(); i++) {
            HoleInfo holeInfo = holeInfos.get(i);
            switch (holeInfo.getHoleType()){
                case GuestCoverView.TYPE_CIRCLE:
                    int radius= Math.min(holeInfo.getRect().width()/2,holeInfo.getRect().height()/2);
                    canvas.drawCircle(holeInfo.getRect().centerX(),holeInfo.getRect().centerY(),radius,paint);
                    break;
                case GuestCoverView.TYPE_RECT:
                    canvas.drawRect(holeInfo.getRect(),paint);
                    break;
                case GuestCoverView.TYPE_ROUND_RECT:
                    //绘制圆角矩形，由于当前设置颜色避免当前的颜色重叠处产生色差
                    paint.setColor(Color.parseColor("#000000"));
                    canvas.drawRect(holeInfo.getRect(),paint);
                    int radius2= Math.min(holeInfo.getRect().width()/2,holeInfo.getRect().height()/2);
                    canvas.drawCircle(holeInfo.getRect().left,holeInfo.getRect().centerY(),radius2,paint);

                    break;
                case GuestCoverView.TYPE_OVAL:
                    canvas.drawOval( new RectF(holeInfo.getRect()),paint);
                    break;
            }
        }
        paint.setXfermode(null);
    }
    protected List<HoleInfo> holeInfos = new ArrayList<>();


    public void setHoleInfo(HoleInfo info) {

        holeInfos.add(info);
        postInvalidate();

    }

    /**
     * 一次性添加多个镂空区域图形
     *
     * @param infos
     */
    public void addHoleInfos(List<HoleInfo> infos) {
        this.holeInfos.addAll(infos);
        postInvalidate();

    }

    public void clearGuideDrawInfo() {

        holeInfos.clear();

    }


    public static class HoleInfo{
        private Rect rect;
        private int holeType;

        public HoleInfo(Rect rect , int holeType){
            this.rect=rect;
            this.holeType=holeType;
        }

        public Rect getRect() {
            return rect;
        }

        public void setRect(Rect rect) {
            this.rect = rect;
        }

        public int getHoleType() {
            return holeType;
        }

        public void setHoleType(int holeType) {
            this.holeType = holeType;
        }
    }

}
