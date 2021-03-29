package com.Example.videocallrecorder.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
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

import com.Example.videocallrecorder.Activities.VideoViewActivity;
import com.Example.videocallrecorder.Adapters.AudioAdapter;
import com.Example.videocallrecorder.Models.Audio;
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

public class AudioActivity extends AppCompatActivity {
    private Adapter adapter;
    private StringBuilder bundle1;
    private ImageView iv_back;
    private SimpleDateFormat j1;
    private float j2;
    private LinearLayoutManager layoutManager;
    private String path;
    private RecyclerView recyclerViewAudio;
    private TextView textnoaudio;

    private ArrayList<Audio> getAllAudios() {
        ArrayList arrayList = new ArrayList();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
        stringBuilder.append(File.separator);
        stringBuilder.append("Audio");
        this.path = stringBuilder.toString();
        File[] listFiles = new File(this.path).listFiles();
        if (listFiles != null) {
            for (int i = 0; i < listFiles.length; i++) {
                Audio audio = new Audio();
                audio.setName(listFiles[i].getName());
                MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                mediaMetadataRetriever.setDataSource(getApplicationContext(), Uri.fromFile(listFiles[i]));
                audio.setDuration(millsToDateFormat(Long.parseLong(mediaMetadataRetriever.extractMetadata(9))));
                audio.setSize(getStringSizeLengthFile(listFiles[i].length()));
                mediaMetadataRetriever.release();
                arrayList.add(audio);
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
        setContentView((int) R.layout.fragment_audio);

        RelativeLayout rel = (RelativeLayout) findViewById(R.id.banner);
        fb_baner(rel, AudioActivity.this);

        this.iv_back = (ImageView) findViewById(R.id.iv_back);
        this.iv_back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AudioActivity.this.finish();
            }
        });
        this.recyclerViewAudio = (RecyclerView) findViewById(R.id.recycler_view_audio);
        this.textnoaudio = (TextView) findViewById(R.id.textnoaudio);
        this.bundle1 = new StringBuilder();
        this.bundle1.append("--------getallaudio");
        this.bundle1.append(getAllAudios().size());
        if (getAllAudios().size() == 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("--------getallaudio");
            stringBuilder.append(getAllAudios().size());
            Log.e("tk", stringBuilder.toString());
            this.textnoaudio.setVisibility(View.VISIBLE);
            this.recyclerViewAudio.setVisibility(View.GONE);
            return;
        }
        this.textnoaudio.setVisibility(View.GONE);
        this.recyclerViewAudio.setVisibility(View.VISIBLE);
    }


    public void fb_baner(final RelativeLayout ad_layout, final Context context) {


        com.facebook.ads.AdView adView = new com.facebook.ads.AdView(context, getString(R.string.fb_bnr),
                com.facebook.ads.AdSize.BANNER_HEIGHT_50);

        ad_layout.addView(adView);

        ad_layout.setVisibility(View.VISIBLE);

        adView.loadAd();

    }

    public void onResume() {
        super.onResume();
        this.layoutManager = new LinearLayoutManager(getApplicationContext());
        this.recyclerViewAudio.setLayoutManager(this.layoutManager);
        this.adapter = new AudioAdapter(getAllAudios(), this);
        this.recyclerViewAudio.setAdapter(this.adapter);
        if (getAllAudios().size() == 0) {
            this.textnoaudio.setVisibility(View.VISIBLE);
            this.recyclerViewAudio.setVisibility(View.GONE);
            return;
        }
        this.textnoaudio.setVisibility(View.GONE);
        this.recyclerViewAudio.setVisibility(View.VISIBLE);
    }
}
