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

package com.app.lib_widget.calendar.view;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.lib_widget.R;
import com.app.lib_widget.calendar.adapter.CalendarAdapter;
import com.app.lib_widget.calendar.adapter.CalendarHeaderAdapter;
import com.app.lib_widget.calendar.utils.TimeUtils;
import com.app.lib_widget.calendar.view.decoration.GroupListener;
import com.app.lib_widget.calendar.view.decoration.StickyDecoration;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日历控件
 * Created by peng on 2017/8/3.
 */
public class CalendarView extends LinearLayout {
    private final CalendarAdapter calendarAdapter = new CalendarAdapter();
    private final RecyclerView weekView;
    private final RecyclerView bodyView;
    private final LinearLayoutManager mLayoutManager;

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        inflate(context, R.layout.calendar_body, this);
        weekView = findViewById(R.id.calendar_body_week);
        //初始化星期标头
        initWeekGridView(context);
        //月份列表
        bodyView = findViewById(R.id.calendar_body_content);
        mLayoutManager = new LinearLayoutManager(context);
        bodyView.setLayoutManager(mLayoutManager);
        bodyView.setAdapter(getAdapter());
        initDecoration(bodyView);
    }

    private void initWeekGridView(Context context) {
        String[] from = new String[]{"week"};
        int[] to = new int[]{R.id.calendar_week_item_day};
        String[] strings = new String[]{
                "日", "一", "二", "三", "四", "五", "六"
        };
        List<Map<String, String>> weeks = new ArrayList<>();
        for (String string : strings) {
            Map<String, String> map = new HashMap<>();
            map.put(from[0], string);
            weeks.add(map);
        }
        CalendarHeaderAdapter adapter = new CalendarHeaderAdapter(strings);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(context,strings.length);
        weekView.setLayoutManager(mGridLayoutManager);
        weekView.setAdapter(adapter);

    }


    public final RecyclerView getBodyView() {
        return bodyView;
    }

    public final LinearLayoutManager getLayoutManager() {
        return mLayoutManager;
    }

    public final CalendarAdapter getAdapter() {
        return calendarAdapter;
    }

    public void initDecoration(RecyclerView bodyView) {
        GroupListener groupListener = new GroupListener() {
            @Override
            public String getGroupName(int position) {
                Date date = getAdapter().value(position);
                return TimeUtils.dateText(date.getTime(), TimeUtils.YY_M_CN);
            }
        };
        StickyDecoration decoration = StickyDecoration.Builder
                .init(groupListener)
                .setGroupBackground(ContextCompat.getColor(getContext(), R.color
                        .calendar_background_decoration_color2))     //背景色
                .setGroupHeight((int) getResources().getDimension(R.dimen.calendar_decoration_height))     //高度
                .setDivideColor(ContextCompat.getColor(getContext(), R.color.transport))           //分割线颜色
                .setDivideHeight((int) getResources().getDimension(R.dimen.calendar_decoration_divide_line_height))
                //分割线高度 (默认没有分割线)
                .setGroupTextColor(ContextCompat.getColor(getContext(), R.color.white))
                //字体颜色
                .setTypeface(Typeface.defaultFromStyle(Typeface.BOLD)) //加粗
                .setGroupTextSize((int) getResources().getDimension(R.dimen.calendar_decoration_text_size2))   //字体大小
                .setTextSideMargin((int) ((float) 10 * getResources().getDisplayMetrics().density + 0.5f))  //边距   靠左时为左边距  靠右时为右边距
                .setTextAlign(Paint.Align.LEFT)                      //居中显示
                .build();
        bodyView.addItemDecoration(decoration);
    }

}