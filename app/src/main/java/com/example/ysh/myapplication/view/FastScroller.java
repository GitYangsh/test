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

public class FastScroller {
    private static final int STATE_HIDE = 0;
    private static final int STATE_VISIBLE = 1;
    private static final int STATE_DRAG = 2;

    private FastScrollEditText mEditText;
    private Drawable mThumbDrawable;
    private int mThumbW;
    private int mThumbH;
    private int mState = STATE_HIDE;
    private float mTouchDownY;

    public FastScroller(FastScrollEditText fastScrollEditText) {
        mEditText = fastScrollEditText;
        init(fastScrollEditText.getContext());
    }

    private void init(Context context) {
        mThumbDrawable = context.getResources().getDrawable(R.drawable.scrollbar_handle_accelerated_anim2);
        mThumbDrawable.setAlpha(200);
        mThumbW = context.getResources().getDimensionPixelSize(R.dimen.fast_scrollbar_width);
        mThumbH = context.getResources().getDimensionPixelSize(R.dimen.fast_scrollbar_height);
    }

    public void draw(Canvas canvas) {
        if (mState == STATE_HIDE) {
            return;
        }
        mThumbDrawable.draw(canvas);
    }

    void onScrollChanged() {
        changeScrollState();
    }

    void onSizeChanged() {
        changeScrollState();
    }

    private void changeScrollState() {
        int totalHeight = mEditText.getLayout().getHeight();
        int visibleHeight = mEditText.getVisibleHeight();
        if (totalHeight > visibleHeight) {
            int visibleWidth = mEditText.getWidth();

            int scrollY = mEditText.getScrollY();
            int hideHeight = totalHeight - visibleHeight;
            float percent = (1.0f * scrollY / hideHeight);
            int totalOffset = visibleHeight - mThumbH;
            int offset = (int) (percent * totalOffset);
            int thumbY = scrollY + offset;
            mThumbDrawable.setBounds(visibleWidth - mThumbW, thumbY, visibleWidth, thumbY + mThumbH);
            if (mState == STATE_HIDE) {
                mState = STATE_VISIBLE;
            }
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
        return (x > rect.left) && (y > rect.top - mEditText.getScrollY()) && (y < rect.bottom - mEditText.getScrollY());
    }

    boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mTouchDownY = event.getY();
                System.out.println("====DOWN event.getY()=" + event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("====MOVE event.getY()=" + event.getY());
                scrollTo(event);
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("====UP event.getY()=" + event.getY());
                mState = STATE_VISIBLE;
                break;
            default:
                break;
        }
        return true;
    }

    private void scrollTo(MotionEvent event) {
        float move = mTouchDownY - event.getY();
        if (Math.abs(move) < 10f) {
            return;
        }
        int totalHeight = mEditText.getLayout().getHeight();
        int visibleHeight = mEditText.getVisibleHeight();
        float percent = event.getY() / visibleHeight;
        if (percent > 1) {
            percent = 1;
        } else if (percent < 0) {
            percent = 0;
        }
        int scrollY = (int) (percent * (totalHeight - visibleHeight));
        mEditText.scrollTo(0, scrollY);

        int lineCount = mEditText.getLineCount();
        int targetLine = (int) (percent * lineCount);
        mEditText.moveToLine(targetLine);
    }

    void stop() {

    }
}
