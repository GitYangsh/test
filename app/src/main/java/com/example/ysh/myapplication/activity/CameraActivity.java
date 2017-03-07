package com.example.ysh.myapplication.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ysh.myapplication.R;
import com.example.ysh.myapplication.view.CameraPreview;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 类说明：
 *
 * @author yangsh
 * @version 1.0
 *          CreateTime:  2017/3/3 10:17
 *          Description:
 */

public class CameraActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "CameraActivity";
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private static final int[] FLASH_OPTIONS = {

    };

    private static final int[] FLASH_ICONS = {
            R.drawable.ic_flash_auto,
            R.drawable.ic_flash_off,
            R.drawable.ic_flash_on,
    };

    private static final int[] FLASH_TITLES = {
            R.string.flash_auto,
            R.string.flash_off,
            R.string.flash_on,
    };

    Camera mCamera;
    CameraPreview mPreview;
    MediaRecorder mRecorder;
    CountDownTimer mCountDownTimer;
    TextView mTimeTextView;
    CheckBox mVideoCheckBox;
    FrameLayout mCameraView;
    int mCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        findViewById(R.id.camera).setOnClickListener(this);
        mVideoCheckBox = (CheckBox) findViewById(R.id.video);
        mVideoCheckBox.setOnCheckedChangeListener(this);
        mTimeTextView = (TextView) findViewById(R.id.video_time);
        mCameraView = (FrameLayout) findViewById(R.id.camera_preview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }

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
                Toast.makeText(CameraActivity.this, "video finish", Toast.LENGTH_SHORT).show();
            }
        };
    }

    /**
     * Check if this device has a camera
     */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    private void initCamera(int cameraId) {
        mCamera = getCameraInstance(cameraId);
        if (mCamera == null) {
            Log.e("Camera", "mCamera is null");
            return;
        }
        mPreview = new CameraPreview(this, mCamera);
        mCameraView.addView(mPreview);

        setCameraDisplayOrientation(this, cameraId, mCamera);
        setCameraDisplaySize(mCamera);
    }

    private void releaseCamera(){
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
        if (mPreview != null) {
            mCameraView.removeView(mPreview);
            mPreview = null;
        }
    }

    private void releaseMediaRecorder(){
        if (mRecorder != null) {
            mRecorder.reset();
            mRecorder.release();
            mRecorder = null;
            mCamera.lock();
        }
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
                    printCameraSize(previewSize);
                }
            }
        }
        return sizes;
    }

    public void setCameraDisplayOrientation(Activity activity, int cameraId, Camera camera) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
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
        int result;
        int cameraRotation;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            cameraRotation = result;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
            cameraRotation = result;
        }

        camera.setDisplayOrientation(result);
        Camera.Parameters parameters = camera.getParameters();
        parameters.setRotation(cameraRotation);
        camera.setParameters(parameters);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera:
                mCamera.takePicture(null, null, mPicture);
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
                    Toast.makeText(CameraActivity.this, "video start", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mCamera.unlock();
        mRecorder.setCamera(mCamera);
        mRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_720P));

        mRecorder.setOutputFile(getOutputMediaFile(MEDIA_TYPE_VIDEO).getPath());
        mRecorder.setPreviewDisplay(mPreview.getHolder().getSurface());
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            releaseMediaRecorder();
            Log.e(TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.reset();
        mRecorder.release();
        mRecorder = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkCameraHardware(this)) {
            initCamera(mCameraId);
        } else {
            Toast.makeText(this, "no camera on this device", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaRecorder();
        releaseCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaRecorder();
        releaseCamera();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.camera, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aspect_ratio:
                if (mCameraView != null) {
//                    final Set<AspectRatio> ratios = mCameraView.getSupportedAspectRatios();
//                    final AspectRatio currentRatio = mCameraView.getAspectRatio();
//                    AspectRatioFragment.newInstance(ratios, currentRatio)
//                            .show(getSupportFragmentManager(), FRAGMENT_DIALOG);
                }
                break;
            case R.id.switch_flash:
                if (mCameraView != null) {
//                    mCurrentFlash = (mCurrentFlash + 1) % FLASH_OPTIONS.length;
//                    item.setTitle(FLASH_TITLES[mCurrentFlash]);
//                    item.setIcon(FLASH_ICONS[mCurrentFlash]);
//                    mCameraView.setFlash(FLASH_OPTIONS[mCurrentFlash]);
                }
                break;
            case R.id.switch_camera:
                if (mCameraView != null) {
                    Camera.CameraInfo info = new Camera.CameraInfo();
                    Camera.getCameraInfo(mCameraId, info);
                    releaseMediaRecorder();
                    releaseCamera();
                    if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                        mCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
                        initCamera(mCameraId);
                    } else {
                        mCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
                        initCamera(mCameraId);
                    }
                }
                break;
        }
        return false;
    }

    private void printCameraSize(List<Camera.Size> sizes) {
        for (Camera.Size size : sizes) {
            System.out.println(size.width + " x " + size.height);
        }
    }

    private void printCameraSize(Camera.Size size) {
        System.out.println(size.width + " x " + size.height);
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null) {
                Log.d(TAG, "Error creating media file, check storage permissions: ");
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error accessing file: " + e.getMessage());
            }
            Toast.makeText(CameraActivity.this, "take picture sucess", Toast.LENGTH_SHORT).show();
            camera.startPreview();
            camera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                    Log.d("Camera", "autoFocus:" + success);
                }
            });
        }
    };

    /**
     * Create a file Uri for saving an image or video
     */
    private static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }
}
