package com.Example.videocallrecorder.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import com.Example.videocallrecorder.Fragments.VideoActivity;
import com.Example.videocallrecorder.R;
import java.io.File;
import life.knowledge4.videotrimmer.K4LVideoTrimmer;
import life.knowledge4.videotrimmer.interfaces.OnTrimVideoListener;

public class VideoTrimActivity extends AppCompatActivity implements OnTrimVideoListener {
    private StringBuilder bundle1;
    private File file;
    private String path;
    private Intent uri1;
    private K4LVideoTrimmer videoTrimmer;
    private Uri videoUri;

    public void cancelAction() {
        this.videoTrimmer.destroy();
        finish();
    }

    public void getResult(Uri uri) {


        Log.e("filepath",""+file.getPath());
        Log.e("uripath",""+uri.getPath());

       if (file.getPath().equals(uri.getPath())){
           this.uri1 = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
           this.uri1.setData(Uri.fromFile(this.file));
           sendBroadcast(this.uri1);
           startActivity(new Intent(VideoTrimActivity.this, VideoActivity.class));
           return;
       }

        Log.e("trim video",""+uri);
        if (this.file.exists()) {
            this.file.delete();
            this.uri1 = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
            this.uri1.setData(Uri.fromFile(this.file));
            sendBroadcast(this.uri1);
            startActivity(new Intent(VideoTrimActivity.this, VideoActivity.class));
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
       // Hawk.init(this).build();
        setContentView((int) R.layout.quore_video_trim);
        if (getIntent().hasExtra("video")) {
            this.file = new File(getIntent().getStringExtra("video"));
            this.videoUri = Uri.fromFile(this.file);
            //Hawk.delete("video");
        }
        this.bundle1 = new StringBuilder();
        this.bundle1.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
        this.bundle1.append(File.separator);
        this.bundle1.append("Recordings/");
        this.path = this.bundle1.toString();
        this.videoTrimmer = (K4LVideoTrimmer) findViewById(R.id.timeline);
        if (this.videoTrimmer != null && this.videoUri != null) {
            this.videoTrimmer.setMaxDuration(100);
            this.videoTrimmer.setOnTrimVideoListener(this);
            this.videoTrimmer.setDestinationPath(this.path);
            this.videoTrimmer.setVideoURI(this.videoUri);
        }
    }
}
