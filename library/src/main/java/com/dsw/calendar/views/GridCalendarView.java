package com.dsw.calendar.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dsw.calendar.component.GridMonthView;
import com.dsw.calendar.component.MonthView;
import com.dsw.calendar.component.WeekView;
import com.dsw.calendar.entity.CalendarInfo;
import com.dsw.calendar.theme.IDayTheme;
import com.dsw.calendar.theme.IWeekTheme;

import java.util.List;

/**
 * Created by Administrator on 2016/7/31.
 */
public class GridCalendarView extends LinearLayout implements View.OnClickListener {
    private WeekView weekView;
    private GridMonthView gridMonthView;
    private TextView textViewYear,textViewMonth;
    public GridCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
        LayoutParams llParams =
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(context).inflate(com.dsw.calendar.R.layout.display_grid_date,null);
        weekView = new WeekView(context,null);
        gridMonthView = new GridMonthView(context,null);
        addView(view,llParams);
        addView(weekView,llParams);
        addView(gridMonthView,llParams);

        view.findViewById(com.dsw.calendar.R.id.left).setOnClickListener(this);
        view.findViewById(com.dsw.calendar.R.id.right).setOnClickListener(this);
        textViewYear = (TextView) view.findViewById(com.dsw.calendar.R.id.year);
        textViewMonth = (TextView) view.findViewById(com.dsw.calendar.R.id.month);
        gridMonthView.setMonthLisener(new MonthView.IMonthLisener() {
            @Override
            public void setTextMonth() {
                textViewYear.setText(gridMonthView.getSelYear()+"年");
                textViewMonth.setText((gridMonthView.getSelMonth() + 1)+"月");
            }
        });
    }

    /**
     * 设置日历点击事件
     * @param dateClick
     */
    public void setDateClick(MonthView.IDateClick dateClick){
        gridMonthView.setDateClick(dateClick);
    }

    /**
     * 设置星期的形式
     * @param weekString
     * 默认值	"日","一","二","三","四","五","六"
     */
    public void setWeekString(String[] weekString){
        weekView.setWeekString(weekString);
    }

    public void setCalendarInfos(List<CalendarInfo> calendarInfos){
        gridMonthView.setCalendarInfos(calendarInfos);
        textViewYear.setText(gridMonthView.getSelYear()+"年");
        textViewMonth.setText((gridMonthView.getSelMonth() + 1)+"月");
    }

    public void setDayTheme(IDayTheme theme){
        gridMonthView.setTheme(theme);
    }

    public void setWeekTheme(IWeekTheme weekTheme){
        weekView.setWeekTheme(weekTheme);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == com.dsw.calendar.R.id.left){
            gridMonthView.onLeftClick();
        }else{
            gridMonthView.onRightClick();
        }
    }
}