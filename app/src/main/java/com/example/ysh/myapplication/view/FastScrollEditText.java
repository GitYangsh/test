package com.example.ysh.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

/**
 * 类说明：
 *
 * @author yangsh
 * @version 1.0
 *          CreateTime:  2017/2/24 13:43
 *          Description:
 */

public class FastScrollEditText extends EditText {
    private FastScroller mFastScroller;

    public FastScrollEditText(Context context) {
        super(context);
    }

    public FastScrollEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FastScrollEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mFastScroller != null) {
            mFastScroller.draw(canvas);
        }
    }

    @Override
    protected void onScrollChanged(int horiz, int vert, int oldHoriz, int oldVert) {
        super.onScrollChanged(horiz, vert, oldHoriz, oldVert);
        if (mFastScroller != null) {
            mFastScroller.onScrollChanged();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (mFastScroller != null) {
            mFastScroller.onSizeChanged();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mFastScroller != null) {
            if (mFastScroller.onInterceptTouchEvent(event)) {
                return mFastScroller.onTouchEvent(event);
            }

            if (event.getAction() == MotionEvent.ACTION_UP) {
                mFastScroller.hide();
            }
        }
        return super.onTouchEvent(event);
    }

    public void moveToLine(int line) {
        int start = getLayout().getLineStart(line);
        setSelection(start);
    }

    public void setFastScrollEnabled(boolean enabled) {
        if (enabled) {
            if (mFastScroller == null) {
                mFastScroller = new FastScroller(this);
            }
        } else {
            mFastScroller.stop();
            mFastScroller = null;
        }
    }
}