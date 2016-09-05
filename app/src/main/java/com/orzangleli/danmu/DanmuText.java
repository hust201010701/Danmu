package com.orzangleli.danmu;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by 81118 on 2016/9/4.
 */
public class DanmuText {
    private String content ;
    private int textSize ;
    private int textColor;
    private int x;
    private int y;
    private double xRate;
    private double yRate;
    private Paint paint;
    private boolean isShow ;
    private int speed ;
    private int width;
    private int height;
    private int orientation; //方向，0代表水平，1代表竖直
    private int antiDirection; //是否反方向运动，0代表否，1代表是

    DanmuText()
    {
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setTextSize(80);
        width = 0;
        speed = 1;
        xRate = yRate = Math.random();
        orientation = 0;
        antiDirection = 0;
        new Thread(new DanmuRollRunnable()).start();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        Rect rect=new Rect();
        paint.getTextBounds(content, 0, content.length(), rect);
        width = rect.width();
        height = rect.height();
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        paint.setColor(textColor);
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        paint.setTextSize(textSize);
        Rect rect=new Rect();
        paint.getTextBounds(content, 0, content.length(), rect);
        width = rect.width();
        height = rect.height();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getxRate() {
        return xRate;
    }

    public void setxRate(double xRate) {
        this.xRate = xRate;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getyRate() {
        return yRate;
    }

    public void setyRate(double yRate) {
        this.yRate = yRate;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight() {

        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getAntiDirection() {
        return antiDirection;
    }

    public void setAntiDirection(int antiDirection) {
        this.antiDirection = antiDirection;
    }

    public class DanmuRollRunnable implements Runnable {
        @Override
        public void run() {
            while (true) {
                if(orientation == 0)
                    x += speed;
                else
                    y += speed;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


    }

}
