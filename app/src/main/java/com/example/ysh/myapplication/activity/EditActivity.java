package com.example.ysh.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.ysh.myapplication.R;
import com.example.ysh.myapplication.view.AuthorEditText;
import com.example.ysh.myapplication.view.FastEditBar;

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
    private static final String SYMBOL_INDENT = "\u3000\u3000";
    EditText mEditText;
    AuthorEditText mAuthorEditText;

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

        mAuthorEditText = (AuthorEditText) findViewById(R.id.edit_text);
        mAuthorEditText.setDefaultText(SYMBOL_INDENT);
        FastEditBar fastTextEditBar = (FastEditBar) findViewById(R.id.tab_layout);
        fastTextEditBar.setFastEditClickListener(mAuthorEditText);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_activity_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                break;
            case R.id.action_undo:
                mAuthorEditText.undo();
                break;
            case R.id.action_redo:
                mAuthorEditText.redo();
                break;
            default:
                break;
        }
        return true;
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
