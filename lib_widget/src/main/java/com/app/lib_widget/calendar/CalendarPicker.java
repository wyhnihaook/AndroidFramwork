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
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.recyclerview.widget.RecyclerView;

import com.app.lib_widget.R;
import com.app.lib_widget.calendar.adapter.CalendarAdapter;
import com.app.lib_widget.calendar.protocol.OnCalendarSelectListener;
import com.app.lib_widget.calendar.utils.DateUtils;
import com.app.lib_widget.calendar.view.CalendarView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日历日期选择器，基于`https://github.com/oxsource/calendar`
 *
 * @author 贵州山野羡民（1032694760@qq.com）
 * @since 2019/4/30 13:36
 */
@SuppressWarnings("unused")
public class CalendarPicker extends ConfirmPicker implements OnCalendarSelectListener {
    private CalendarView calendarView;
    private FrameLayout bottomView;
    private CalendarAdapter calendarAdapter;
    private boolean rangePick = true;
    private Date minDate, maxDate;
    private Date selectDate, startDate, endDate;
    private String noteFrom, noteTo;
    private OnSingleDatePickListener onSingleDatePickListener;
    private OnRangeDatePickListener onRangeDatePickListener;
    private boolean initialized = false;

    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    public CalendarPicker(Activity activity) {
        super(activity);
    }

    public CalendarPicker(@NonNull Activity activity, @StyleRes int themeResId) {
        super(activity, themeResId);
    }

    @NonNull
    @Override
    protected View createBodyView(@NonNull Activity activity) {
        View view = View.inflate(activity, R.layout.calendar_picker, null);
        calendarView = view.findViewById(R.id.calendar_picker_body);
        bottomView = view.findViewById(R.id.calendar_picker_bottom);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        initialized = true;
        setHeight((int) (calendarView.getResources().getDisplayMetrics().heightPixels * 0.9));
        calendarAdapter = calendarView.getAdapter();
        calendarAdapter.setOnCalendarSelectListener(this);
        refreshData();
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected void onOk() {
        if (!rangePick && selectDate == null) {
            return;
        }
        boolean rangeNotSelected = startDate == null || endDate == null;
        if (rangePick && rangeNotSelected) {
            return;
        }
        dismiss();
        if (onSingleDatePickListener != null) {
            onSingleDatePickListener.onSingleDatePicked(selectDate);
        }
        if (onRangeDatePickListener != null) {
            onRangeDatePickListener.onRangeDatePicked(startDate, endDate);
        }
    }

    @Override
    public void onSingleSelect(@NonNull Date date) {
        //重置起始时间
        selectDate = date;
        //转化时间信息，进行处理
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        String str = format.format(selectDate);
        setBottomStartData(str);
    }

    @Override
    public void onDoubleSelect(@NonNull Date before, @NonNull Date after) {
        //结束时间设置
        startDate = before;
        endDate = after;

        SimpleDateFormat format = new SimpleDateFormat("MM-dd");

        String startStr = format.format(startDate);

        String endStr = format.format(endDate);

        int count = getDiffDay(endDate.getTime(), startDate.getTime());

        setBottomData(startStr, endStr, count);
    }

    public static int getDiffDay(long endDay, long startDay) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = formatter.format(endDay);
        String date2 = formatter.format(startDay);
        // 获取服务器返回的时间戳 转换成"yyyy-MM-dd HH:mm:ss"
        // 计算的时间差
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d1 = df.parse(date1);
            Date d2 = df.parse(date2);
            // 这样得到的差值是微秒级别
            long diff = d1.getTime() - d2.getTime();
            int days = (int) (diff / (1000 * 60 * 60 * 24));

            return days;

        } catch (Exception e) {

        }

        return 0;
    }

    /**
     * 设置日期范围选择回调
     */
    public void setOnRangeDatePickListener(OnRangeDatePickListener onRangeDatePickListener) {
        this.rangePick = true;
        this.onRangeDatePickListener = onRangeDatePickListener;
        if (initialized) {
            refreshData();
        }
    }

    /**
     * 设置单个日期选择回调
     */
    public void setOnSingleDatePickListener(OnSingleDatePickListener onSingleDatePickListener) {
        this.rangePick = false;
        this.onSingleDatePickListener = onSingleDatePickListener;
        if (initialized) {
            refreshData();
        }
    }

    /**
     * 设置日期范围
     */
    public void setRangeDate(Date minDate, Date maxDate) {
        this.minDate = DateUtils.min(minDate, maxDate);
        this.maxDate = DateUtils.max(minDate, maxDate);
        if (initialized) {
            refreshData();
        }
    }

    /**
     * 设置日期范围为当前年月之后的几个月
     */
    public void setRangeDateOnFuture(int offsetMonth) {
        if (offsetMonth < 0) {
            offsetMonth = 0;
        }
        minDate = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.MONTH, offsetMonth);
        calendar.set(Calendar.DAY_OF_MONTH, DateUtils.maxDaysOfMonth(calendar.getTime()));
        maxDate = calendar.getTime();
        if (initialized) {
            refreshData();
        }
    }

    /**
     * 设置默认选择的日期时间戳（单个日期选择模式）
     */
    public void setSelectedDate(long timeInMillis) {
        setSelectedDate(new Date(timeInMillis));
    }

    /**
     * 设置默认选择的日期（单个日期选择模式）
     */
    public void setSelectedDate(Date date) {
        this.selectDate = date;
        if (initialized) {
            refreshData();
        }
    }

    /**
     * 设置默认选择的日期时间戳（日期范围选择模式）
     */
    public void setSelectedDate(long timeInMillisStart, long timeInMillisEnd) {
        setSelectedDate(new Date(Math.min(timeInMillisStart, timeInMillisEnd)),
                new Date(Math.max(timeInMillisStart, timeInMillisEnd)));
    }

    /**
     * 设置默认选择的日期（日期范围选择模式）
     */
    public void setSelectedDate(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        if (initialized) {
            refreshData();
        }
    }

    /**
     * 设置选择区间提示语
     */
    public void setIntervalNotes(String noteFrom, String noteTo) {
        this.noteFrom = noteFrom;
        this.noteTo = noteTo;
        if (initialized) {
            refreshData();
        }
    }

    private void refreshData() {
        if (!TextUtils.isEmpty(noteFrom) && !TextUtils.isEmpty(noteTo)) {
            calendarAdapter.intervalNotes(noteFrom, noteTo);
        }
        calendarAdapter.single(!rangePick);
        if (!rangePick) {
            startDate = selectDate;
            endDate = selectDate;
        }

        calendarAdapter.setRange(minDate, maxDate, true, false);
        calendarAdapter.valid(minDate, maxDate);
        calendarAdapter.select(startDate, endDate);
        final Calendar startCalendar = DateUtils.calendar(startDate);
        final RecyclerView bodyView = calendarView.getBodyView();
        bodyView.post(new Runnable() {
            @Override
            public void run() {
                //默认选中的信息
                //获取开始的日期

                //解析当前选中的日期，强制性固定
                String date = startCalendar.get(Calendar.YEAR) + "-" + (startCalendar.get(Calendar.MONTH) + 1) + "-" + 15 + " 00:00:00";
                try {
                    Date time = format.parse(date);
                    int position = calendarAdapter.getDatePosition(time);
                    //超过一半选择下一个月份，功能迁移为当前信息开始时间永远为月中进行匹配，保证是这个月开始
//                if (startCalendar.get(Calendar.DAY_OF_MONTH) > 15) {
//                    position++;
//                }
                    bodyView.scrollToPosition(Math.min(position, calendarAdapter.getItemCount() - 1));
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    public final CalendarView getCalendarView() {
        return calendarView;
    }

    public final FrameLayout getBottomView() {
        return bottomView;
    }

    @Override
    protected boolean enableMaskView() {
        return false;
    }
}
