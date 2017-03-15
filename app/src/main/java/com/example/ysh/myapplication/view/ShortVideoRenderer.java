package com.example.ysh.myapplication.view;

import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.example.ysh.myapplication.encoder.GLDrawer2D;
import com.example.ysh.myapplication.encoder.MediaVideoEncoder;

import java.lang.ref.WeakReference;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by ysh on 2017/3/14.
 */

public class ShortVideoRenderer implements GLSurfaceView.Renderer, SurfaceTexture.OnFrameAvailableListener {
    private final WeakReference<ShortVideoPreview> mWeakParent;
    protected SurfaceTexture mSTexture;    // API >= 11
    protected int hTex;
    private GLDrawer2D mDrawer;
    private final float[] mStMatrix = new float[16];
    private final float[] mMvpMatrix = new float[16];
    protected MediaVideoEncoder mVideoEncoder;
    private volatile boolean requserUpdateTex = false;
    private boolean flip = true;

    public ShortVideoRenderer(final ShortVideoPreview parent) {
        mWeakParent = new WeakReference<ShortVideoPreview>(parent);
        Matrix.setIdentityM(mMvpMatrix, 0);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // This renderer required OES_EGL_image_external extension
        final String extensions = GLES20.glGetString(GLES20.GL_EXTENSIONS);    // API >= 8
//			if (DEBUG) Log.i(TAG, "onSurfaceCreated:Gl extensions: " + extensions);
        if (!extensions.contains("OES_EGL_image_external"))
            throw new RuntimeException("This system does not support OES_EGL_image_external.");
        // create textur ID
        hTex = GLDrawer2D.initTex();
        // create SurfaceTexture with texture ID.
        mSTexture = new SurfaceTexture(hTex);
        mSTexture.setOnFrameAvailableListener(this);
        // clear screen with yellow color so that you can see rendering rectangle
        GLES20.glClearColor(1.0f, 1.0f, 0.0f, 1.0f);
        final ShortVideoPreview parent = mWeakParent.get();
        if (parent != null) {
            parent.mHasSurface = true;
        }
        // create object for preview display
        mDrawer = new GLDrawer2D();
        mDrawer.setMatrix(mMvpMatrix, 0);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if ((width == 0) || (height == 0)) return;
        updateViewport();
        final ShortVideoPreview parent = mWeakParent.get();
        if (parent != null) {
            parent.startPreview(width, height);
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        if (requserUpdateTex) {
            requserUpdateTex = false;
            // update texture(came from camera)
            mSTexture.updateTexImage();
            // get texture matrix
            mSTexture.getTransformMatrix(mStMatrix);
        }
        // draw to preview screen
        if (mDrawer != null) {
            mDrawer.draw(hTex, mStMatrix);
        }
        flip = !flip;
        if (flip) {    // ~30fps
            synchronized (this) {
                if (mVideoEncoder != null) {
                    // notify to capturing thread that the camera frame is available.
//						mVideoEncoder.frameAvailableSoon(mStMatrix);
                    mVideoEncoder.frameAvailableSoon(mStMatrix, mMvpMatrix);
                }
            }
        }

    }

    /**
     * when GLSurface context is soon destroyed
     */
    public void onSurfaceDestroyed() {
        if (mDrawer != null) {
            mDrawer.release();
            mDrawer = null;
        }
        if (mSTexture != null) {
            mSTexture.release();
            mSTexture = null;
        }
        GLDrawer2D.deleteTex(hTex);
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        requserUpdateTex = true;
    }

    public final void updateViewport() {
        final ShortVideoPreview parent = mWeakParent.get();
        if (parent != null) {
            final int view_width = parent.getWidth();
            final int view_height = parent.getHeight();
            GLES20.glViewport(0, 0, view_width, view_height);
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
            if (mDrawer != null)
                mDrawer.setMatrix(mMvpMatrix, 0);
        }
    }
}
