package com.dsw.calendar.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.dsw.calendar.entity.CalendarInfo;
import com.dsw.calendar.theme.ADCircleDayTheme;

/**
 * Created by Administrator on 2016/8/9.
 */
public class ADCircleMonthView extends MonthView {

    public ADCircleMonthView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void drawLines(Canvas canvas, int rowsCount) {
        int rightX = getWidth();
        Path path;
        float startX = 0;
        float endX = rightX;
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(theme.colorLine());
        for(int row = 1; row <= rowsCount ;row++){
            float startY = row * rowSize;
            path = new Path();
            path.moveTo(startX, startY);
            path.lineTo(endX, startY);
            canvas.drawPath(path, paint);
        }
    }

    @Override
    protected void drawBG(Canvas canvas, int column, int row, int day) {
        float startRecX = columnSize * column + 1;
        float startRecY = rowSize * row +1;
        float endRecX = startRecX + columnSize - 2 * 1;
        float endRecY = startRecY + rowSize - 2 * 1;
        float cx = (startRecX + endRecX) / 2;
        float cy = (startRecY + endRecY) / 2;
        float radius = columnSize < (rowSize * 0.6) ? columnSize / 2 : (float)(rowSize * 0.6) / 2;
        paint.setColor(theme.colorSelectBG());
        if(day == selDay){ //绘制背景色圆形
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(cx,cy,radius,paint);
        }
        if(day== currDay && currDay != selDay && currMonth == selMonth){//今日绘制圆环
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(cx,cy,radius,paint);
        }
    }

    @Override
    protected void drawDecor(Canvas canvas, int column, int row, int year,int month,int day) {
        if(calendarInfos != null && calendarInfos.size() >0){
            if(TextUtils.isEmpty(iscalendarInfo(year,month,day)))return;
            paint.setColor(theme.colorDecor());
            paint.setStyle(Paint.Style.FILL);
            float circleX = (float) (columnSize * column +	columnSize*0.5);
            float circleY = (float) (rowSize * row + rowSize * 0.25);
            if(day == selDay){//选中日期无事务
                circleY = (float) (rowSize * row + rowSize * 0.1);
            }
            canvas.drawCircle(circleX, circleY, theme.sizeDecor(), paint);
        }
    }

    @Override
    protected void drawRest(Canvas canvas, int column, int row, int year,int month,int day) {
        if(calendarInfos != null && calendarInfos.size() > 0){
            float radius = columnSize < (rowSize * 0.6) ? columnSize / 2 : (float)(rowSize * 0.6) / 2;
            for(CalendarInfo calendarInfo : calendarInfos){
                if(calendarInfo.day == day && calendarInfo.year == year && calendarInfo.month == month + 1){
                    float restX = columnSize * column + (columnSize + paint.measureText(day+""))/2;
                    float restY = rowSize * row + rowSize/2 - (paint.ascent() + paint.descent())/2;
                    if(day == selDay){
                        restX = columnSize * column + columnSize/2 + radius;
                    }
                    paint.setStyle(Paint.Style.FILL);
                    if(calendarInfo.rest == 2){//班
                        paint.setColor(theme.colorWork());
                        paint.setTextSize(theme.sizeDesc());
                        paint.measureText("班");
                        canvas.drawText("班", restX, restY, paint);
                    }else if(calendarInfo.rest == 1){//休息
                        paint.setColor(theme.colorRest());
                        paint.setTextSize(theme.sizeDesc());
                        canvas.drawText("休", restX, restY, paint);
                    }
                }
            }
        }
    }

    @Override
    protected void drawText(Canvas canvas, int column, int row, int year,int month,int day) {
        paint.setTextSize(theme.sizeDay());
        float startX = columnSize * column + (columnSize - paint.measureText(day+""))/2;
        float startY = rowSize * row + rowSize/2 - (paint.ascent() + paint.descent())/2;
        paint.setStyle(Paint.Style.STROKE);
        String des = iscalendarInfo(year,month,day);
        if(day== selDay){//日期为选中的日期
            if(!TextUtils.isEmpty(des)){//desc不为空的时候
                int dateY = (int) startY;
                paint.setColor(theme.colorSelectDay());
                canvas.drawText(day+"", startX, dateY, paint);

                paint.setColor(theme.colorWeekday());
                paint.setTextSize(theme.sizeDesc());
                int desX = (int) (columnSize * column + (columnSize - paint.measureText(des))/2);
                int desY = (int) (rowSize * row + rowSize*0.9 - (paint.ascent() + paint.descent())/2);
                canvas.drawText(des, desX, desY, paint);
            }else{//des为空的时候
                paint.setColor(theme.colorSelectDay());
                canvas.drawText(day+"", startX, startY, paint);
            }
        }else if(day== currDay && currDay != selDay && currMonth == selMonth){//今日的颜色，不是选中的时候
            //正常月，选中其他日期，则今日为红色
            paint.setColor(theme.colorToday());
            canvas.drawText(day+"", startX, startY, paint);
        }else{
            if(!TextUtils.isEmpty(des)){//没选中，但是desc不为空
                int dateY = (int)startY;
                paint.setColor(theme.colorWeekday());
                canvas.drawText(day + "", startX, dateY, paint);

                paint.setTextSize(theme.sizeDesc());
                paint.setColor(theme.colorDesc());
                int desX = (int) (columnSize * column + Math.abs((columnSize - paint.measureText(des))/2));
                int desY = (int) (startY + 20);
                canvas.drawText(des, desX, desY, paint);
            }else{//des为空
                paint.setColor(theme.colorWeekday());
                canvas.drawText(day+"", startX, startY, paint);
            }
        }
    }

    @Override
    protected void createTheme() {
        theme = new ADCircleDayTheme();
    }
}
