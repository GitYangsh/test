package com.example.ysh.myapplication.activity;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.TextureView;

import com.example.ysh.myapplication.util.CameraUtil;

import java.io.IOException;

/**
 * Created by ysh on 2017/3/21.
 */

public class CameraTextureViewActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener{
    private static final String TAG = "CameraTextureView";
    private Camera mCamera;
    private TextureView mTextureView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTextureView = new TextureView(this);
        mTextureView.setSurfaceTextureListener(this);

        setContentView(mTextureView);
    }

    private void initCamera(int cameraId) {
        if (mCamera == null) {
            mCamera = CameraUtil.getCameraInstance(cameraId);
            CameraUtil.setCameraDisplayOrientation(this, cameraId, mCamera);
            CameraUtil.setCameraDisplaySize(mCamera);

            Camera.Parameters params = mCamera.getParameters();
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            mCamera.setParameters(params);
        }
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        Log.d(TAG, "onSurfaceTextureAvailable");
        initCamera(0);
        try {
            mCamera.setPreviewTexture(surface);
            mCamera.startPreview();
        } catch (IOException ioe) {
            // Something bad happened
        }
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        // Ignored, Camera does all the work for us
        Log.d(TAG, "onSurfaceTextureSizeChanged");
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        Log.d(TAG, "onSurfaceTextureDestroyed");
        mCamera.stopPreview();
        mCamera.release();
        return true;
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        // Invoked every time there's a new Camera preview frame
        Log.d(TAG, "onSurfaceTextureUpdated");
    }
}
