package com.example.ysh.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.daimajia.numberprogressbar.OnProgressBarListener;
import com.example.ysh.myapplication.R;
import com.example.ysh.myapplication.view.CustomProgressBar;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 类说明：
 *
 * @author yangsh
 * @version 1.0
 * @time 2017/2/16 14:51
 * Description:
 */

public class NumberProgressBarActivity extends AppCompatActivity implements OnProgressBarListener {

    private Timer timer;
    private NumberProgressBar bnp;
    private CustomProgressBar bcp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_progress_bar);

        bnp = (NumberProgressBar)findViewById(R.id.numberbar1);
        bcp = (CustomProgressBar)findViewById(R.id.numberbar2);
        bnp.setOnProgressBarListener(this);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bnp.incrementProgressBy(1);
                        bcp.incrementProgressBy(1);
                    }
                });
            }
        }, 1000, 100);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    public void onProgressChange(int current, int max) {
        if(current == max) {
            Toast.makeText(getApplicationContext(), getString(R.string.finish), Toast.LENGTH_SHORT).show();
            bnp.setProgress(0);
        }
    }
}
