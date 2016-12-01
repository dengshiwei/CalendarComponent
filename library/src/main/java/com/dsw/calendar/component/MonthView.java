package com.dsw.calendar.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import com.dsw.calendar.entity.CalendarInfo;
import com.dsw.calendar.theme.IDayTheme;
import com.dsw.calendar.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2016/7/30.
 */
public abstract class MonthView extends View {
    protected int NUM_COLUMNS = 7;
    protected int NUM_ROWS = 6;
    protected Paint paint;
    protected IDayTheme theme;
    private  IMonthLisener monthLisener;
    private IDateClick dateClick;
    protected int currYear,currMonth,currDay;
    protected int selYear,selMonth,selDay;
    private int leftYear,leftMonth,leftDay;
    private int rightYear,rightMonth,rightDay;
    protected int [][] daysString;
    protected float columnSize,rowSize,baseRowSize;
    private int mTouchSlop;
    protected float density;
    private int indexMonth;
    private int width;
    protected List<CalendarInfo> calendarInfos = new ArrayList<CalendarInfo>();
    private int downX = 0,downY = 0;
    private Scroller mScroller;
    private int smoothMode;
    public MonthView(Context context, AttributeSet attrs) {
        super(context, attrs);
        density = getResources().getDisplayMetrics().density;
        mScroller = new Scroller(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        Calendar calendar = Calendar.getInstance();
        currYear = calendar.get(Calendar.YEAR);
        currMonth = calendar.get(Calendar.MONTH);
        currDay = calendar.get(Calendar.DATE);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setSelectDate(currYear,currMonth,currDay);
        setLeftDate();
        setRightDate();
        createTheme();
        baseRowSize = rowSize = theme == null ? 70 : theme.dateHeight();
        smoothMode = theme == null ? 0 : theme.smoothMode();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if(widthMode == MeasureSpec.AT_MOST){
            widthSize = (int) (300 * density);
        }
        width = widthSize;
        NUM_ROWS = 6; //本来是想根据每月的行数，动态改变控件高度，现在为了使滑动的左右两边效果相同，不适用getMonthRowNumber();
        int heightSize = (int) (NUM_ROWS * baseRowSize);
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(theme.colorMonthView());
        if(smoothMode == 1){
            drawDate(canvas,selYear,selMonth,indexMonth * width,0);
            return;
        }
        //绘制上一月份
        drawDate(canvas,leftYear,leftMonth,(indexMonth - 1)* width,0);
        //绘制下一月份
        drawDate(canvas,rightYear,rightMonth,(indexMonth + 1)* width,0);
        //绘制当前月份
        drawDate(canvas,selYear,selMonth,indexMonth * width,0);
    }

    private void drawDate(Canvas canvas,int year,int month,int startX,int startY){
        canvas.save();
        canvas.translate(startX,startY);
        NUM_ROWS =  getMonthRowNumber(year,month);
        columnSize = getWidth() *1.0F/ NUM_COLUMNS;
        rowSize = getHeight() * 1.0F / NUM_ROWS;
        daysString = new int[6][7];
        int mMonthDays = DateUtils.getMonthDays(year, month);
        int weekNumber = DateUtils.getFirstDayWeek(year, month);
        int column,row;
        drawLines(canvas,NUM_ROWS);
        for(int day = 0;day < mMonthDays;day++){
            column = (day+weekNumber - 1) % 7;
            row = (day+weekNumber - 1) / 7;
            daysString[row][column]=day + 1;
            drawBG(canvas,column,row,daysString[row][column]);
            drawDecor(canvas,column,row,year,month,daysString[row][column]);
            drawRest(canvas,column,row,year,month,daysString[row][column]);
            drawText(canvas,column,row,year,month,daysString[row][column]);
        }
        canvas.restore();
    }
    /**
     * 回执格网线
     * @param canvas
     */
    protected abstract void drawLines(Canvas canvas,int rowsCount);

    protected abstract void drawBG(Canvas canvas,int column,int row,int day);

    protected abstract void drawDecor(Canvas canvas,int column,int row,int year,int month,int day);

    protected abstract void drawRest(Canvas canvas,int column,int row,int year,int month,int day);

    protected abstract void drawText(Canvas canvas,int column,int row,int year,int month,int day);

    /**
     * 实例化Theme
     */
    protected abstract void createTheme();
    private int lastMoveX;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventCode=  event.getAction();
        switch(eventCode){
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                downY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if(smoothMode == 1)break;
                int dx = (int) (downX - event.getX());
                if(Math.abs(dx) > mTouchSlop){
                    int moveX = dx + lastMoveX;
                    smoothScrollTo(moveX, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                int upX = (int) event.getX();
                int upY = (int) event.getY();
                if(upX-downX > 0 && Math.abs(upX-downX) > mTouchSlop*10){//左滑
                    if(smoothMode == 0){
                        setLeftDate();
                        indexMonth--;
                    }else{
                        onLeftClick();
                    }
                }else if(upX-downX < 0 && Math.abs(upX-downX) > mTouchSlop*10){//右滑
                    if(smoothMode == 0){
                        setRightDate();
                        indexMonth++;
                    }else{
                        onRightClick();
                    }
                }else if(Math.abs(upX-downX) < 10 && Math.abs(upY - downY) < 10){//点击事件
                    performClick();
                    doClickAction((upX + downX)/2,(upY + downY)/2);
                }
                if(smoothMode == 0) {
                    lastMoveX = indexMonth * width;
                    smoothScrollTo(width * indexMonth, 0);
                }
                break;
        }
        return true;
    }

    //调用此方法滚动到目标位置
    public void smoothScrollTo(int fx, int fy) {
        int dx = fx - mScroller.getFinalX();
        int dy = fy - mScroller.getFinalY();
        smoothScrollBy(dx, dy);
    }

    //调用此方法设置滚动的相对偏移
    public void smoothScrollBy(int dx, int dy) {
        //设置mScroller的滚动偏移量
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy,500);
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    /**
     * 设置选中的月份
     * @param year
     * @param month
     */
    protected void setSelectDate(int year,int month,int day){
        selYear = year;
        selMonth = month;
        selDay = day;
    }

    protected int getMonthRowNumber(int year,int month){
        int monthDays = DateUtils.getMonthDays(year, month);
        int weekNumber = DateUtils.getFirstDayWeek(year, month);
        return (monthDays + weekNumber - 1) % 7 == 0 ? (monthDays + weekNumber - 1) / 7 : (monthDays + weekNumber - 1) / 7 + 1;
    }

    public void setCalendarInfos(List<CalendarInfo> calendarInfos) {
        this.calendarInfos = calendarInfos;
        invalidate();
    }

    /**
     * 判断是否为事务天数,通过获取desc来辨别
     * @param day
     * @return
     */
    protected String iscalendarInfo(int year,int month,int day){
        if(calendarInfos == null || calendarInfos.size() == 0)return "";
        for(CalendarInfo calendarInfo : calendarInfos){
            if(calendarInfo.day == day && calendarInfo.month == month + 1 && calendarInfo.year == year){
                return calendarInfo.des;
            }
        }
        return "";
    }

    /**
     * 执行点击事件
     * @param x
     * @param y
     */
    private void doClickAction(int x,int y){
        int row = (int) (y / rowSize);
        int column = (int) (x / columnSize);
        setSelectDate(selYear,selMonth,daysString[row][column]);
        invalidate();
        //执行activity发送过来的点击处理事件
        if(dateClick != null){
            dateClick.onClickOnDate(selYear,selMonth + 1,selDay);
        }
    }

    /**
     * 左点击，日历向后翻页
     */
    public void onLeftClick(){
        setLeftDate();
        invalidate();
        if(monthLisener != null){
            monthLisener.setTextMonth();
        }
    }

    /**
     * 右点击，日历向前翻页
     */
    public void onRightClick(){
        setRightDate();
        invalidate();
        if(monthLisener != null){
            monthLisener.setTextMonth();
        }
    }

    private void setLeftDate(){
        int year = selYear;
        int month = selMonth;
        int day = selDay;
        if(month == 0){//若果是1月份，则变成12月份
            year = selYear-1;
            month = 11;
        }else if(DateUtils.getMonthDays(year, month-1) < day){//向左滑动，当前月天数小于左边的
            //如果当前日期为该月最后一点，当向前推的时候，就需要改变选中的日期
            month = month-1;
            day = DateUtils.getMonthDays(year, month);
        }else{
            month = month-1;
        }
        setSelectDate(year,month,day);
        computeDate();
    }

    private void setRightDate(){
        int year = selYear;
        int month = selMonth;
        int day = selDay;
        if(month == 11){//若果是12月份，则变成1月份
            year = selYear + 1;
            month = 0;
        }else if(DateUtils.getMonthDays(year, month + 1) < day){//向右滑动，当前月天数小于左边的
            //如果当前日期为该月最后一点，当向前推的时候，就需要改变选中的日期
            month = month + 1;
            day = DateUtils.getMonthDays(year, month);
        }else{
            month = month + 1;
        }
        setSelectDate(year,month,day);
        computeDate();
    }

    private void computeDate(){
        if(selMonth == 0){
            leftYear = selYear -1;
            leftMonth = 11;
            rightYear = selYear;
            rightMonth = selMonth + 1;
        }else if(selMonth == 11){
            leftYear = selYear;
            leftMonth = selMonth -1;
            rightYear = selYear + 1;
            rightMonth = 0;
        }else{
            leftYear = selYear;
            leftMonth = selMonth - 1;
            rightYear = selYear;
            rightMonth = selMonth + 1;
        }
        if(monthLisener != null){
            monthLisener.setTextMonth();
        }
    }

    public void setDateClick(IDateClick dateClick) {
        this.dateClick = dateClick;
    }

    public interface IDateClick{
        void onClickOnDate(int year,int month,int day);
    }

    public interface IMonthLisener{
        void setTextMonth();
    }

    public void setMonthLisener(IMonthLisener monthLisener) {
        this.monthLisener = monthLisener;
    }

    /**
     * 设置样式
     * @param theme
     */
    public void setTheme(IDayTheme theme) {
        this.theme = theme;
    }

    public int getSelYear() {
        return selYear;
    }

    public int getSelMonth() {
        return selMonth;
    }
}
