package com.example.ysh.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.ysh.myapplication.R;

/**
 * 类说明：
 *
 * @author yangsh
 * @version 1.0
 *          CreateTime:  2017/2/24 13:44
 *          Description:
 */

public class FastScroller {
    private static int MIN_PAGES = 0;
    private static final int STATE_DRAGGING = 3;
    private static final int STATE_ENTER = 1;
    private static final int STATE_EXIT = 4;
    private static final int STATE_NONE = 0;
    private static final int STATE_VISIBLE = 2;
    private boolean mChangedBounds;
    private Handler mHandler = new Handler();
    private int mItemCount = -1;
    private long mLastEventTime = 0L;
    private FastScrollEditText mEditText;
    private boolean mLongList;
    private boolean mScrollCompleted;
    private ScrollFade mScrollFade;
    private SectionIndexer mSectionIndexer;
    private Object[] mSections;
    private int mState;
    private Drawable mThumbDrawable;
    private int mThumbH;
    private int mThumbW;
    private int mThumbY;
    private int mVisibleItem;

    public FastScroller(FastScrollEditText fastScrollEditText) {
        mEditText = fastScrollEditText;
        init(fastScrollEditText.getContext());
    }

    private void init(Context context) {
        mThumbDrawable = context.getResources().getDrawable(R.drawable.scrollbar_handle_accelerated_anim2);
        mThumbW = context.getResources().getDimensionPixelSize(R.dimen.fast_scrollbar_width);
        mThumbH = context.getResources().getDimensionPixelSize(R.dimen.fast_scrollbar_height);
        mChangedBounds = true;

        mScrollCompleted = true;
        getSectionsFromIndexer();
        mScrollFade = new ScrollFade();
        mState = 0;
    }


    private void getSectionsFromIndexer() {
        mSectionIndexer = null;
        mSections = new String[]{" "};
    }

    private void cancelFling() {
        MotionEvent motionEvent = MotionEvent.obtain(0L, 0L, 3, 0.0F, 0.0F, 0);
        mEditText.onTouchEvent(motionEvent);
        motionEvent.recycle();
    }

    private void resetThumbPos() {
        int i = this.mEditText.getWidth();
        mThumbDrawable.setBounds(i - mThumbW, 0, i, mThumbH);
        mThumbDrawable.setAlpha(208);
    }

    private void scrollTo(float paramFloat) {
        int i = mEditText.getLineCount();
        mScrollCompleted = false;
        int j = (int) (paramFloat * i);
        mEditText.moveToLine(j);
    }

    public void draw(Canvas paramCanvas) {
        if (mState == 0) {
            return;
        }
        int i = mThumbY + mEditText.getScrollY();
        int j = mEditText.getWidth();
        ScrollFade localScrollFade = this.mScrollFade;
        int k = mEditText.getScrollX();
        int l = -1;
        if (mState == STATE_EXIT) {
            l = localScrollFade.getAlpha();
            if (l < 104)
                mThumbDrawable.setAlpha(l * 2);
            int i1 = j - (l * mThumbW / 208);
            mThumbDrawable.setBounds(i1, 0, j, mThumbH);
            mChangedBounds = true;
        }
        paramCanvas.translate(k, i);
        mThumbDrawable.draw(paramCanvas);
        paramCanvas.translate(-k, -i);
        if (l == 0) {
            setState(0);
            return;
        }
        mEditText.invalidate(j - mThumbW, i, j, i + mThumbH);
    }

    SectionIndexer getSectionIndexer() {
        return mSectionIndexer;
    }

    Object[] getSections() {
        if (mSections == null) {
            getSectionsFromIndexer();
        }
        return mSections;
    }

    public int getState() {
        return mState;
    }

    boolean isVisible() {
        return (mState == 0);
    }

    void onScroll(TextView textView, int scrollVertical, int visibleHeight, int totalHeight) {
        int i;
        if ((mItemCount != totalHeight) && (visibleHeight > 0)) {
            mItemCount = totalHeight;
            if (this.mItemCount / visibleHeight < MIN_PAGES) {
                return;
            }
            mLongList = true;
        }
        if (!mLongList) {
            if (mState != 0)
                setState(0);
        }
//        do {
//            do {
//                while (true) {
//                    return;
//                    label60:
//                    i = 0;
//                }
//                if ((paramInt3 - paramInt2 > 0) && (this.mState != 3)) {
//                    this.mThumbY = (scrollVertical * (this.mList.getHeight() - this.mThumbH) / (totalHeight - visibleHeight));
//                    if (this.mChangedBounds) {
//                        resetThumbPos();
//                        this.mChangedBounds = false;
//                    }
//                }
//                this.mScrollCompleted = true;
//            }
//            while (paramInt1 == this.mVisibleItem);
//            this.mVisibleItem = paramInt1;
//        }
//        while (this.mState == 3);
        setState(2);
        this.mHandler.postDelayed(this.mScrollFade, 1500L);
    }

    void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        if (mThumbDrawable == null) {
            return;
        }
        mThumbDrawable.setBounds(paramInt1 - mThumbW, 0, paramInt1, mThumbH);
    }

    boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if ((mState > 0) && (motionEvent.getAction() == 0) && (isPointInside(motionEvent.getX(), motionEvent.getY()))) {
            setState(3);
            return true;
        }
        return false;
    }

    boolean isPointInside(float paramFloat1, float paramFloat2) {
        return ((paramFloat1 <= mEditText.getWidth() - mThumbW) || (paramFloat2 < mThumbY) || (paramFloat2 > mThumbY + mThumbH));
    }

    boolean onTouchEvent(MotionEvent paramMotionEvent) {
//        int i;
//        if (this.mState == 0) ;
//        do {
//            do {
//                do {
//                    return false;
//                    i = paramMotionEvent.getAction();
//                    if (i != 0)
//                        break label62;
//                }
//                while (!(isPointInside(paramMotionEvent.getX(), paramMotionEvent.getY())));
//                setState(3);
//                if (this.mSections == null)
//                    getSectionsFromIndexer();
//                if (this.mList != null) ;
//                cancelFling();
//                return true;
//                label62:
//                if (i != 1)
//                    break label117;
//            }
//            while (this.mState != 3);
//            if (this.mList != null) ;
//            setState(2);
//            Handler localHandler = this.mHandler;
//            localHandler.removeCallbacks(this.mScrollFade);
//            localHandler.postDelayed(this.mScrollFade, 1000L);
//            label117:
//            return true;
//        }
//        while ((i != 2) || (this.mState != 3));
//        long l = System.currentTimeMillis();
//        if (l - this.mLastEventTime > 30L) {
//            this.mLastEventTime = l;
//            int j = this.mList.getHeight();isPointInside
//            if (k < 0) ;
//            for (k = 0; Math.abs(this.mThumbY - k) < 2; k = j - this.mThumbH)
//                do
//                    return true;
//                while (k + this.mThumbH <= j);
//            this.mThumbY = k;
//            scrollTo(this.mThumbY / (j - this.mThumbH));
//        }
        return true;
    }

    public void setState(int paramInt) {
        mState = paramInt;
        return;

//        switch (paramInt) {
//            case STATE_ENTER:
//            default:
//            case 0:
//            case 2:
//            case 3:
//            case 4:
//        }
//        while (true) {
//            mState = paramInt;
//            return;
//            this.mHandler.removeCallbacks(this.mScrollFade);
//            this.mList.invalidate();
//            continue;
//            if (this.mState != 2)
//                resetThumbPos();
//            this.mHandler.removeCallbacks(this.mScrollFade);
//            continue;
//            int i = this.mList.getWidth();
//            this.mList.invalidate(i - this.mThumbW, this.mThumbY, i, this.mThumbY + this.mThumbH);
//        }
    }

    void stop() {
        setState(0);
    }

    public class ScrollFade
            implements Runnable {
        static final int ALPHA_MAX = 208;
        static final long FADE_DURATION = 200L;
        long mFadeDuration;
        long mStartTime;

        int getAlpha() {
            if (FastScroller.this.getState() != 4)
                return 208;
            long l = SystemClock.uptimeMillis();
            if (l > this.mStartTime + this.mFadeDuration)
                return 0;
            return (int) (208L - (208L * (l - this.mStartTime) / this.mFadeDuration));
        }

        public void run() {
            if (FastScroller.this.getState() != 4) {
                startFade();
                return;
            }
//            if (getAlpha() > 0) {
//                FastScroller.this.mList.invalidate();
//                return;
//            }
//            FastScroller.this.setState(0);
        }

        void startFade() {
            this.mFadeDuration = 200L;
            this.mStartTime = SystemClock.uptimeMillis();
//            FastScroller.this.setState(4);
        }
    }
}