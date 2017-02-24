package com.example.ysh.myapplication.view;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;


/**
 * 类说明：
 *
 * @author yangsh
 *         CreateTime: 2017/2/22 16:04
 *         Description:
 */

public class FastEditBar extends TabLayout {

    private OnFastEditClickListener mListener;
    private List<EditAction> mEditActions;

    public FastEditBar(Context context) {
        super(context);
        initView();
    }

    public FastEditBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public FastEditBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mEditActions = new ArrayList<>();
        mEditActions.add(new EditAction("?", "?", 0));
        mEditActions.add(new EditAction("()", "()", -1));
        mEditActions.add(new EditAction("、", "、", 0));
        mEditActions.add(new EditAction("+", "+", 0));
        mEditActions.add(new EditAction("!", "!", 0));
        mEditActions.add(new EditAction("[]", "[]", -1));
        mEditActions.add(new EditAction("◀", "", -1, true));
        mEditActions.add(new EditAction("▶", "", 1, true));

        for (EditAction action : mEditActions) {
            addTab(newTab().setText(action.text).setContentDescription(action.text));
        }

        addOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(Tab tab) {
                if (mListener != null) {
                    mListener.onFastEdit(mEditActions.get(tab.getPosition()));
                }
            }

            @Override
            public void onTabUnselected(Tab tab) {
            }

            @Override
            public void onTabReselected(Tab tab) {
                if (mListener != null) {
                    mListener.onFastEdit(mEditActions.get(tab.getPosition()));
                }
            }
        });

    }

    public void setFastEditClickListener(OnFastEditClickListener listener) {
        this.mListener = listener;
    }

    public class EditAction {
        String text;
        String content;
        int cursorOffset;
        boolean isLongPress;

        EditAction(String text, String content, int cursorOffset) {
            this(text, content, cursorOffset, false);
        }

        EditAction(String text, String content, int cursorOffset, boolean isLongPress) {
            this.text = text;
            this.content = content;
            this.cursorOffset = cursorOffset;
            this.isLongPress = isLongPress;
        }
    }

    public interface OnFastEditClickListener {
        void onFastEdit(EditAction editAction);
    }
}
