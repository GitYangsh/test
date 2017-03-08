package com.example.ysh.myapplication.view;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;

import com.example.ysh.myapplication.util.DirectDrawer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by ysh on 2017/3/8.
 */

public class CameraGLSurfaceView extends GLSurfaceView implements GLSurfaceView.Renderer, SurfaceTexture.OnFrameAvailableListener {
    private static final String TAG = "CameraGLSurfaceView";

    Context mContext;
    SurfaceTexture mSurfaceTexture;
    int mTextureID = -1;
    DirectDrawer mDirectDrawer;
    Camera mCamera;
    boolean isPreviewing = false;

    public CameraGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        setEGLContextClientVersion(2);
        setRenderer(this);
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.i(TAG, "onSurfaceCreated...");
        mTextureID = createTextureID();
        mSurfaceTexture = new SurfaceTexture(mTextureID);
        mSurfaceTexture.setOnFrameAvailableListener(this);
        mDirectDrawer = new DirectDrawer(mTextureID);
//        CameraInterface.getInstance().doOpenCamera(null);
        mCamera = getCameraInstance(0);
        setCameraDisplaySize(mCamera);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.i(TAG, "onSurfaceChanged...");
        GLES20.glViewport(0, 0, width, height);
//        if(!CameraInterface.getInstance().isPreviewing()){
//            CameraInterface.getInstance().doStartPreview(mSurface, 1.33f);
//        }
        if (!isPreviewing) {
            try {
                System.out.println("-----surfaceCreated");
                mCamera.setPreviewTexture(mSurfaceTexture);
                mCamera.startPreview();
                mCamera.autoFocus(new Camera.AutoFocusCallback() {
                    @Override
                    public void onAutoFocus(boolean success, Camera camera) {
                        Log.d("Camera", "autoFocus:" + success);
                    }
                });
            } catch (IOException e) {
                Log.d(TAG, "Error setting camera preview: " + e.getMessage());
            }
            isPreviewing = true;
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        Log.i(TAG, "onDrawFrame...");
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        mSurfaceTexture.updateTexImage();
        float[] mtx = new float[16];
        mSurfaceTexture.getTransformMatrix(mtx);
        mDirectDrawer.draw(mtx);
    }

    private int createTextureID() {
        int[] texture = new int[1];

        GLES20.glGenTextures(1, texture, 0);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, texture[0]);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);

        return texture[0];
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        Log.i(TAG, "onFrameAvailable...");
        this.requestRender();
    }

    public SurfaceTexture getSurfaceTexture() {
        return mSurfaceTexture;
    }

    /**
     * A safe way to get an instance of the Camera object.
     */
    public Camera getCameraInstance(int cameraId) {
        Camera c = null;
        try {
            c = Camera.open(cameraId); // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
            e.printStackTrace();
            Log.e("Camera", "error:" + e.getMessage());
        }
        return c; // returns null if camera is unavailable
    }

    private void setCameraDisplaySize(Camera camera) {
        List<Camera.Size> sizes = getSupportSizes(camera);
        if (sizes.size() == 0) {
            return;
        }
        Camera.Size selectSize = sizes.get(0);
        Camera.Parameters parameters = camera.getParameters();
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        parameters.setPreviewSize(selectSize.width, selectSize.height);
        parameters.setPictureSize(selectSize.width, selectSize.height);
        mCamera.setParameters(parameters);
    }

    private List<Camera.Size> getSupportSizes(Camera camera) {
        List<Camera.Size> sizes = new ArrayList<>();

        Camera.Parameters parameters = camera.getParameters();
        List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();
        List<Camera.Size> pictureSizes = parameters.getSupportedPictureSizes();
        for (int i = 0; i < previewSizes.size(); i++) {
            Camera.Size previewSize = previewSizes.get(i);
            for (int j = 0; j < pictureSizes.size(); j++) {
                if (previewSize.equals(pictureSizes.get(j))) {
                    sizes.add(previewSize);
                }
            }
        }
        return sizes;
    }

}
