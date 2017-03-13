package com.example.ysh.myapplication.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ysh.myapplication.R;
import com.example.ysh.myapplication.view.CameraGLSurfaceView;

/**
 * Created by ysh on 2017/3/8.
 */

public class CameraGLSurfaceActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    CameraGLSurfaceView mCameraGLSurfaceView;
    CountDownTimer mCountDownTimer;
    TextView mTimeTextView;
    CheckBox mVideoCheckBox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_glserface);

        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        mCameraGLSurfaceView = new CameraGLSurfaceView(this);
        preview.addView(mCameraGLSurfaceView);

        findViewById(R.id.camera).setOnClickListener(this);

        mVideoCheckBox = (CheckBox) findViewById(R.id.video);
        mVideoCheckBox.setOnCheckedChangeListener(this);
        mTimeTextView = (TextView) findViewById(R.id.video_time);
        mTimeTextView.setText("03:00");
        mCountDownTimer = new CountDownTimer(3000, 10) {

            public void onTick(long millisUntilFinished) {
                long first = millisUntilFinished / 10000;
                long second = millisUntilFinished % 10000 / 1000;
                long third = millisUntilFinished % 1000 / 100;
                long four = millisUntilFinished % 100/ 10;

                mTimeTextView.setText(first + "" + second + ":" + third + "" + four);
            }

            public void onFinish() {
                stopRecording();
                mTimeTextView.setText("00:00");
                mVideoCheckBox.setChecked(false);
                mVideoCheckBox.setEnabled(true);
                Toast.makeText(CameraGLSurfaceActivity.this, "video finish", Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera:
                mCameraGLSurfaceView.tackPicture();
                break;
            case R.id.video:
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.video:
                if (isChecked) {
                    buttonView.setEnabled(false);
                    mTimeTextView.setText("03:00");
                    mCountDownTimer.start();
                    startRecording();
                    Toast.makeText(CameraGLSurfaceActivity.this, "video start", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void startRecording(){
        mCameraGLSurfaceView.startRecording();
    }

    private void stopRecording(){
        mCameraGLSurfaceView.stopRecording();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mCameraGLSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        mCameraGLSurfaceView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
