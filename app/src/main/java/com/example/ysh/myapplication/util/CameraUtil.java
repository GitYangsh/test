package com.example.ysh.myapplication.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ysh on 2017/3/9.
 */

public class CameraUtil {

    /**
     * Check if this device has a camera
     */
    public static boolean checkCameraHardware(Context context) {
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
    public static Camera getCameraInstance(int cameraId) {
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

    public static void releaseCamera(Camera camera){
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    public static void setCameraDisplayOrientation(Activity activity, int cameraId, Camera camera) {
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

    public static void setCameraDisplaySize(Camera camera) {
        List<Camera.Size> sizes = getSupportSizes(camera);
        if (sizes.size() == 0) {
            return;
        }
        Camera.Size selectSize = sizes.get(0);
        Camera.Parameters parameters = camera.getParameters();
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        parameters.setPreviewSize(selectSize.width, selectSize.height);
        parameters.setPictureSize(selectSize.width, selectSize.height);
        camera.setParameters(parameters);
    }

    public static List<Camera.Size> getSupportSizes(Camera camera) {
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
