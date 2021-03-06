package com.example.ysh.myapplication.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import java.util.LinkedList;

/**
 * 类说明：
 *
 * @author yangsh
 *         CreateTime: 2017/2/23 10:54
 *         Description:
 */

public class AuthorEditText extends FastScrollEditText implements FastEditBar.OnFastEditClickListener {

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
     * 是否首行缩进
     */
    private boolean mIsIndent = false;
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
        setFastScrollEnabled(true);

        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    if (mIsIndent) {
                        // 换行之后加缩进，actionIndex保持一致
                        mActionIndex--;
                        mEditable.insert(getSelectionStart(), SYMBOL_INDENT);
                    }
                }
                return false;
            }
        });

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged:start=" + start + ",count=" + count + ",after=" + after + ",s=" + s);
                if (mLockFlag) {
                    return;
                }
                // 删除文字
                if (count > 0) {
                    Action addAction = newAction(s, start, count, false, ++mActionIndex);
                    addHistoryStack(addAction);
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged:start=" + start + ",before=" + before + ",count=" + count + ",s=" + s);
                if (mLockFlag) {
                    return;
                }
                // 输入文字
                if (count > 0) {
                    //before = 0 时，输入文字，index增加
                    //before > 0 时，替换文字，index不变
                    if (before == 0) {
                        ++mActionIndex;
                    }
                    Action addAction = newAction(s, start, count, true, mActionIndex);
                    addHistoryStack(addAction);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private Action newAction(CharSequence s, int start, int count, boolean isAdd, long index) {
        Log.d(TAG, "newAction:s.length=" + s.length() + ",start=" + start + ",end=" + (start + count) + ",isAdd=" + isAdd + ",s=" + s);
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
    public void setDefaultText(CharSequence text) {
        clearHistory();
        mLockFlag = true;
        mEditable.replace(0, mEditable.length(), text);
        mLockFlag = false;
    }

    /**
     * 设置首行缩进
     * Set indent.
     */
    public void setIndent(boolean isIndent) {
        mIsIndent = isIndent;
        if (mIsIndent) {
            mLockFlag = true;
            mEditable.insert(0, SYMBOL_INDENT);
            mLockFlag = false;
        }
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
