package com.dsw.calendarcomponent;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.dsw.calendar.component.MonthView;
import com.dsw.calendar.entity.CalendarInfo;
import com.dsw.calendar.views.ADCircleCalendarView;
import com.dsw.calendar.views.CirclePointCalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CirclePointCalendarActivity extends Activity {
    private CirclePointCalendarView circleCalendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circlepoint_calendar_view);
        Calendar calendar = Calendar.getInstance();
        int currYear = calendar.get(Calendar.YEAR);
        int currMonth = calendar.get(Calendar.MONTH) + 1;
        List<CalendarInfo> list = new ArrayList<CalendarInfo>();
        list.add(new CalendarInfo(currYear,currMonth,4,"￥120"));
        list.add(new CalendarInfo(currYear,currMonth,6,"￥120"));
        list.add(new CalendarInfo(currYear,currMonth,12,"￥120"));
        list.add(new CalendarInfo(currYear,currMonth,16,"￥120"));
        list.add(new CalendarInfo(currYear,currMonth,28,"￥120"));
        list.add(new CalendarInfo(currYear,currMonth,1,"￥120",1));
        list.add(new CalendarInfo(currYear,currMonth,11,"￥120",1));
        list.add(new CalendarInfo(currYear,currMonth,19,"￥120",2));
        list.add(new CalendarInfo(currYear,currMonth,21,"￥120",1));
        circleCalendarView = (CirclePointCalendarView) findViewById(R.id.circleMonthView);
        circleCalendarView.setCalendarInfos(list);
        circleCalendarView.setDateClick(new MonthView.IDateClick(){

            @Override
            public void onClickOnDate(int year, int month, int day) {
                Toast.makeText(CirclePointCalendarActivity.this,"点击了" +  year + "-" + month + "-" + day,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
