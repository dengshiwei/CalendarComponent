package com.dsw.calendarcomponent;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.dsw.calendar.component.CircleMonthView;
import com.dsw.calendar.component.MonthView;
import com.dsw.calendar.entity.CalendarInfo;
import com.dsw.calendar.utils.DateUtils;
import com.dsw.calendar.views.CircleCalendarView;
import com.dsw.calendar.views.GridCalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CircleCalendarActivity extends Activity {
    private CircleCalendarView circleCalendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_calendar_view);
        Calendar calendar = Calendar.getInstance();
        int currYear = calendar.get(Calendar.YEAR);
        int currMonth = calendar.get(Calendar.MONTH) + 1;
        List<CalendarInfo> list = new ArrayList<CalendarInfo>();
        list.add(new CalendarInfo(currYear,currMonth,4,"￥1200"));
        list.add(new CalendarInfo(currYear,currMonth,6,"￥1200"));
        list.add(new CalendarInfo(currYear,currMonth,12,"￥1200"));
        list.add(new CalendarInfo(currYear,currMonth,16,"￥1200"));
        list.add(new CalendarInfo(currYear,currMonth,28,"￥1200"));
        list.add(new CalendarInfo(currYear,currMonth,1,"￥1200",1));
        list.add(new CalendarInfo(currYear,currMonth,11,"￥1200",1));
        list.add(new CalendarInfo(currYear,currMonth,19,"￥1200",2));
        list.add(new CalendarInfo(currYear,currMonth,21,"￥1200",1));
        circleCalendarView = (CircleCalendarView) findViewById(R.id.circleMonthView);
        circleCalendarView.setCalendarInfos(list);
        circleCalendarView.setDateClick(new MonthView.IDateClick(){

            @Override
            public void onClickOnDate(int year, int month, int day) {
                Toast.makeText(CircleCalendarActivity.this,"点击了" +  year + "-" + month + "-" + day,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
