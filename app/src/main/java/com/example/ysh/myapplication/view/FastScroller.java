package com.example.ysh.myapplication.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
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
    private static final int STATE_NONE = 0;
    private static final int STATE_HIDE = 1;
    private static final int STATE_VISIBLE = 2;
    private static final int STATE_DRAG = 3;

    private static final int DEFAULT_ALPHA = 200;
    private FastScrollEditText mEditText;
    private Drawable mThumbDrawable;
    private int mThumbW;
    private int mThumbH;
    private int mState = STATE_NONE;
    private float mTouchDownY;
    private AnimatorSet mHideAnimSet = new AnimatorSet();

    public FastScroller(FastScrollEditText fastScrollEditText) {
        mEditText = fastScrollEditText;
        init(fastScrollEditText.getContext());
    }

    private void init(Context context) {
        mThumbDrawable = context.getResources().getDrawable(R.drawable.scrollbar_handle_accelerated_anim2);
        mThumbW = context.getResources().getDimensionPixelSize(R.dimen.fast_scrollbar_width);
        mThumbH = context.getResources().getDimensionPixelSize(R.dimen.fast_scrollbar_height);

        mEditText.post(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator alphaAnim = ObjectAnimator.ofInt(mThumbDrawable, "alpha", DEFAULT_ALPHA, 0);
                ValueAnimator hideAnim = ValueAnimator.ofInt(mEditText.getWidth() - mThumbW, mEditText.getWidth());
                hideAnim.setDuration(300);
                hideAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int currentValue = (int) animation.getAnimatedValue();
                        Rect rect = mThumbDrawable.getBounds();
                        rect.left = currentValue;
                        mThumbDrawable.setBounds(rect);
                        mEditText.invalidate(rect);
                    }
                });
                mHideAnimSet.play(hideAnim).with(alphaAnim);
                mHideAnimSet.setStartDelay(1500);
            }
        });
    }

    public void draw(Canvas canvas) {
        if (mState == STATE_NONE) {
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
            if (mState == STATE_NONE || mState == STATE_HIDE) {
                setState(STATE_VISIBLE);
            }
        }
    }

    boolean onInterceptTouchEvent(MotionEvent event) {
        if (mState == STATE_DRAG) {
            return true;
        }
        if ((event.getAction() == MotionEvent.ACTION_DOWN) && (isPointInside(event.getX(), event.getY()))) {
            setState(STATE_DRAG);
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
                stopAnim();
                break;
            case MotionEvent.ACTION_MOVE:
                scrollTo(event);
                break;
            case MotionEvent.ACTION_UP:
                setState(STATE_HIDE);
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

    private void setState(int state) {
        switch (state) {
            case STATE_HIDE:
                startAnim();
                break;
            case STATE_VISIBLE:
                stopAnim();
                break;
            case STATE_DRAG:
                stopAnim();
                break;
            default:
                break;
        }
        mState = state;
    }

    void hide() {
        setState(STATE_HIDE);
    }

    void stop() {
        stopAnim();
    }

    private void startAnim() {
        if (!mHideAnimSet.isStarted() && !mHideAnimSet.isRunning()) {
            mHideAnimSet.start();
        }
    }

    private void stopAnim() {
        if (mHideAnimSet.isStarted() || mHideAnimSet.isRunning()) {
            mHideAnimSet.cancel();
        }
        mThumbDrawable.setAlpha(DEFAULT_ALPHA);
    }
}
