package com.example.ysh.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.ysh.myapplication.R;
import com.example.ysh.myapplication.view.CameraGLSurfaceView;

/**
 * Created by ysh on 2017/3/8.
 */

public class CameraGLSerfaceActivity extends AppCompatActivity {

    CameraGLSurfaceView mCameraGLSurfaceView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_glserface);

        mCameraGLSurfaceView = (CameraGLSurfaceView) findViewById(R.id.camera_preview);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mCameraGLSurfaceView.bringToFront();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mCameraGLSurfaceView.onPause();
    }
}
