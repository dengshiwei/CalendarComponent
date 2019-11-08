# 日历组件
[![Latest Stable Version](https://poser.pugx.org/hanson/foundation-sdk/v/stable)](https://github.com/dengshiwei/CalendarComponent)
[![Total Downloads](https://poser.pugx.org/hanson/foundation-sdk/downloads)](https://github.com/dengshiwei/CalendarComponent)
[![Latest Unstable Version](https://poser.pugx.org/hanson/foundation-sdk/v/unstable)](https://github.com/dengshiwei/CalendarComponent)
[![License](https://poser.pugx.org/hanson/foundation-sdk/license)](https://github.com/dengshiwei/CalendarComponent)
[![Monthly Downloads](https://poser.pugx.org/hanson/foundation-sdk/d/monthly)](https://github.com/dengshiwei/CalendarComponent)
[![Daily Downloads](https://poser.pugx.org/hanson/foundation-sdk/d/daily)](https://github.com/dengshiwei/CalendarComponent)

>日历组件（CalendarComponent）库的来源是[CalendarView](https://github.com/dengshiwei/CalendarView)。该控件的来由在[Android自定义控件之日历控件](http://blog.csdn.net/mr_dsw/article/details/48755993)中有介绍。CalendarView称不上一个组件，仅仅只是一个自定义的View，根据当时的项目定制需求进行开发，由于在平时也遇到了多种的日历样式，所以萌生了对日历控件进行重构的项目，所以诞生了这个库。

## 版本迭代
##### 1.0.0 release
* 支持月份日期界面的样式自定制。
* 支持星期的界面样式定制。
* 支持日历事务的显示。
* 支持班、休标志的绘制。
* 支持左右滑动进行月份的切换。

##### 1.0.1 release
* 新增CircleCalendarView圆形日历样式。

##### 1.0.2 release
* 新增ADCircleCalendarView日历样式。
* 新增平滑模式

##### 1.0.3 release
* 修复滑动时左右两边月份显示事务的bug。
* 修复月末滑动处理显示的bug。

## 项目集成
##### 方式一、Gradle直接引用项目library类库。

	compile 'com.dsw.calendar:library:1.0.0'

##### 方式二、Maven引用方式

	<dependency>
      <groupId>com.dsw.calendar</groupId>
      <artifactId>library</artifactId>
      <version>1.0.0</version>
      <type>pom</type>
    </dependency>

## 简单使用

#### 样式设置
日历界面的样式继承IDayTheme，通过实现IDayTheme中的方法来实现日历控件样式的更改。
* colorSelectBG：选中日期的背景色
* colorSelectDay：选中日期的颜色
* colorToday：今天日期颜色
* colorMonthView：日历的整个背景色
* colorWeekday：工作日的颜色
* colorWeekend：周末的颜色
* colorDecor：事务装饰颜色
* colorRest：假日颜色
* colorWork：班颜色
* colorDesc：描述文字颜色
* sizeDay：日期大小
* sizeDesc：描述文字大小
* sizeDecor：装饰器大小
* dateHeight：日期高度
* colorLine：线条颜色
* smoothMode：滑动模式，0为有滑动模式，1没有滑动效果。

周的样式设置通过继承实现IWeekTheme。
* colorTopLinen：顶部线颜色
* colorBottomLine：底部颜色
* colorWeekday：工作日颜色
* colorWeekend：周末颜色
* colorWeekView：星期的整个背景色
* sizeLine:线的宽度
* sizeText:字体大小

使用自定义样式就需要分别继承实现日期和周的样式，然后通过方法进行设置。
	//设置日期日历界面的样式
    calendarView.setDayTheme(new IDayTheme());
    //设置星期的界面样式
    calendarView.setWeekTheme(new IWeekTheme() {);

##### 一、GridCalendarView，格网日历控件
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

滑动模式一：

![gridcalendarview](https://github.com/dengshiwei/CalendarComponent/blob/master/GridCalendarView.gif?raw=true)

滑动模式二：

![gridcalendarview_mode](https://github.com/dengshiwei/CalendarComponent/blob/master/gif/GridCalendarView_Mode1.gif?raw=true)

##### 二、CircleCalendarView圆形日历控件
使用方法同GridCalendarView，包括控件的回调函数、设置事务数据，都是一样的回调接口。该日历控件样式是圆形日历样式。

滑动模式一：

![CircleCalendarView](https://github.com/dengshiwei/CalendarComponent/blob/master/CircleCalendarView.gif?raw=true)

滑动模式二：

![circlecalendarview_mode](https://github.com/dengshiwei/CalendarComponent/blob/master/gif/CircleCalendarView_Mode1.gif?raw=true)

##### 三、ADCircleCalendarView圆形日历控件
使用方法同GridCalendarView，包括控件的回调函数、设置事务数据，都是一样的回调接口。该日历控件样式是圆形日历样式，类似于华为p6的系统日历。

滑动模式一：

![ADCircleCalendarView](https://github.com/dengshiwei/CalendarComponent/blob/master/ADCircleCalendarView.gif?raw=true)

滑动模式二：

![adcirclecalendarview_mode](https://github.com/dengshiwei/CalendarComponent/blob/master/gif/ADCircleCalendarView_Mode1.gif?raw=true)

##### 四、ADCircleCalendarView圆形日历控件
![circlePointView](https://github.com/dengshiwei/CalendarComponent/blob/master/circleview.gif)

## 高级进阶
该项目的核心是MonthView基类的实现，该基类中实现了多数的业务数据处理以及滑动效果的开发。如果你想使用定制一些日历效果，你可以继承MonthView，然后重写它的相关方法。

* drawLines(Canvas canvas,int rowsCount);绘制格网线
* drawBG(Canvas canvas,int column,int row,int day);绘制选中背景色
* drawDecor(Canvas canvas,int column,int row,int day);绘制事务标识符号
* drawRest(Canvas canvas,int column,int row,int day);绘制‘班’、‘休’
* drawText(Canvas canvas,int column,int row,int day);绘制日期

在开发设计时，我提取了这四部分的业务逻辑。你可以重写这四个方法进行定制，实现个性化效果。参数说明：

* Canvas canvas：绘制的画布；
* int rowsCount：当前年月日历绘制需要的行数
* int column：列号，以0开始；
* int row：行号，以0开始
* int day：需要绘制的日期号，从月的1号开始

完成主要的日期绘制，就完成了大部分的日历绘制，然后你可自行使用实现星期-——星期日的绘制，比如你使用TextView结合LinearLayout实现。在这里我通过WeekView进行实现，你可以通过实现IWeekTheme来实现它的样式调整，最后通过组合实现效果。

基本的思路就是这样，欢迎大家给予意见。
