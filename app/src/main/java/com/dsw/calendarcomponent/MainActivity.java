package com.dsw.calendarcomponent;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.dsw.calendar.component.GridMonthView;
import com.dsw.calendar.component.MonthView;
import com.dsw.calendar.entity.CalendarInfo;
import com.dsw.calendar.views.GridCalendarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private GridCalendarView gridCalendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<CalendarInfo> list = new ArrayList<CalendarInfo>();
        list.add(new CalendarInfo(2016,7,4,"￥1200"));
        list.add(new CalendarInfo(2016,7,6,"￥1200"));
        list.add(new CalendarInfo(2016,7,12,"￥1200"));
        list.add(new CalendarInfo(2016,7,16,"￥1200"));list.add(new CalendarInfo(2016,7,28,"￥1200"));
        list.add(new CalendarInfo(2016,7,1,"￥1200",1));
        list.add(new CalendarInfo(2016,7,11,"￥1200",1));
        list.add(new CalendarInfo(2016,7,19,"￥1200",2));
        list.add(new CalendarInfo(2016,7,21,"￥1200",1));
        gridCalendarView = (GridCalendarView) findViewById(R.id.gridMonthView);
        gridCalendarView.setCalendarInfos(list);
        gridCalendarView.setDateClick(new MonthView.IDateClick(){

            @Override
            public void onClickOnDate(int year, int month, int day) {
                Toast.makeText(MainActivity.this,"点击了" +  year + "-" + month + "-" + day,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
