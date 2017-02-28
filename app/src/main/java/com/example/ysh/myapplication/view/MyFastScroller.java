package com.example.ysh.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;

import com.example.ysh.myapplication.R;

/**
 * 类说明：
 *
 * @author yangsh
 * @version 1.0
 *          CreateTime:  2017/2/24 16:31
 *          Description:
 */

public class MyFastScroller {
    private static final String TAG = MyFastScroller.class.getSimpleName();
    private static final int STATE_HIDE = 0;
    private static final int STATE_VISIBLE = 1;
    private static final int STATE_DRAG = 2;

    private FastScrollEditText mEditText;
    private Drawable mThumbDrawable;
    private int mThumbW;
    private int mThumbH;
    private int mState;
    private long mLong;
    private int mOffset;

    public MyFastScroller(FastScrollEditText fastScrollEditText) {
        mEditText = fastScrollEditText;
        init(fastScrollEditText.getContext());
    }

    private void init(Context context) {
        mThumbDrawable = context.getResources().getDrawable(R.drawable.scrollbar_handle_accelerated_anim2);
        mThumbW = context.getResources().getDimensionPixelSize(R.dimen.fast_scrollbar_width);
        mThumbH = context.getResources().getDimensionPixelSize(R.dimen.fast_scrollbar_height);
    }

    public void draw(Canvas canvas) {
        if (mState == STATE_HIDE) {
            return;
        }
        mThumbDrawable.draw(canvas);
    }

    void onScrollChanged(int horiz, int vert, int oldHoriz, int oldVert) {
        mOffset = vert - oldVert;
        if (mState == STATE_DRAG) {
            return;
        }
        changeScrollState(STATE_VISIBLE);
    }

    void onSizeChanged() {
        if (mState == STATE_DRAG) {
            return;
        }
        changeScrollState(STATE_VISIBLE);
    }

    public void changeScrollState(int state) {
        int totalHeight = mEditText.getLayout().getHeight();
        int visibleHeight = mEditText.getVisibleHeight();
        if (totalHeight > visibleHeight) {
            int visibleWidth = mEditText.getWidth();
            int scrollY = mEditText.getScrollY();
            float percent = 1.0f * scrollY / (totalHeight - visibleHeight);
            int offset = (int)((1.0f * mThumbH / visibleHeight) * totalHeight);
            int thumbY = (int) (percent * totalHeight);

            if (mOffset >= 0) {
                if (thumbY < totalHeight / 2) {
                    mThumbDrawable.setBounds(visibleWidth - mThumbW, thumbY, visibleWidth, thumbY + mThumbH);
                }
                if (thumbY < totalHeight / 2 + mThumbH) {
                    mThumbDrawable.setBounds(visibleWidth - mThumbW, scrollY + (int)(1.0f * scrollY / totalHeight * visibleHeight), visibleWidth, scrollY + (int)(1.0f * scrollY / totalHeight * visibleHeight) + mThumbH);
                } else {
                    mThumbDrawable.setBounds(visibleWidth - mThumbW, thumbY - mThumbH, visibleWidth, thumbY);
                }
            } else {

            }
            mState = state;
        } else {
            mState = STATE_HIDE;
        }
    }

    boolean onInterceptTouchEvent(MotionEvent event) {
        if (mState == STATE_DRAG) {
            return true;
        }
        if ((mState != STATE_HIDE) && (event.getAction() == MotionEvent.ACTION_DOWN) && (isPointInside(event.getX(), event.getY()))) {
            mState = STATE_DRAG;
            return true;
        }
        return false;
    }

    private boolean isPointInside(float x, float y) {
        Rect rect = mThumbDrawable.getBounds();
        System.out.println("====x:" + x + ",y:" + y);
        System.out.println("====rect:" + rect.toShortString());
        System.out.println("====mEditText.getScrollY():" + mEditText.getScrollY());
        return (x > rect.left) && (y > rect.top - mEditText.getScrollY()) && (y < rect.bottom - mEditText.getScrollY());
    }

    boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLong = System.currentTimeMillis();
                System.out.println("====ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("====ACTION_MOVE");

                System.out.println("====motionEvent.getY()=" + event.getY());
                System.out.println("====mEditText.getHeight()=" + mEditText.getHeight());

                long current = System.currentTimeMillis();
                System.out.println("====current=" + current);
                System.out.println("====mLong=" + mLong);
                if (current - mLong > 30L) {
                    float percent = 1.0f * event.getY() / mEditText.getHeight();
                    scrollTo(percent);
                    setDrawPos(percent);
                    mLong = current;
                }
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("====ACTION_UP");
                mState = STATE_VISIBLE;
                break;
            default:
                break;
        }
        return true;
    }

    private void setDrawPos(float percent) {
        int scrollY = mEditText.getScrollY();
        System.out.println("====scrollY=" + scrollY);
        int visibleWidth = mEditText.getWidth();
        int visibleHeight = mEditText.getHeight();
        int totalHeight = mEditText.getLayout().getHeight();
        int thumbY = scrollY + (int) (percent * visibleHeight);
        int offset = mThumbH / 2;
        if (thumbY < offset) {
            thumbY = offset;
        } else if (thumbY > totalHeight - offset) {
            thumbY = totalHeight - offset;
        }
        mThumbDrawable.setBounds(visibleWidth - mThumbW, thumbY - offset, visibleWidth, thumbY + offset);
    }

    private void scrollTo(float percent) {
        if (percent > 1) {
            percent = 1;
        } else if (percent < 0) {
            percent = 0;
        }
        int lineCount = mEditText.getLineCount();
        int targetLine = (int) (percent * lineCount);
        System.out.println("====percent=" + percent);
        System.out.println("====lineCount=" + lineCount);
        System.out.println("====targetLine=" + targetLine);
        mEditText.moveToLine(targetLine);
    }


    void stop() {

    }
}
