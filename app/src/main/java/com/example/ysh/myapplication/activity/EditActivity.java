package com.example.ysh.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.ysh.myapplication.R;
import com.example.ysh.myapplication.view.FastTextEditBar;

/**
 * 类说明：
 *
 * @author yangsh
 * @version 1.0
 * @time 2017/2/16 18:07
 * Description:
 */

public class EditActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = EditActivity.class.getSimpleName();

    EditText mEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.symbol_1).setOnClickListener(this);
        findViewById(R.id.symbol_2).setOnClickListener(this);
        findViewById(R.id.symbol_3).setOnClickListener(this);
        findViewById(R.id.symbol_4).setOnClickListener(this);
        findViewById(R.id.symbol_5).setOnClickListener(this);
        findViewById(R.id.symbol_6).setOnClickListener(this);
        findViewById(R.id.symbol_7).setOnClickListener(this);
        findViewById(R.id.symbol_8).setOnClickListener(this);

        mEditText = (EditText) findViewById(R.id.edit_text);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged: s=" + s + ",start=" + start + ",after=" + after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged: s=" + s + ",start=" + start + ",before=" + before + ",count=" + count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged: s=" + s + ",start=");
                Log.d(TAG, "getSelectionStart: " + mEditText.getSelectionStart());
                Log.d(TAG, "getSelectionEnd  : " + mEditText.getSelectionEnd());
            }
        });

        FastTextEditBar fastTextEditBar = (FastTextEditBar) findViewById(R.id.tab_layout);
        fastTextEditBar.setFastTextEditClickListener(new FastTextEditBar.OnFastTextEditClickListener() {
            @Override
            public void onClick(FastTextEditBar.EditAction editAction) {
                Editable editable = mEditText.getText();
                int selectionStart = mEditText.getSelectionStart();

                if(editAction.cursorOffset != 0) {
                    int position = selectionStart + editAction.cursorOffset;
                    if (position <= editable.length()) {
                        mEditText.setSelection(position);
                    }
                }

                if(!TextUtils.isEmpty(editAction.content)) {
                    editable.insert(selectionStart, editAction.content);
                }


            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.symbol_1:
                mEditText.getEditableText().insert(mEditText.getSelectionStart(), "?");
                break;
            case R.id.symbol_2:
                mEditText.getEditableText().insert(mEditText.getSelectionStart(), "()");
                mEditText.setSelection(mEditText.getSelectionStart() -1);
                break;
            case R.id.symbol_3:
                mEditText.getEditableText().insert(mEditText.getSelectionStart(), "、");
                break;
            case R.id.symbol_4:
                mEditText.append("?");
                break;
            case R.id.symbol_5:
                mEditText.append("?");
                break;
            case R.id.symbol_6:
                mEditText.getEditableText().insert(mEditText.getSelectionStart(), "[]");
                break;
            case R.id.symbol_7:
                mEditText.setSelection(mEditText.getSelectionStart() - 1, mEditText.getSelectionStart());
                break;
            case R.id.symbol_8:
                mEditText.setSelection(mEditText.getSelectionStart() + 1);
                break;
            default:
                break;

        }
    }
}
