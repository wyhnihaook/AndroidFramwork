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

package com.app.lib_widget.calendar.adapter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.app.lib_widget.calendar.view.MonthView;


/**
 * Created by peng on 2017/8/3.
 */

public class CalendarViewHolder extends RecyclerView.ViewHolder {
    private final MonthView view;

    CalendarViewHolder(View itemView) {
        super(itemView);
        view = (MonthView) itemView;
    }

    public MonthView view() {
        return view;
    }
}
