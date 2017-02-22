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
 * @version 1.0
 * @time 2017/2/22 16:04
 * Description:
 */

public class FastTextEditBar extends TabLayout {

    private OnFastTextEditClickListener mListener;
    private List<EditAction> mEditActions;

    public FastTextEditBar(Context context) {
        super(context);
        initView();
    }

    public FastTextEditBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public FastTextEditBar(Context context, AttributeSet attrs, int defStyleAttr) {
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
            addTab(newTab().setText(action.text));
        }

        addOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(Tab tab) {
                if (mListener != null) {
                    mListener.onClick(mEditActions.get(tab.getPosition()));
                }
            }

            @Override
            public void onTabUnselected(Tab tab) {
            }

            @Override
            public void onTabReselected(Tab tab) {
                if (mListener != null) {
                    mListener.onClick(mEditActions.get(tab.getPosition()));
                }
            }
        });

    }

    public void setFastTextEditClickListener(OnFastTextEditClickListener listener) {
        this.mListener = listener;
    }

    public static final class EditAction {
        public String text;
        public String content;
        public int cursorOffset;
        public boolean isLongPress;

        public EditAction(String text, String content, int cursorOffset) {
            this(text, content, cursorOffset, false);
        }

        public EditAction(String text, String content, int cursorOffset, boolean isLongPress) {
            this.text = text;
            this.content = content;
            this.cursorOffset = cursorOffset;
            this.isLongPress = isLongPress;
        }
    }

    public interface OnFastTextEditClickListener {
        void onClick(EditAction editAction);
    }
}
