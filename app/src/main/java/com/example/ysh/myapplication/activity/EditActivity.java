package com.example.ysh.myapplication.activity;

import android.graphics.Rect;
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

public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    AuthorEditText mAuthorEditText;
    FastEditBar mFastTextEditBar;
    boolean mIsFirst = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuthorEditText = (AuthorEditText) findViewById(R.id.edit_text);
        mAuthorEditText.setIndent(true);
        mFastTextEditBar = (FastEditBar) findViewById(R.id.tab_layout);
        mFastTextEditBar.setFastEditClickListener(mAuthorEditText);

        EditText editText = (EditText) findViewById(R.id.edit_text_title);
        editText.setText("白鸽");
        String chapter = "这个念头激起他五十四年来清白无疵的自尊心，使他豁然一惊。他忽然看见那些鸽子变成六百家嗷嗷待哺的客户，其中有几家是孤苦无依的老寡妇，靠亡夫留下的一点薄产，节衣缩食地活着：其中有一只鸽子是魏尔德小姐。羞恶之心，不禁油然而生。他回过头来，跑回公司；虽然他的心里还有一个声音在讥嘲他重投樊笼，为人役使，太不聪明；但是他的意念趋于坚定。他为那个喂鸽子的人祝福，因为那个人把他从噩梦中拯救出来，使他及时省悟。他要从头拾起那位爱玫瑰的人给予他的爱，他得到一个新生的机会。此时，那个喂鸽子的人还在公园里；陶柏蒙茫然地环视四周，回过头来，看见一只肥美的鸽子正在他掌中吃得高兴；……\n\u3000\u3000";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 50; i++) {
            sb.append(chapter);
        }
        mAuthorEditText.append(sb.toString());
        mAuthorEditText.requestFocus();
        mAuthorEditText.setSelection(mAuthorEditText.getText().length());
        mAuthorEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mIsFirst) {
                    mIsFirst = true;
                }
            }
        });

        getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (!mIsFirst) {
                    return;
                }
                //获取View可见区域的bottom
                Rect rect = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                if(bottom!=0 && oldBottom!=0 && bottom - rect.bottom <= 0){
                    if (mFastTextEditBar.getVisibility() == View.VISIBLE) {
                        mFastTextEditBar.post(new Runnable() {
                            @Override
                            public void run() {
                                mFastTextEditBar.setVisibility(View.GONE);
                            }
                        });
                    }
                }else {
                    if (mFastTextEditBar.getVisibility() == View.GONE) {
                        mFastTextEditBar.post(new Runnable() {
                            @Override
                            public void run() {
                                mFastTextEditBar.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                }
            }
        });

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
