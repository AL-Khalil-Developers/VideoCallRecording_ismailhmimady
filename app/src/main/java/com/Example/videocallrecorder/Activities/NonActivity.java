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
import android.util.Log;

import com.Example.videocallrecorder.Services.RecordingService;
import com.Example.videocallrecorder.Services.RecordingService.RecordBinder;

public class NonActivity extends Activity {
    private static final int RECORD_REQUEST_CODE = 101;
    private DisplayMetrics componentName1;
    private ServiceConnection connection = new C03651();
    private MediaProjection mediaProjection;
    private MediaProjectionManager projectionManager;
    private RecordingService recordingService;

    class C03651 implements ServiceConnection {
        C03651() {
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            NonActivity.this.componentName1 = new DisplayMetrics();
            NonActivity.this.getWindowManager().getDefaultDisplay().getMetrics(NonActivity.this.componentName1);
            NonActivity.this.recordingService = ((RecordBinder) iBinder).getRecordingService();
            NonActivity.this.recordingService.setConfig(NonActivity.this.componentName1.widthPixels, NonActivity.this.componentName1.heightPixels, NonActivity.this.componentName1.densityDpi);
        }

        public void onServiceDisconnected(ComponentName componentName) {
        }
    }

    @RequiresApi(api = 21)
    @Override
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 101) {

            Log.e("startRecord", "before");
            this.mediaProjection = this.projectionManager.getMediaProjection(i2, intent);
            this.recordingService.setMediaProject(this.mediaProjection);
            this.recordingService.startRecord();

            Log.e("startRecord", "start");
            finish();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);


        if (VERSION.SDK_INT >= 21) {
            Log.e("nikhere", "start");
            this.projectionManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
            startActivityForResult(this.projectionManager.createScreenCaptureIntent(), 101);
        }
        Log.e("nikhere", "start2");
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
