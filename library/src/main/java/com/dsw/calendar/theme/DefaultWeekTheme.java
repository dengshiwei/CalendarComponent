package com.dsw.calendar.theme;

import android.graphics.Color;

/**
 * Created by Administrator on 2016/7/31.
 */
public class DefaultWeekTheme implements IWeekTheme {
    @Override
    public int colorTopLinen() {
        return Color.parseColor("#CCE4F2");
    }

    @Override
    public int colorBottomLine() {
        return Color.parseColor("#CCE4F2");
    }

    @Override
    public int colorWeekday() {
        return Color.parseColor("#1FC2F3");
    }

    @Override
    public int colorWeekend() {
        return Color.parseColor("#fa4451");
    }

    @Override
    public int colorWeekView() {
        return Color.parseColor("#FEFEFF");
    }

    @Override
    public int sizeLine() {
        return 4;
    }

    @Override
    public int sizeText() {
        return 14;
    }
}
