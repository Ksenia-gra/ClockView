package com.example.clockviewproj;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;


public class CustomClockView extends View {
    private double mHour;
    private double mMinute;
    private double mSecond;
    private int mHeight;
    private int mWidth;
    private int mRadius;
    private double mAngle;
    private int mCentreX;
    private int mCentreY;
    private boolean mIsInit;
    private Paint mPaint;
    private Path mPath;
    private int[] mNumbers;
    private int mHourHandSize;
    private int mMinuteHandSize;
    private int mSecondHandSize;
    private Rect mRect;
    private int mFontSize;

    public CustomClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    private void init(){
        mNumbers = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        mIsInit = true;
        mHeight = getHeight();
        mWidth = getWidth();
        mFontSize=62;
        mCentreX = mWidth/2;
        mCentreY = mHeight/2;
        mRadius = ((Math.min(mHeight, mWidth))-20)/2;
        mPaint = new Paint();
        mPath = new Path();
        mRect = new Rect();
        mHourHandSize = mRadius - mRadius/2;
        mSecondHandSize = mRadius - mRadius/4;
        mMinuteHandSize = mRadius - mRadius/3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!mIsInit) {
            init();
        }
        drawCircle(canvas);
        drawHands(canvas);
        makeClockFace(canvas);
        postInvalidateDelayed(500);
    }
    private void setPaintAttr(int colour, Paint.Style stroke, int strokeWidth) {
        mPaint.reset();
        mPaint.setColor(colour);
        mPaint.setStyle(stroke);
        mPaint.setStrokeWidth(strokeWidth);
    }
    private void makeClockFace(Canvas canvas) {
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(mFontSize);
        for(int i=0;i<60;i++)
        {
            String point = ".";
            if(i%5==0)
                point="";
            mPaint.getTextBounds(point, 0, point.length(), mRect);
            double angle = Math.PI *i/ 30 -Math.PI / 2;
            int x = (int) (mCentreX + Math.cos(angle) * mRadius/1.5 - mRect.width() /2);
            int y = (int) (mCentreY + Math.sin(angle) * mRadius/1.5 + mRect.height() /2);
            canvas.drawText(point, x, y, mPaint);
        }
        for (int number : mNumbers) {
            String num = String.valueOf(number);
            mPaint.getTextBounds(num, 0, num.length(), mRect);
            double angle = Math.PI / 6 * (number - 3);
            int x = (int) (mCentreX + Math.cos(angle) * mRadius/1.2 - mRect.width() /2);
            int y = (int) (mCentreY + Math.sin(angle) * mRadius/1.2 + mRect.height() /2);
            canvas.drawText(num, x, y, mPaint);
        }
    }

    private void drawHands(Canvas canvas) {
        Calendar calendar = Calendar.getInstance();
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mHour = mHour > 12 ? mHour - 12 : mHour;
        mMinute = calendar.get(Calendar.MINUTE);
        mSecond = calendar.get(Calendar.SECOND);

        drawSecondsHand(canvas,mSecond);
        drawMinuteHand(canvas,mMinute);
        drawHourHand(canvas, mHour * 5.0);


    }
    private void drawMinuteHand(Canvas canvas, double loc) {
        mPaint.reset();
        setPaintAttr(Color.BLACK, Paint.Style.STROKE,12);
        mAngle = Math.PI * loc / 30 - Math.PI / 2;
        canvas.drawLine(mCentreX,
                mCentreY, (float) (mCentreX + Math.cos( mAngle)* mMinuteHandSize),
                (float)(mCentreY+Math.sin(mAngle)*mMinuteHandSize),
                mPaint);
    }
    private void drawHourHand(Canvas canvas, double loc) {
        mPaint.reset();
        setPaintAttr(Color.BLACK, Paint.Style.STROKE,15);
        mAngle = Math.PI * loc / 30 - Math.PI / 2;
        canvas.drawLine(mCentreX,mCentreY,(float) (mCentreX+Math.cos(mAngle)*mHourHandSize)
                , (float) (mCentreY+Math.sin(mAngle)*mHourHandSize),mPaint);
    }
    private void drawSecondsHand(Canvas canvas, double location) {
        mPaint.reset();
        setPaintAttr(Color.RED, Paint.Style.STROKE,8);
        mAngle = Math.PI * location / 30 - Math.PI / 2;
        canvas.drawLine(mCentreX,mCentreY,(float)   (mCentreX+Math.cos(mAngle)*mSecondHandSize)
                , (float) (mCentreY+Math.sin(mAngle)*mSecondHandSize),mPaint);
    }
    private void drawCircle(Canvas canvas) {
        setPaintAttr(Color.WHITE, Paint.Style.FILL,8);
        canvas.drawCircle(mCentreX,mCentreY,mRadius+1,mPaint);
        setPaintAttr(Color.BLACK, Paint.Style.STROKE,8);
        canvas.drawCircle(mCentreX,mCentreY,mRadius,mPaint);
    }

}
