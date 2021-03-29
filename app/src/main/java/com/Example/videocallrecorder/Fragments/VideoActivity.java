package com.Example.videocallrecorder.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Example.videocallrecorder.Activities.HomeActivity;
import com.Example.videocallrecorder.Adapters.VideoAdapter;
import com.Example.videocallrecorder.Models.Video;
import com.Example.videocallrecorder.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;

public class VideoActivity extends AppCompatActivity {
    private Adapter adapter;
    private StringBuilder bundle1;
    private ImageView iv_back;
    private SimpleDateFormat j1;
    private float j2;
    private LinearLayoutManager layoutManager;
    private String path;
    private RecyclerView recyclerViewVideos;
    private TextView textnoaudio;

    private ArrayList<Video> getAllVideos() {
        ArrayList arrayList = new ArrayList();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
        stringBuilder.append(File.separator);
        stringBuilder.append("Recordings");
        this.path = stringBuilder.toString();
        File[] listFiles = new File(this.path).listFiles();
        if (listFiles != null) {
            for (int i = 0; i < listFiles.length; i++) {

                MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                mediaMetadataRetriever.setDataSource(getApplicationContext(), Uri.fromFile(listFiles[i]));

                if (mediaMetadataRetriever.extractMetadata(9)==null){

                }else {

                    Video video = new Video();
                    video.setName(listFiles[i].getName());
                    video.setThumbnail(Uri.fromFile(listFiles[i]));
                    video.setDuration(millsToDateFormat(Long.parseLong(mediaMetadataRetriever.extractMetadata(9))));

                    video.setSize(getStringSizeLengthFile(listFiles[i].length()));
                    mediaMetadataRetriever.release();
                    arrayList.add(video);
                }



            }
        }
        Collections.reverse(arrayList);
        return arrayList;
    }

    public String getStringSizeLengthFile(long j) {
        StringBuilder stringBuilder;
        String str;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        this.j2 = (float) j;
        if (this.j2 < 1.23312538E9f) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(decimalFormat.format((double) (this.j2 / 1024.0f)));
            str = " KB";
        } else if (this.j2 < 1.31701146E9f) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(decimalFormat.format((double) (this.j2 / 1.23312538E9f)));
            str = " MB";
        } else if (this.j2 >= 1.09951163E12f) {
            return "";
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append(decimalFormat.format((double) (this.j2 / 1.31701146E9f)));
            str = " GB";
        }
        stringBuilder.append(str);
        return stringBuilder.toString();
    }

    @SuppressLint("SimpleDateFormat")
    public String millsToDateFormat(long j) {
        Date date = new Date(j);
        this.j1 = new SimpleDateFormat("HH:mm:ss");
        this.j1.setTimeZone(TimeZone.getTimeZone("UTC"));
        return this.j1.format(date);
    }

    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        //Hawk.init(this).build();
        setContentView((int) R.layout.fragment_videos);


        RelativeLayout rel = (RelativeLayout) findViewById(R.id.banner);
        fb_baner(rel, VideoActivity.this);

        this.iv_back = (ImageView) findViewById(R.id.iv_back);
        this.iv_back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                VideoActivity.this.finish();
            }
        });
        this.recyclerViewVideos = (RecyclerView) findViewById(R.id.recycler_view_videos);
        this.layoutManager = new LinearLayoutManager(getApplicationContext());
        this.recyclerViewVideos.setLayoutManager(this.layoutManager);
        this.adapter = new VideoAdapter(getAllVideos(), this);
        this.recyclerViewVideos.setAdapter(this.adapter);
        this.textnoaudio = (TextView) findViewById(R.id.textnoaudio);
        this.bundle1 = new StringBuilder();
        this.bundle1.append("--------getallaudio");
        this.bundle1.append(getAllVideos().size());
        Log.e("tk", this.bundle1.toString());
        if (getAllVideos().size() == 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("--------getallaudio");
            stringBuilder.append(getAllVideos().size());
            Log.e("tk", stringBuilder.toString());
            this.textnoaudio.setVisibility(View.VISIBLE);
            this.recyclerViewVideos.setVisibility(View.GONE);
            return;
        }
        this.textnoaudio.setVisibility(View.GONE);
        this.recyclerViewVideos.setVisibility(View.VISIBLE);
    }

    public void onResume() {
        super.onResume();
        this.adapter.notifyDataSetChanged();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("--------getallaudio");
        stringBuilder.append(getAllVideos().size());
        Log.e("tk", stringBuilder.toString());
        if (getAllVideos().size() == 0) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("--------getallaudio");
            stringBuilder.append(getAllVideos().size());
            Log.e("tk", stringBuilder.toString());
            this.textnoaudio.setVisibility(View.VISIBLE);
            this.recyclerViewVideos.setVisibility(View.GONE);
            return;
        }
        this.textnoaudio.setVisibility(View.GONE);
        this.recyclerViewVideos.setVisibility(View.VISIBLE);
    }

    public void fb_baner(final RelativeLayout ad_layout, final Context context) {


        com.facebook.ads.AdView adView = new com.facebook.ads.AdView(context, getString(R.string.fb_bnr),
                com.facebook.ads.AdSize.BANNER_HEIGHT_50);

        ad_layout.addView(adView);

        ad_layout.setVisibility(View.VISIBLE);

        adView.loadAd();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(VideoActivity.this, HomeActivity.class));
    }
}
