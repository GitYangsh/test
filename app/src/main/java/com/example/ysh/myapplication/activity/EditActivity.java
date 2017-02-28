package com.example.ysh.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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

public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    AuthorEditText mAuthorEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuthorEditText = (AuthorEditText) findViewById(R.id.edit_text);
        mAuthorEditText.setIndent(true);
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
            default:
                break;

        }
    }
}
