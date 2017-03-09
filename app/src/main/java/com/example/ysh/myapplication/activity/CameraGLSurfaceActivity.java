package com.example.ysh.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.example.ysh.myapplication.R;
import com.example.ysh.myapplication.view.CameraGLSurfaceView;

/**
 * Created by ysh on 2017/3/8.
 */

public class CameraGLSurfaceActivity extends AppCompatActivity implements View.OnClickListener{

    CameraGLSurfaceView mCameraGLSurfaceView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_glserface);

        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        mCameraGLSurfaceView = new CameraGLSurfaceView(this);
        preview.addView(mCameraGLSurfaceView);

        findViewById(R.id.camera).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera:
                mCameraGLSurfaceView.tackPicture();
                break;
            default:
                break;
        }
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
