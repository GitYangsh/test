package com.example.ysh.myapplication.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.example.ysh.myapplication.util.CameraUtil;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


/**
 * Created by ysh on 2017/3/8.
 */

public class CameraGLSurfaceView extends GLSurfaceView implements GLSurfaceView.Renderer, SurfaceTexture.OnFrameAvailableListener {
    private static final String TAG = "CameraGLSurfaceView";
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private Activity mActivity;
    private Camera mCamera;
    private int mCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
    private SurfaceTexture mSurfaceTexture;
    private int mWidth;
    private int mHeight;
    private int mTextureName;
    private Square mSquare;
    private boolean isTakePic;
    private boolean isStartRecording;
    private ByteBuffer mCaptureBuffer;
    private Bitmap mBitmap;

    public CameraGLSurfaceView(Activity activity) {
        super(activity);
        mActivity = activity;
        setEGLContextClientVersion(2);
        setRenderer(this);
        setRenderMode(RENDERMODE_WHEN_DIRTY);
        initCamera(mCameraId);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //设置背景的颜色
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);

        int[] textures = new int[1];
        GLES20.glGenTextures(1, textures, 0);
        mTextureName = textures[0];

        mSurfaceTexture = new SurfaceTexture(mTextureName);

        mSquare = new Square();

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        mWidth = width;
        mHeight = height;
        GLES20.glViewport(0, 0, width, height);
        mCaptureBuffer = ByteBuffer.allocate(mWidth * mHeight * 4);
        mBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);

        if (getHolder().getSurface() == null) {
            // preview surface does not exist
            return;
        }

        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        try {
            mCamera.setPreviewTexture(mSurfaceTexture);
            mSurfaceTexture.setOnFrameAvailableListener(this);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
//        System.out.println("onDrawFrame");
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        mSurfaceTexture.updateTexImage();
        mSquare.draw();

        if (isTakePic) {
            mCaptureBuffer.rewind();
            GLES20.glReadPixels(0, 0, mWidth, mHeight, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE,
                    mCaptureBuffer);
            isTakePic = false;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mCaptureBuffer.rewind();
                    mBitmap.copyPixelsFromBuffer(mCaptureBuffer);
                    mBitmap = adjustPhotoRotation1(mBitmap, 180);
                    try {
                        FileOutputStream fos = new FileOutputStream("/sdcard/screen.jpg");//注意app的sdcard读写权限问题
                        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);//压缩成png,100%显示效果
                        fos.flush();
                        fos.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        if (isStartRecording) {

        }

    }

    Bitmap adjustPhotoRotation1(Bitmap bm, final int orientationDegree) {
        Matrix m = new Matrix();
        m.setRotate(orientationDegree, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        try {
            Bitmap bm1 = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
            return bm1;
        } catch (OutOfMemoryError ex) {
        }
        return null;
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        requestRender();
    }

    @Override
    public void onResume() {
        super.onResume();
        initCamera(mCameraId);
    }

    @Override
    public void onPause() {
        super.onPause();
        CameraUtil.releaseCamera(mCamera);
    }

    public void tackPicture() {
        isTakePic = true;
    }

    public void startRecording() {
        isStartRecording = true;
    }

    public void stopRecording() {
        isStartRecording = false;
    }

    private void initCamera(int cameraId) {
        if (mCamera == null) {
            mCamera = CameraUtil.getCameraInstance(cameraId);
            CameraUtil.setCameraDisplayOrientation(mActivity, cameraId, mCamera);
            CameraUtil.setCameraDisplaySize(mCamera);

            Camera.Parameters params = mCamera.getParameters();
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            mCamera.setParameters(params);
        }
    }

    public class Square {
        private float shapeCoords[] = {
                -1.0f, 1.0f, 0.0f,   // top left
                -1.0f, -1.0f, 0.0f,   // bottom left
                1.0f, -1.0f, 0.0f,   // bottom right
                1.0f, 1.0f, 0.0f}; // top right
        //90 degree rotated
        private float textureCoords[] = {
                0.0f, 1.0f,   // top left
                1.0f, 1.0f,   // bottom left
                1.0f, 0.0f,    // bottom right
                0.0f, 0.0f}; // top right

        private final String vertexShaderCode =
                "attribute vec4 aPosition;\n" +
                        "attribute vec2 aTextureCoord;\n" +
                        "varying vec2 vTextureCoord;\n" +
                        "void main() {\n" +
                        "gl_Position = aPosition;\n" +
                        "vTextureCoord = aTextureCoord;\n" +
                        "}\n";
        private final String fragmentShaderCode =
                "#extension GL_OES_EGL_image_external : require\n" +
                        "precision mediump float;\n" +
                        "varying vec2 vTextureCoord;\n" +
                        "uniform samplerExternalOES sTexture;\n" +
                        "const vec3 monoMultiplier = vec3(0.299, 0.587, 0.114);\n" +
                        "void main() {\n" +
                        "vec4 color = texture2D(sTexture, vTextureCoord);\n" +
                        "float monoColor = dot(color.rgb,monoMultiplier);\n" +
                        "gl_FragColor = vec4(monoColor, monoColor, monoColor, 1.0);\n" +
                        "}\n";
        private final int COORDS_PER_VERTEX = 3;
        private final int TEXTURE_COORS_PER_VERTEX = 2;
        private short drawOrder[] = {0, 1, 2, 0, 2, 3};

        /*Vertex buffers*/
        private FloatBuffer mVertexBuffer;
        private FloatBuffer mTexCoordBuffer;
        private ShortBuffer mDrawListBuffer;
        private final int mProgram;

        public Square() {
            ByteBuffer bb = ByteBuffer.allocateDirect(4 * shapeCoords.length);
            bb.order(ByteOrder.nativeOrder());
            mVertexBuffer = bb.asFloatBuffer();
            mVertexBuffer.put(shapeCoords);
            mVertexBuffer.position(0);

            /*Vertex texture coord buffer*/
            ByteBuffer txeb = ByteBuffer.allocateDirect(4 * textureCoords.length);
            txeb.order(ByteOrder.nativeOrder());
            mTexCoordBuffer = txeb.asFloatBuffer();
            mTexCoordBuffer.put(textureCoords);
            mTexCoordBuffer.position(0);

		    /*Draw list buffer*/
            ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);
            dlb.order(ByteOrder.nativeOrder());
            mDrawListBuffer = dlb.asShortBuffer();
            mDrawListBuffer.put(drawOrder);
            mDrawListBuffer.position(0);


            //Load Shader source code (in string)
            int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER,
                    vertexShaderCode);
            int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER,
                    fragmentShaderCode);

            //Link Shader
            mProgram = GLES20.glCreateProgram();
            GLES20.glAttachShader(mProgram, vertexShader);
            GLES20.glAttachShader(mProgram, fragmentShader);
            GLES20.glLinkProgram(mProgram);
        }

        public int loadShader(int type, String shaderCode) {

            // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
            // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
            int shader = GLES20.glCreateShader(type);

            // add the source code to the shader and compile it
            GLES20.glShaderSource(shader, shaderCode);
            GLES20.glCompileShader(shader);

            return shader;
        }

        private void draw() {
            GLES20.glUseProgram(mProgram);

            int positionHandler = GLES20.glGetAttribLocation(mProgram, "aPosition");
            int texCoordHandler = GLES20.glGetAttribLocation(mProgram, "aTextureCoord");
            int textureHandler = GLES20.glGetUniformLocation(mProgram, "sTexture");

            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, mTextureName);

            GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

            GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

            GLES20.glEnableVertexAttribArray(positionHandler);
            GLES20.glVertexAttribPointer(positionHandler, COORDS_PER_VERTEX,
                    GLES20.GL_FLOAT, false, COORDS_PER_VERTEX * 4, mVertexBuffer);

            GLES20.glEnableVertexAttribArray(texCoordHandler);
            GLES20.glVertexAttribPointer(texCoordHandler, TEXTURE_COORS_PER_VERTEX,
                    GLES20.GL_FLOAT, false,
                    TEXTURE_COORS_PER_VERTEX * 4, mTexCoordBuffer);

            GLES20.glUniform1i(textureHandler, 0);
            GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length, GLES20.GL_UNSIGNED_SHORT, mDrawListBuffer);

            GLES20.glDisableVertexAttribArray(positionHandler);
            GLES20.glDisableVertexAttribArray(texCoordHandler);

        }
    }
}
