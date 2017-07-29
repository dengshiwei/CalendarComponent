package com.dsw.calendarcomponent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dsw.calendar.views.ADCircleCalendarView;
import com.dsw.calendar.views.CirclePointCalendarView;

public class MainActivity extends Activity {
    private ListView listView;
    private String[] items = new String[]{"GridCalendarView","CircleCalendarView","ADCircleCalendarView",
    "CirclePointMonthView"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
                android.R.id.text1,items);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position){
                    case 0:
                        intent = new Intent(MainActivity.this,GridCalendarActivity.class);
                        break;
                    case 1:
                        intent = new Intent(MainActivity.this,CircleCalendarActivity.class);
                        break;
                    case 2:
                        intent = new Intent(MainActivity.this, ADCircleCalendarActivity.class);
                        break;
                    case 3:
                        intent = new Intent(MainActivity.this, CirclePointCalendarActivity.class);
                        break;
                    default:
                        break;
                }
                if(null != intent)
                startActivity(intent);
            }
        });
    }
}
