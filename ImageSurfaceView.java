package com.example.asuslaptop.medcare;

import android.annotation.TargetApi;
import android.app.Activity;
import android.hardware.Camera;
import android.content.Context;
import android.hardware.camera2.CameraCharacteristics;
import android.os.Build;
import android.util.SparseIntArray;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.IOException;

public class ImageSurfaceView extends SurfaceView implements SurfaceHolder.Callback {


    //private static final int ORIENTATION_0 = 0;
    //private static final int ORIENTATION_90 = 3;
    //private static final int ORIENTATION_270 = 1;

    private Camera camera;
    private SurfaceHolder surfaceHolder;



    public ImageSurfaceView(Context context, Camera camera) {
        super(context);
        this.camera = camera;
        this.surfaceHolder = getHolder();
        this.surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        Camera.Parameters parameters = camera.getParameters();
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        parameters.setJpegQuality(100);

        Display display = ((WindowManager) this.getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        if(display.getRotation() == Surface.ROTATION_0)
        {
            //parameters.setPreviewSize(height, width);
            camera.setDisplayOrientation(90);
        }

        if(display.getRotation() == Surface.ROTATION_90)
        {
            camera.setDisplayOrientation(0);
        }

        if(display.getRotation() == Surface.ROTATION_180)
        {
            //landscape vertical to left landscape
            // parameters.setPreviewSize(height, width);
        }

        if(display.getRotation() == Surface.ROTATION_270)
        {
            //landscape vertical to right landscape
            //parameters.setPreviewSize(width, height);
            camera.setDisplayOrientation(180);
        }

        try {
            this.camera.setPreviewDisplay(holder);
            camera.setParameters(parameters);

            //System.out.println("Camera autofocused");

            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        this.camera.stopPreview();
        this.camera.release();
    }
}