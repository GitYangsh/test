package com.example.materialhome.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * 类说明：
 *
 * @author yangsh
 * @version 1.0
 * @time 2016/11/14 23:12
 * Description:
 */

public class BaseActivity extends AppCompatActivity {
    protected Toolbar mToolbar;

    private Toolbar getToolbar() {
        return mToolbar;
    }


}
