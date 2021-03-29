package com.Example.videocallrecorder.Activities;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.util.DisplayMetrics;
import com.Example.videocallrecorder.Services.RecordingService;
import com.Example.videocallrecorder.Services.RecordingService.RecordBinder;

public class NonScreenshotActivity extends Activity {
    private static final int RECORD_REQUEST_CODE = 101;
    private ServiceConnection connection = new C03661();
    private MediaProjection mediaProjection;
    private MediaProjectionManager projectionManager;
    private RecordingService recordingService;

    class C03661 implements ServiceConnection {
        C03661() {
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            NonScreenshotActivity.this.getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
            NonScreenshotActivity.this.recordingService = ((RecordBinder) iBinder).getRecordingService();
        }

        public void onServiceDisconnected(ComponentName componentName) {
        }
    }

    @RequiresApi(api = 21)
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 101 && i2 == -1) {
            this.mediaProjection = this.projectionManager.getMediaProjection(i2, intent);
            this.recordingService.setScreenShotMediaProject(this.mediaProjection);
            finish();
        }
    }

    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        if (VERSION.SDK_INT >= 21) {
            this.projectionManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
            startActivityForResult(this.projectionManager.createScreenCaptureIntent(), 101);
        }
        bindService(new Intent(this, RecordingService.class), this.connection, BIND_AUTO_CREATE);
    }

    protected void onDestroy() {
        super.onDestroy();
        unbindService(this.connection);
    }

    protected void onStart() {
        super.onStart();
    }
}
