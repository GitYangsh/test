package com.example.ysh.myapplication.view;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.EGL14;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.WindowManager;

import com.example.ysh.myapplication.encoder.MediaVideoEncoder;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by ysh on 2017/3/14.
 */

public class ShortVideoPreview extends GLSurfaceView {
    private static final boolean DEBUG = false; // TODO set false on release
    private static final String TAG = "CameraGLView";

    private static final int CAMERA_ID = 0;

    private static final int SCALE_STRETCH_FIT = 0;
    private ShortVideoRenderer mRenderer;
    protected boolean mHasSurface;
    private CameraHandler mCameraHandler = null;
    private int mVideoWidth, mVideoHeight;
    private int mRotation;
    private int mScaleMode = SCALE_STRETCH_FIT;

    public ShortVideoPreview(Context context) {
        super(context);
        init();
    }

    public ShortVideoPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mRenderer = new ShortVideoRenderer(this);
        setEGLContextClientVersion(2);    // GLES 2.0, API >= 8
        setRenderer(mRenderer);
        setRenderMode(RENDERMODE_CONTINUOUSLY);
//        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public void onResume() {
        if (DEBUG) Log.v(TAG, "onResume:");
        super.onResume();
        if (mHasSurface) {
            if (mCameraHandler == null) {
                if (DEBUG) Log.v(TAG, "surface already exist");
                startPreview(getWidth(), getHeight());
            }
        }
    }

    protected synchronized void startPreview(final int width, final int height) {
        if (mCameraHandler == null) {
            final CameraThread thread = new CameraThread(this);
            thread.start();
            mCameraHandler = thread.getHandler();
        }
        mCameraHandler.startPreview(1280, 720/*width, height*/);
    }

    @Override
    public void onPause() {
        if (DEBUG) Log.v(TAG, "onPause:");
        if (mCameraHandler != null) {
            // just request stop prviewing
            mCameraHandler.stopPreview(false);
        }
        super.onPause();
    }

    public void setScaleMode(final int mode) {
        if (mScaleMode != mode) {
            mScaleMode = mode;
            queueEvent(new Runnable() {
                @Override
                public void run() {
                    mRenderer.updateViewport();
                }
            });
        }
    }

    public int getScaleMode() {
        return mScaleMode;
    }

    public void setVideoSize(final int width, final int height) {
        if ((mRotation % 180) == 0) {
            mVideoWidth = width;
            mVideoHeight = height;
        } else {
            mVideoWidth = height;
            mVideoHeight = width;
        }
        queueEvent(new Runnable() {
            @Override
            public void run() {
                mRenderer.updateViewport();
            }
        });
    }

    public int getVideoWidth() {
        return mVideoWidth;
    }

    public int getVideoHeight() {
        return mVideoHeight;
    }

    public SurfaceTexture getSurfaceTexture() {
        if (DEBUG) Log.v(TAG, "getSurfaceTexture:");
        return mRenderer != null ? mRenderer.mSTexture : null;
    }

    @Override
    public void surfaceDestroyed(final SurfaceHolder holder) {
        if (DEBUG) Log.v(TAG, "surfaceDestroyed:");
        if (mCameraHandler != null) {
            // wait for finish previewing here
            // otherwise camera try to display on un-exist Surface and some error will occure
            mCameraHandler.stopPreview(true);
        }
        mCameraHandler = null;
        mHasSurface = false;
        mRenderer.onSurfaceDestroyed();
        super.surfaceDestroyed(holder);
    }

    public void setVideoEncoder(final MediaVideoEncoder encoder) {
        if (DEBUG) Log.v(TAG, "setVideoEncoder:tex_id=" + mRenderer.hTex + ",encoder=" + encoder);
        queueEvent(new Runnable() {
            @Override
            public void run() {
                synchronized (mRenderer) {
                    if (encoder != null) {
                        encoder.setEglContext(EGL14.eglGetCurrentContext(), mRenderer.hTex);
                    }
                    mRenderer.mVideoEncoder = encoder;
                }
            }
        });
    }


    /**
     * Handler class for asynchronous camera operation
     */
    private static final class CameraHandler extends Handler {
        private static final int MSG_PREVIEW_START = 1;
        private static final int MSG_PREVIEW_STOP = 2;
        private CameraThread mThread;

        public CameraHandler(final CameraThread thread) {
            mThread = thread;
        }

        public void startPreview(final int width, final int height) {
            sendMessage(obtainMessage(MSG_PREVIEW_START, width, height));
        }

        /**
         * request to stop camera preview
         *
         * @param needWait need to wait for stopping camera preview
         */
        public void stopPreview(final boolean needWait) {
            synchronized (this) {
                sendEmptyMessage(MSG_PREVIEW_STOP);
                if (needWait && mThread.mIsRunning) {
                    try {
                        if (DEBUG) Log.d(TAG, "wait for terminating of camera thread");
                        wait();
                    } catch (final InterruptedException e) {
                    }
                }
            }
        }

        /**
         * message handler for camera thread
         */
        @Override
        public void handleMessage(final Message msg) {
            switch (msg.what) {
                case MSG_PREVIEW_START:
                    mThread.startPreview(msg.arg1, msg.arg2);
                    break;
                case MSG_PREVIEW_STOP:
                    mThread.stopPreview();
                    synchronized (this) {
                        notifyAll();
                    }
                    Looper.myLooper().quit();
                    mThread = null;
                    break;
                default:
                    throw new RuntimeException("unknown message:what=" + msg.what);
            }
        }
    }

    /**
     * Thread for asynchronous operation of camera preview
     */
    private static final class CameraThread extends Thread {
        private final Object mReadyFence = new Object();
        private final WeakReference<ShortVideoPreview> mWeakParent;
        private CameraHandler mHandler;
        private volatile boolean mIsRunning = false;
        private Camera mCamera;
        private boolean mIsFrontFace;

        public CameraThread(final ShortVideoPreview parent) {
            super("Camera thread");
            mWeakParent = new WeakReference<ShortVideoPreview>(parent);
        }

        public CameraHandler getHandler() {
            synchronized (mReadyFence) {
                try {
                    mReadyFence.wait();
                } catch (final InterruptedException e) {
                }
            }
            return mHandler;
        }

        /**
         * message loop
         * prepare Looper and create Handler for this thread
         */
        @Override
        public void run() {
            if (DEBUG) Log.d(TAG, "Camera thread start");
            Looper.prepare();
            synchronized (mReadyFence) {
                mHandler = new CameraHandler(this);
                mIsRunning = true;
                mReadyFence.notify();
            }
            Looper.loop();
            if (DEBUG) Log.d(TAG, "Camera thread finish");
            synchronized (mReadyFence) {
                mHandler = null;
                mIsRunning = false;
            }
        }

        /**
         * start camera preview
         *
         * @param width
         * @param height
         */
        private final void startPreview(final int width, final int height) {
            if (DEBUG) Log.v(TAG, "startPreview:");
            final ShortVideoPreview parent = mWeakParent.get();
            if ((parent != null) && (mCamera == null)) {
                // This is a sample project so just use 0 as camera ID.
                // it is better to selecting camera is available
                try {
                    mCamera = Camera.open(CAMERA_ID);
                    final Camera.Parameters params = mCamera.getParameters();
                    final List<String> focusModes = params.getSupportedFocusModes();
                    if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
                        params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
                    } else if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
                        params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                    } else {
                        if (DEBUG) Log.i(TAG, "Camera does not support autofocus");
                    }
                    // let's try fastest frame rate. You will get near 60fps, but your device become hot.
                    final List<int[]> supportedFpsRange = params.getSupportedPreviewFpsRange();
//					final int n = supportedFpsRange != null ? supportedFpsRange.size() : 0;
//					int[] range;
//					for (int i = 0; i < n; i++) {
//						range = supportedFpsRange.get(i);
//						Log.i(TAG, String.format("supportedFpsRange(%d)=(%d,%d)", i, range[0], range[1]));
//					}
                    final int[] max_fps = supportedFpsRange.get(supportedFpsRange.size() - 1);
                    Log.i(TAG, String.format("fps:%d-%d", max_fps[0], max_fps[1]));
                    params.setPreviewFpsRange(max_fps[0], max_fps[1]);
                    params.setRecordingHint(true);
                    // request closest supported preview size
                    final Camera.Size closestSize = getClosestSupportedSize(
                            params.getSupportedPreviewSizes(), width, height);
                    params.setPreviewSize(closestSize.width, closestSize.height);
                    // request closest picture size for an aspect ratio issue on Nexus7
                    final Camera.Size pictureSize = getClosestSupportedSize(
                            params.getSupportedPictureSizes(), width, height);
                    params.setPictureSize(pictureSize.width, pictureSize.height);
                    // rotate camera preview according to the device orientation
                    setRotation(params);
                    mCamera.setParameters(params);
                    // get the actual preview size
                    final Camera.Size previewSize = mCamera.getParameters().getPreviewSize();
                    Log.i(TAG, String.format("previewSize(%d, %d)", previewSize.width, previewSize.height));
                    // adjust view size with keeping the aspect ration of camera preview.
                    // here is not a UI thread and we should request parent view to execute.
                    parent.post(new Runnable() {
                        @Override
                        public void run() {
                            parent.setVideoSize(previewSize.width, previewSize.height);
                        }
                    });
                    final SurfaceTexture st = parent.getSurfaceTexture();
                    st.setDefaultBufferSize(previewSize.width, previewSize.height);
                    mCamera.setPreviewTexture(st);
                } catch (final IOException e) {
                    Log.e(TAG, "startPreview:", e);
                    if (mCamera != null) {
                        mCamera.release();
                        mCamera = null;
                    }
                } catch (final RuntimeException e) {
                    Log.e(TAG, "startPreview:", e);
                    if (mCamera != null) {
                        mCamera.release();
                        mCamera = null;
                    }
                }
                if (mCamera != null) {
                    // start camera preview display
                    mCamera.startPreview();
                }
            }
        }

        private static Camera.Size getClosestSupportedSize(List<Camera.Size> supportedSizes, final int requestedWidth, final int requestedHeight) {
            return (Camera.Size) Collections.min(supportedSizes, new Comparator<Camera.Size>() {

                private int diff(final Camera.Size size) {
                    return Math.abs(requestedWidth - size.width) + Math.abs(requestedHeight - size.height);
                }

                @Override
                public int compare(final Camera.Size lhs, final Camera.Size rhs) {
                    return diff(lhs) - diff(rhs);
                }
            });

        }

        /**
         * stop camera preview
         */
        private void stopPreview() {
            if (DEBUG) Log.v(TAG, "stopPreview:");
            if (mCamera != null) {
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }
            final ShortVideoPreview parent = mWeakParent.get();
            if (parent == null) return;
            parent.mCameraHandler = null;
        }

        /**
         * rotate preview screen according to the device orientation
         *
         * @param params
         */
        private final void setRotation(final Camera.Parameters params) {
            if (DEBUG) Log.v(TAG, "setRotation:");
            final ShortVideoPreview parent = mWeakParent.get();
            if (parent == null) return;

            final Display display = ((WindowManager) parent.getContext()
                    .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            final int rotation = display.getRotation();
            int degrees = 0;
            switch (rotation) {
                case Surface.ROTATION_0:
                    degrees = 0;
                    break;
                case Surface.ROTATION_90:
                    degrees = 90;
                    break;
                case Surface.ROTATION_180:
                    degrees = 180;
                    break;
                case Surface.ROTATION_270:
                    degrees = 270;
                    break;
            }
            // get whether the camera is front camera or back camera
            final Camera.CameraInfo info =
                    new android.hardware.Camera.CameraInfo();
            android.hardware.Camera.getCameraInfo(CAMERA_ID, info);
            mIsFrontFace = (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT);
            if (mIsFrontFace) {    // front camera
                degrees = (info.orientation + degrees) % 360;
                degrees = (360 - degrees) % 360;  // reverse
            } else {  // back camera
                degrees = (info.orientation - degrees + 360) % 360;
            }
            // apply rotation setting
            mCamera.setDisplayOrientation(degrees);
            parent.mRotation = degrees;
            // XXX This method fails to call and camera stops working on some devices.
//			params.setRotation(degrees);
        }

    }
}
