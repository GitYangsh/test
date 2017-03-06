package com.example.ysh.myapplication.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.View;
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

public class CameraActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "CameraActivity";

    TextView mCameraInfoTv;
    Camera mCamera;
    CameraPreview mPreview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        findViewById(R.id.camera_check_btn).setOnClickListener(this);
        findViewById(R.id.camera_open_btn).setOnClickListener(this);
        findViewById(R.id.camera_release_btn).setOnClickListener(this);

        mCameraInfoTv = (TextView) findViewById(R.id.camera_info_tv);

        if (checkCameraHardware(this)) {
            mCamera = getCameraInstance();
            if (mCamera == null) {
                Log.e("Camera", "mCamera is null");
                return;
            }
            mPreview = new CameraPreview(this, mCamera);
            FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
            preview.addView(mPreview);

            setCameraDisplayOrientation(this, 0, mCamera);
            setCameraDisplaySize(mCamera);
        } else {
            Toast.makeText(this, "no camera on this device", Toast.LENGTH_SHORT).show();
        }

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

    /**
     * A safe way to get an instance of the Camera object.
     */
    public Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
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

    public void setCameraDisplayOrientation(Activity activity, int cameraId, Camera camera) {
        if (camera == null) {
            return;
        }
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
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        Camera.Parameters parameters = camera.getParameters();
        parameters.setRotation(result);
        camera.setParameters(parameters);
        camera.setDisplayOrientation(result);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera_check_btn:
                StringBuilder sb = new StringBuilder();
                sb.append("相机状态\n");
                sb.append("是否可用：" + checkCameraHardware(this) + "\n");
                sb.append("相机个数：" + Camera.getNumberOfCameras() + "\n");
                mCameraInfoTv.setText(sb.toString());
                break;
            case R.id.camera_open_btn:
                mCamera.takePicture(null, null, mPicture);
                break;
            case R.id.camera_release_btn:
                break;
            default:
                break;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        setCameraDisplayOrientation(this, 0, mCamera);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    private void printCameraSize(List<Camera.Size> sizes) {
        for (Camera.Size size : sizes) {
            System.out.println(size.width + " x " + size.height);
        }
    }

    private void printCameraSize(Camera.Size size) {
        System.out.println(size.width + " x " + size.height);
    }

    private Camera.Size selectCameraSize(List<Camera.Size> previewSizes, List<Camera.Size> pictureSizes) {
        for (int i = 0; i < previewSizes.size(); i++) {
            Camera.Size previewSize = previewSizes.get(i);
            for (int j = 0; j < pictureSizes.size(); j++) {
                if (previewSize.equals(pictureSizes.get(j))) {
                    printCameraSize(previewSize);
                    return previewSize;
                }
            }
        }
        return null;
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
        }
    };

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

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
