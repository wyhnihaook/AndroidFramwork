/*
 * Copyright (c) 2016-present 贵州纳雍穿青人李裕江<1032694760@qq.com>
 *
 * The software is licensed under the Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *     http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR
 * PURPOSE.
 * See the Mulan PSL v2 for more details.
 */

package com.app.lib_widget.calendar;

import android.app.Activity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.CallSuper;
import androidx.annotation.Dimension;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;

import com.app.lib_widget.R;

/**
 * @author 贵州山野羡民（1032694760@qq.com）
 * @since 2021/6/3 15:23
 */
@SuppressWarnings("unused")
public abstract class ConfirmPicker extends BottomDialog implements View.OnClickListener {
    protected View headerView;
    protected View bottomView;
    protected TextView titleView;
    protected ImageView cancelView;
    protected View topLineView;
    protected View bodyView;
    protected View footerView;

    public ConfirmPicker(@NonNull Activity activity) {
        super(activity);
    }

    public ConfirmPicker(@NonNull Activity activity, @StyleRes int themeResId) {
        super(activity, themeResId);
    }

    @NonNull
    @Override
    protected View createContentView(@NonNull Activity activity) {
        LinearLayout rootLayout = new LinearLayout(activity);
        rootLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setGravity(Gravity.CENTER);
        rootLayout.setPadding(0, 0, 0, 0);
        rootLayout.setBackgroundColor(0xFFFFFFFF);
        headerView = createHeaderView(activity);
        if (headerView != null) {
            rootLayout.addView(headerView);
        }


//        topLineView = createTopLineView(activity);
//        if (topLineView != null) {
//            rootLayout.addView(topLineView);
//        }
        bodyView = createBodyView(activity);
        rootLayout.addView(bodyView, new LinearLayout.LayoutParams(MATCH_PARENT, 0, 1.0f));
        footerView = createFooterView(activity);
        if (footerView != null) {
            rootLayout.addView(footerView);
        }

        bottomView = createBottomView(activity);
        if (bottomView != null) {
            //初始化信息，传递参数做初始化
            initBottomView(bottomView);
            rootLayout.addView(bottomView);
        }

        return rootLayout;
    }

    /**
     * 添加底部展示信息
     * */
    private TextView confirm,allDayCount,inDay,outDay;
    protected View createBottomView(@NonNull Activity activity) {
        return View.inflate(activity, R.layout.confirm_picker_bottom, null);
    }

    protected void initBottomView(View view){
        allDayCount=view.findViewById(R.id.all_day_count);
        inDay=view.findViewById(R.id.in_day);
        outDay=view.findViewById(R.id.out_day);
        confirm=view.findViewById(R.id.confirm);

        confirm.setOnClickListener(this);
    }

    /**
     * 设置底部展示信息
     * @param inDayData 入住时间
     * @param outDayData 离店时间
     * @param allDayCountData 时间差
     * */

    public void setBottomData(String inDayData, String outDayData, int allDayCountData){

        this.inDay.setText("入住："+(TextUtils.isEmpty(inDayData)?"未选择":"")+inDayData);

        this.outDay.setText("离店："+(TextUtils.isEmpty(outDayData)?"未选择":"")+outDayData);

        this.allDayCount.setText("共 "+(allDayCountData==0?"-":allDayCountData)+" 晚");

        confirm.setEnabled(allDayCountData>0);
    }

    public void setBottomStartData(String inDayData){
        this.inDay.setText("入住："+(TextUtils.isEmpty(inDayData)?"未选择":"")+inDayData);

        this.outDay.setText("离店："+"未选择");

        this.allDayCount.setText("共 "+"-"+" 晚");

        confirm.setEnabled(false);
    }


    @Nullable
    protected View createHeaderView(@NonNull Activity activity) {
        return View.inflate(activity, R.layout.confirm_picker_header, null);
    }

    @Nullable
    protected View createTopLineView(@NonNull Activity activity) {
        View view = new View(activity);
        view.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, 1));
        view.setBackgroundColor(0xFFCCCCCC);
        return view;
    }

    @NonNull
    protected abstract View createBodyView(@NonNull Activity activity);

    @Nullable
    protected View createFooterView(@NonNull Activity activity) {
        return null;
    }

    @CallSuper
    @Override
    protected void initView(@NonNull View contentView) {
        super.initView(contentView);
        titleView = contentView.findViewById(R.id.confirm_picker_title);
        cancelView = contentView.findViewById(R.id.cancel_action);
    }

    @CallSuper
    @Override
    protected void initData() {
        super.initData();
        cancelView.setOnClickListener(this);
    }

    @CallSuper
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.cancel_action) {
            PickerLog.print("cancel clicked");
            onCancel();
            dismiss();
        } else
            if (id == R.id.confirm) {
            PickerLog.print("ok clicked");
            onOk();
            dismiss();
        }
    }

    protected abstract void onCancel();

    protected abstract void onOk();

    public final void setBodyWidth(@Dimension(unit = Dimension.DP) @IntRange(from = 50) int bodyWidth) {
        ViewGroup.LayoutParams layoutParams = bodyView.getLayoutParams();
        int width = WRAP_CONTENT;
        if (bodyWidth != WRAP_CONTENT && bodyWidth != MATCH_PARENT) {
            width = (int) (bodyView.getResources().getDisplayMetrics().density * bodyWidth);
        }
        layoutParams.width = width;
        bodyView.setLayoutParams(layoutParams);
    }

    public final void setBodyHeight(@Dimension(unit = Dimension.DP) @IntRange(from = 50) int bodyHeight) {
        ViewGroup.LayoutParams layoutParams = bodyView.getLayoutParams();
        int height = WRAP_CONTENT;
        if (bodyHeight != WRAP_CONTENT && bodyHeight != MATCH_PARENT) {
            height = (int) (bodyView.getResources().getDisplayMetrics().density * bodyHeight);
        }
        layoutParams.height = height;
        bodyView.setLayoutParams(layoutParams);
    }

    public final View getHeaderView() {
        return headerView;
    }

    public final View getTopLineView() {
        return topLineView;
    }

    public final View getBodyView() {
        return bodyView;
    }

    public final View getFooterView() {
        return footerView;
    }

    public final TextView getTitleView() {
        return titleView;
    }


}
