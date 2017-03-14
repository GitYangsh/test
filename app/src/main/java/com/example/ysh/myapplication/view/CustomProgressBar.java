package com.example.ysh.myapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.example.ysh.myapplication.R;

/**
 * 类说明：
 *
 * @author yangsh
 * @version 1.0
 * @time 2017/2/12 15:57
 * Description:
 */

public class CustomProgressBar extends View {

    private int mMax = 100;

    private int mProgress = 0;

    private int mReachedBarColor;

    private int mUnreachedBarColor;

    private float mReachedBarHeight;

    private float mUnreachedBarHeight;

    private Paint mReachedPaint;

    private Paint mUnreachedPaint;

    private RectF mReachedRectF = new RectF();

    private RectF mUnreachedRectF = new RectF();

    public CustomProgressBar(Context context) {
        this(context, null);
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int defaultReachedColor = Color.rgb(66, 145, 241);
        int defaultUnreachedColor = Color.rgb(204, 204, 204);
        float defaultReachedHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1.5f, displayMetrics);
        float defaultUnreachedHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1.0f, displayMetrics);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomProgressBar, defStyleAttr, 0);
        mReachedBarColor = typedArray.getColor(R.styleable.CustomProgressBar_reachedBarColor, defaultReachedColor);
        mUnreachedBarColor = typedArray.getColor(R.styleable.CustomProgressBar_unreachedBarColor, defaultUnreachedColor);
        mReachedBarHeight = typedArray.getDimension(R.styleable.CustomProgressBar_reachedBarHeight, defaultReachedHeight);
        mUnreachedBarHeight = typedArray.getDimension(R.styleable.CustomProgressBar_unreachedBarHeight, defaultUnreachedHeight);
        setMax(typedArray.getInteger(R.styleable.CustomProgressBar_max, mMax));
        setProgress(typedArray.getInteger(R.styleable.CustomProgressBar_progress, mProgress));

        typedArray.recycle();

        initPaint();
    }

    private void initPaint() {
        mReachedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mReachedPaint.setColor(mReachedBarColor);

        mUnreachedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mUnreachedPaint.setColor(mUnreachedBarColor);
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return super.getSuggestedMinimumWidth();
    }

    @Override
    public int getMinimumHeight() {
        return Math.max((int) mReachedBarHeight, (int) mUnreachedBarHeight);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int padding = getPaddingLeft() + getPaddingRight();

        if (mode == MeasureSpec.EXACTLY) {
            return size;
        }
        int result = getSuggestedMinimumWidth() + padding;
        if (mode == MeasureSpec.AT_MOST) {
            return Math.max(result, size);
        }
        return size;
    }

    private int measureHeight(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int padding = getPaddingTop() + getPaddingBottom();

        if (mode == MeasureSpec.EXACTLY) {
            return size;
        }
        int result = getSuggestedMinimumWidth() + padding;
        if (mode == MeasureSpec.AT_MOST) {
            return Math.min(result, size);
        }
        return size;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        calculateDrawRectF();

        canvas.drawRect(mReachedRectF, mReachedPaint);
        canvas.drawRect(mUnreachedRectF, mUnreachedPaint);
    }

    private void calculateDrawRectF() {
        mReachedRectF.left = getPaddingLeft();
        mReachedRectF.top = getHeight() / 2.0f - mReachedBarHeight / 2.0f;
        mReachedRectF.right = (getRight() - getPaddingLeft() - getPaddingRight()) / mMax * 1.0f * mProgress + getPaddingLeft();
        mReachedRectF.bottom = getHeight() / 2.0f + mReachedBarHeight / 2.0f;

        mUnreachedRectF.left = mReachedRectF.right;
        mUnreachedRectF.top = getHeight() / 2.0f - mUnreachedBarHeight / 2.0f;
        mUnreachedRectF.right = getWidth() - getPaddingRight();
        mUnreachedRectF.bottom = getHeight() / 2.0f + mUnreachedBarHeight / 2.0f;
    }

    public void incrementProgressBy(int by) {
        if (by > 0) {
            setProgress(mProgress + by);
        }
    }

    private void setMax(int max) {
        if (max > 0) {
            mMax = max;
            invalidate();
        }
    }

    public void setProgress(int progress) {
        if (progress >= 0 && progress <= mMax) {
            mProgress = progress;
            invalidate();
        }
    }
}
