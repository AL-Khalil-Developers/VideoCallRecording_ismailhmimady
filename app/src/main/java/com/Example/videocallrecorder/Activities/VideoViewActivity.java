package com.Example.videocallrecorder.Activities;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.Example.videocallrecorder.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;

public class VideoViewActivity extends AppCompatActivity {


    private static final String PLAYBACK_TIME = "play_time";
    private MediaController controller;
    private int currentPosition = 0;
    private ImageView iv_back;
    private ImageView iv_play;
    private Uri videoUri;
    private VideoView videoView;

    class C03671 implements OnCompletionListener {
        C03671() {
        }

        public void onCompletion(MediaPlayer mediaPlayer) {
            VideoViewActivity.this.videoView.seekTo(1);
            VideoViewActivity.this.controller.show();
        }
    }

    private void initializePlayer() {

        int i;
        getWindow().getDecorView().setSystemUiVisibility(4);
        this.videoUri = Uri.parse(( getIntent().getStringExtra("uri")));

        Log.e("videoUri", "" + videoUri);
        this.videoView.setVideoURI(this.videoUri);
        if (this.currentPosition > 0) {

            i = this.currentPosition;
        } else {

            i = 1;
        }
        videoView.seekTo(i);
        this.videoView.start();
        this.videoView.setOnCompletionListener(new C03671());
    }

    private void releasePlayer() {
        this.videoView.stopPlayback();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView((int) R.layout.quore_video_view);


        RelativeLayout rel = (RelativeLayout) findViewById(R.id.banner);
        fb_baner(rel, VideoViewActivity.this);


        this.videoView = (VideoView) findViewById(R.id.videoview);
        if (bundle != null) {
            this.currentPosition = bundle.getInt(PLAYBACK_TIME);
        }
        this.controller = new MediaController(this);
        this.controller.setMediaPlayer(this.videoView);
        this.videoView.setMediaController(this.controller);
        this.iv_back = (ImageView) findViewById(R.id.iv_back);
        this.iv_back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                VideoViewActivity.this.finish();
            }
        });
    }

    protected void onPause() {
        super.onPause();
        if (VERSION.SDK_INT < 24) {
            this.videoView.pause();
        }
    }

    protected void onResume() {
        super.onResume();
        getWindow().getDecorView().setSystemUiVisibility(4);
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(PLAYBACK_TIME, this.videoView.getCurrentPosition());
    }

    protected void onStart() {
        super.onStart();
       // Hawk.init(this).build();
        initializePlayer();
    }

    protected void onStop() {
        super.onStop();
        releasePlayer();
    }

    public void fb_baner(final RelativeLayout ad_layout, final Context context) {


        com.facebook.ads.AdView adView = new com.facebook.ads.AdView(context, getString(R.string.fb_bnr),
                com.facebook.ads.AdSize.BANNER_HEIGHT_50);

        ad_layout.addView(adView);

        ad_layout.setVisibility(View.VISIBLE);

        adView.loadAd();

    }

}
