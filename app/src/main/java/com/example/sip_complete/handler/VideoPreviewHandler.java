package com.example.sip_complete.handler;

import android.view.SurfaceHolder;

import com.example.sip_complete.ui.home.HomeActivity;

import org.pjsip.pjsua2.VideoPreviewOpParam;
import org.pjsip.pjsua2.VideoWindowHandle;

public class VideoPreviewHandler implements SurfaceHolder.Callback {
    public boolean videoPreviewActive = false;

    public void updateVideoPreview(SurfaceHolder holder) {
        if (HomeActivity.currentCall != null &&
                HomeActivity.currentCall.vidWin != null &&
                HomeActivity.currentCall.vidPrev != null) {
            if (videoPreviewActive) {
                VideoWindowHandle vidWH = new VideoWindowHandle();
                vidWH.getHandle().setWindow(holder.getSurface());
                VideoPreviewOpParam vidPrevParam = new VideoPreviewOpParam();
                vidPrevParam.setWindow(vidWH);
                try {
                    HomeActivity.currentCall.vidPrev.start(vidPrevParam);
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else {
                try {
                    HomeActivity.currentCall.vidPrev.stop();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        updateVideoPreview(holder);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        try {
            HomeActivity.currentCall.vidPrev.stop();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
