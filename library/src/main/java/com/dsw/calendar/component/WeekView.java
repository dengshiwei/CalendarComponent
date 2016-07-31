package com.dsw.calendar.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.dsw.calendar.theme.DefaultWeekTheme;
import com.dsw.calendar.theme.IWeekTheme;

/**
 * Created by Administrator on 2016/7/31.
 */
public class WeekView extends View {
    private IWeekTheme weekTheme;
    private Paint paint;
    private DisplayMetrics mDisplayMetrics;
    private String[] weekString = new String[]{"日","一","二","三","四","五","六"};
    public WeekView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDisplayMetrics = getResources().getDisplayMetrics();
        paint = new Paint();
        weekTheme = new DefaultWeekTheme();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if(heightMode == MeasureSpec.AT_MOST){
            heightSize = (int) (mDisplayMetrics.density * 30);
        }
        if(widthMode == MeasureSpec.AT_MOST){
            widthSize = (int) (mDisplayMetrics.density * 300);
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        canvas.drawColor(weekTheme.colorWeekView());
        //进行画上下线
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(weekTheme.colorTopLinen());
        paint.setStrokeWidth(weekTheme.sizeLine());
        canvas.drawLine(0, 0, width, 0, paint);

        //画下横线
        paint.setColor(weekTheme.colorBottomLine());
        canvas.drawLine(0, height, width, height, paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(weekTheme.sizeText() * mDisplayMetrics.scaledDensity);
        int columnWidth = width / 7;
        for(int i=0;i < weekString.length;i++){
            String text = weekString[i];
            int fontWidth = (int) paint.measureText(text);
            int startX = columnWidth * i + (columnWidth - fontWidth)/2;
            int startY = (int) (height/2 - (paint.ascent() + paint.descent())/2);
            if(text.indexOf("日") > -1|| text.indexOf("六") > -1){
                paint.setColor(weekTheme.colorWeekend());
            }else{
                paint.setColor(weekTheme.colorWeekday());
            }
            canvas.drawText(text, startX, startY, paint);
        }
    }

    public void setWeekTheme(IWeekTheme weekTheme) {
        this.weekTheme = weekTheme;
    }

    /**
     * 设置星期的形式
     * @param weekString
     * 默认值	"日","一","二","三","四","五","六"
     */
    public void setWeekString(String[] weekString) {
        this.weekString = weekString;
    }
}
