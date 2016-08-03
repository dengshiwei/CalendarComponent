#日历组件

>日历组件（CalendarComponent）库的来源是[CalendarView](https://github.com/dengshiwei/CalendarView)。该控件的来由在[Android自定义控件之日历控件](http://blog.csdn.net/mr_dsw/article/details/48755993)中有介绍。CalendarView称不上一个组件，仅仅只是一个自定义的View，根据当时的项目定制需求进行开发，由于在平时也遇到了多种的日历样式，所以萌生了对日历控件进行重构的项目，所以诞生了这个库。

## 版本迭代
#####1.0.0 release
* 支持月份日期界面的样式自定制。
* 支持星期的界面样式定制。
* 支持日历事务的显示。
* 支持班、休标志的绘制。
* 支持左右滑动进行月份的切换。（暂时有问题）

## 项目集成
#####方式一、


## 简单使用
#####一、GridCalendarView，格网日历控件
当在你的项目中引入了CalendarComponent库后，你就可以像使用普通控件一样使用库中的日历控件。GridCalendarView是一个绘制有格网线的日历控件，支持日历的左右滑动以及手动翻页，支持日历的事务的显示、以及用户定制日历的主题样式。具体使用如下：

	 GridCalendarView gridCalendarView = (GridCalendarView) findViewById(R.id.gridMonthView);
     gridCalendarView.setDateClick(new MonthView.IDateClick(){

            @Override
            public void onClickOnDate(int year, int month, int day) {
                Toast.makeText(MainActivity.this,"点击了" +  year + "-" + month + "-" + day,Toast.LENGTH_SHORT).show();
            }
        });

这是GridCalendarView的简单使用，我们可以获取单击选择的日期。同时我们也可以支持事务，事务的处理，我们通过CalendarInfo类进行表示。如下：

    List<CalendarInfo> list = new ArrayList<CalendarInfo>();
    list.add(new CalendarInfo(2016,7,4,"￥1200"));
    list.add(new CalendarInfo(2016,7,6,"￥1200"));
    list.add(new CalendarInfo(2016,7,12,"￥1200"));
    list.add(new CalendarInfo(2016,7,16,"￥1200"));
    list.add(new CalendarInfo(2016,7,28,"￥1200"));
    list.add(new CalendarInfo(2016,7,1,"￥1200",1));
    list.add(new CalendarInfo(2016,7,11,"￥1200",1));
    list.add(new CalendarInfo(2016,7,19,"￥1200",2));
    list.add(new CalendarInfo(2016,7,21,"￥1200",1));
    gridCalendarView.setCalendarInfos(list);
    
CalendarInfo类字段示意：
* year  事务年份
* month 事务月份
* day   事务日期号
* des   事务描述
* rest  是否为休、班。1为休，2为班，默认为普通日期

样式的设置：

    //设置日期日历界面的样式
    gridCalendarView.setDayTheme(new IDayTheme());
    //设置星期的界面样式
    gridCalendarView.setWeekTheme(new IWeekTheme() {);
    
效果图：

![gridcalendarview](https://github.com/dengshiwei/CalendarComponent/blob/master/GridCalendarView.gif?raw=true)