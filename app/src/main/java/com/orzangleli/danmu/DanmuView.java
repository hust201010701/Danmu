package com.orzangleli.danmu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by 81118 on 2016/9/4.
 */
public class DanmuView extends View {

    private String mOrientation = "horizontal";
    private boolean mAntidirection = false;

    private int width;
    private int height;

    ArrayList<DanmuText> list;

    public void setOrientation(String orientation) {
        if (orientation.equals("horizontal"))
            mOrientation = "horizontal";
        else if (orientation.equals("vertical"))
            mOrientation = "vertical";
        else
            mOrientation = "horizontal";
    }

    public void setmAntidirection(boolean b)
    {
        mAntidirection = b;
    }


    public DanmuView(Context context) {
        this(context, null, 0, 0);
    }

    public DanmuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0, 0);
    }

    public DanmuView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
        Log.i("lxc", "33333333333333");
    }

    public DanmuView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DanmuView, 0, 0);
        mOrientation = typedArray.getString(R.styleable.DanmuView_orientation);
        mAntidirection = typedArray.getBoolean(R.styleable.DanmuView_antidirection, false);

        list = new ArrayList<DanmuText>();

        Log.i("lxc", "44444444444");

    }

    public void addDanmu(DanmuText text) {
        if (mOrientation.equals("vertical"))
            text.setOrientation(1);
        else
            text.setOrientation(0);
        if (mAntidirection)
            text.setAntiDirection(1);
        else
            text.setAntiDirection(0);
        list.add(text);
    }

    public void removeDanmu(int i) {
        list.remove(i);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);

//        Log.i("lxc","width= "+width);
//        Log.i("lxc","height= "+height);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getAntiDirection() == 0) {
                //竖直方向运动
                if (list.get(i).getOrientation() == 1) {
                    if (list.get(i).getY() < height + list.get(i).getHeight()) {
                        canvas.drawText(list.get(i).getContent(), (int) (list.get(i).getxRate() * width), height - list.get(i).getY(), list.get(i).getPaint());
                        postInvalidate();
                    } else {
                        list.get(i).setShow(false);
                        removeDanmu(i);
                        postInvalidate();
                    }

                } else {
                    if (list.get(i).getX() < width + list.get(i).getWidth()) {
                        canvas.drawText(list.get(i).getContent(), width - list.get(i).getX(), (int) (list.get(i).getyRate() * height), list.get(i).getPaint());
                        postInvalidate();
                    } else {
                        list.get(i).setShow(false);
                        removeDanmu(i);
                        postInvalidate();
                    }

                }
            }
            else
            {

                //竖直方向运动
                if (list.get(i).getOrientation() == 1) {
                    if (list.get(i).getY() < height + list.get(i).getHeight()) {
                        canvas.drawText(list.get(i).getContent(), (int) (list.get(i).getxRate() * width), list.get(i).getY() - list.get(i).getHeight(), list.get(i).getPaint());
                        postInvalidate();
                    } else {
                        list.get(i).setShow(false);
                        removeDanmu(i);
                        postInvalidate();
                    }

                } else {
                    if (list.get(i).getX() < width + list.get(i).getWidth()) {
                        canvas.drawText(list.get(i).getContent(), list.get(i).getX() - list.get(i).getWidth(), (int) (list.get(i).getyRate() * height), list.get(i).getPaint());
                        postInvalidate();
                    } else {
                        list.get(i).setShow(false);
                        removeDanmu(i);
                        postInvalidate();
                    }

                }

            }

        }

    }


}
