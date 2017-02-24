package com.example.ysh.myapplication.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import java.util.LinkedList;

/**
 * 类说明：
 *
 * @author yangsh
 *         CreateTime: 2017/2/23 10:54
 *         Description:
 */

public class AuthorEditText extends EditText implements FastEditBar.OnFastEditClickListener {

    private static final String TAG = AuthorEditText.class.getSimpleName();
    private static final String SYMBOL_INDENT = "\u3000\u3000";
    private static final int HISTORY_MAX_SIZE = 50;

    private Editable mEditable;
    /**
     * 操作序号(一次编辑可能对应多个操作，如替换文字，就是删除+插入)
     */
    private long mActionIndex;
    /**
     * 撤销栈
     */
    private final LinkedList<Action> mHistory = new LinkedList<>();
    /**
     * 恢复栈
     */
    private final LinkedList<Action> mHistoryBack = new LinkedList<>();
    /**
     * 自动操作标志，防止重复回调,导致无限撤销
     */
    private boolean mLockFlag = false;
    /**
     * 记录修改之前的文字
     */
    private CharSequence mBeforeChangeText;
    /**
     * 恢复栈最大容量
     */
    private int mHistoryMaxSize = HISTORY_MAX_SIZE;


    public AuthorEditText(Context context) {
        super(context);
        init();
    }

    public AuthorEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AuthorEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mEditable = getText();

        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    // 换行之后加缩进，actionIndex保持一致
                    mActionIndex--;
                    mEditable.insert(getSelectionStart(), SYMBOL_INDENT);
                }
                return false;
            }
        });
    }

    /**
     * On text changed. Override by TextView
     *
     * @param s      the s
     * @param start  the start 起始光标
     * @param before the before 选择数量
     * @param count  the endCursor 添加的数量
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (mLockFlag) {
            mBeforeChangeText = s.subSequence(0, s.length());
            return;
        }
        Log.d(TAG, "onTextChanged:start=" + start + ",before=" + before + ",count=" + count + ",s=" + s);
        Log.d(TAG, "mBeforeChangeText:s=" + mBeforeChangeText);
        // 1.增加文字：start=操作之前光标位置,before=0,count=文字个数,
        //   增加文字内容=s.subSequence(start, start + count);
        // 2.删除文字：start=操作之后光标位置,before=删除文字个数,count=0,
        //   删除文字内容=beforeChangeText.subSequence(start, start + before);
        // 3.替换文字：before=选择文字个数,count=替换文字个数
        if (before == 0 && count > 0) {
            Action addAction = newAction(s, start, count, true, ++mActionIndex);
            addHistoryStack(addAction);
        } else if (before > 0 && count == 0) {
            Action delAction = newAction(mBeforeChangeText, start, before, false, ++mActionIndex);
            addHistoryStack(delAction);
        } else if (before > 0 && count > 0) {
            //先删除，后增加，使用一个index
            Action delAction = newAction(mBeforeChangeText, start, before, false, ++mActionIndex);
            Action addAction = newAction(s, start, count, true, mActionIndex);
            addHistoryStack(delAction);
            addHistoryStack(addAction);
        }
        mBeforeChangeText = s.subSequence(0, s.length());
    }

    private Action newAction(CharSequence s, int start, int count, boolean isAdd, long index) {
        Log.d(TAG, "newAction:s.length=" + s.length() + ",start=" + start + ",end=" + (start + count) + ",s=" + s);
        CharSequence charSequence = s.subSequence(start, start + count);
        return new Action(charSequence, start, isAdd, index);
    }

    private void addHistoryStack(Action action) {
        mHistory.push(action);
        mHistoryBack.clear();

        if (mHistory.size() > mHistoryMaxSize) {
            // 大于恢复栈最大容量时，删除第一条index相同的历史记录
            long lastIndex = mHistory.removeLast().index;
            for (int i = mHistory.size(); i > 0; i--) {
                if (lastIndex != mHistory.getLast().index) {
                    break;
                }
                mHistory.removeLast();
            }
        }
    }

    @Override
    public void onFastEdit(FastEditBar.EditAction action) {
        if (!TextUtils.isEmpty(action.content)) {
            mEditable.insert(getSelectionStart(), action.content);
        }
        if (action.cursorOffset != 0) {
            int position = getSelectionStart() + action.cursorOffset;
            if (position >= 0 && position <= mEditable.length()) {
                setSelection(position);
            }
        }
    }

    public void setHistoryMaxSize(int size) {
        mHistoryMaxSize = size;
    }

    /**
     * 首次设置文本
     * Set default text.
     */
    public final void setDefaultText(CharSequence text) {
        clearHistory();
        mLockFlag = true;
        mEditable.replace(0, mEditable.length(), text);
        mLockFlag = false;
    }

    /**
     * 清理记录
     * Clear history.
     */
    private void clearHistory() {
        mHistory.clear();
        mHistoryBack.clear();
    }

    /**
     * 撤销
     * Undo.
     */
    public final void undo() {
        if (mHistory.isEmpty()) {
            return;
        }
        //锁定操作
        mLockFlag = true;
        Action action = mHistory.pop();
        mHistoryBack.push(action);
        if (action.isAdd) {
            //撤销添加
            mEditable.delete(action.start, action.start + action.text.length());
            setSelection(action.start);
        } else {
            //撤销删除
            mEditable.insert(action.start, action.text);
            setSelection(action.start + action.text.length());
        }
        //释放操作
        mLockFlag = false;
        //判断是否是下一个动作是否和本动作是同一个操作，直到不同为止
        if (!mHistory.isEmpty() && mHistory.peek().index == action.index) {
            undo();
        }
    }

    /**
     * 恢复
     * Redo.
     */
    public final void redo() {
        if (mHistoryBack.isEmpty()) {
            return;
        }
        mLockFlag = true;
        Action action = mHistoryBack.pop();
        mHistory.push(action);
        if (action.isAdd) {
            //恢复添加
            mEditable.insert(action.start, action.text);
            setSelection(action.start + action.text.length());
        } else {
            //恢复删除
            mEditable.delete(action.start, action.start + action.text.length());
            setSelection(action.start);
        }
        mLockFlag = false;
        //判断是否是下一个动作是否和本动作是同一个操作
        if (!mHistoryBack.isEmpty() && mHistoryBack.peek().index == action.index) {
            redo();
        }
    }

    private class Action {
        /**
         * 改变字符.
         */
        final CharSequence text;
        /**
         * 光标位置.
         */
        final int start;
        /**
         * 标志增加操作.
         */
        final boolean isAdd;
        /**
         * 操作序号.
         */
        final long index;

        Action(CharSequence text, int start, boolean add, long index) {
            this.text = text;
            this.start = start;
            this.isAdd = add;
            this.index = index;
        }
    }

}
