package com.Example.videocallrecorder.Fragments;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.Example.videocallrecorder.Adapters.ImageAdapter;
import com.Example.videocallrecorder.Models.Image;
import com.Example.videocallrecorder.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;


import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class ScreenshotsActivity extends AppCompatActivity {
    private Adapter adapter;
    private StringBuilder bundle1;
    private ImageView iv_back;
    private StaggeredGridLayoutManager layoutManager;
    private String path;
    private RecyclerView recyclerViewAudio;
    private TextView textnoaudio;

    private ArrayList<Image> getAllImages() {
        ArrayList arrayList = new ArrayList();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
        stringBuilder.append(File.separator);
        stringBuilder.append("Screens");
        this.path = stringBuilder.toString();
        File[] listFiles = new File(this.path).listFiles();
        if (listFiles != null) {
            for (int i = 0; i < listFiles.length; i++) {
                Image image = new Image();
                image.setName(listFiles[i].getName());
                image.setBitmap(BitmapFactory.decodeFile(listFiles[i].getAbsolutePath()));
                arrayList.add(image);
            }
        }
        Collections.reverse(arrayList);
        return arrayList;
    }

    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.fragment_screenshots);

        RelativeLayout rel = (RelativeLayout) findViewById(R.id.banner);
        fb_baner(rel, ScreenshotsActivity.this);

        this.iv_back = (ImageView) findViewById(R.id.iv_back);
        this.iv_back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ScreenshotsActivity.this.finish();
            }
        });
        this.recyclerViewAudio = (RecyclerView) findViewById(R.id.recycler_view_images);
        this.textnoaudio = (TextView) findViewById(R.id.textnoaudio);
        this.bundle1 = new StringBuilder();
        this.bundle1.append("--------getallaudio");
        this.bundle1.append(getAllImages().size());
        if (getAllImages().size() == 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("--------getallaudio");
            stringBuilder.append(getAllImages().size());
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
        this.layoutManager = new StaggeredGridLayoutManager(3, 1);
        this.recyclerViewAudio.setLayoutManager(this.layoutManager);
        this.adapter = new ImageAdapter(getAllImages(), this);
        this.recyclerViewAudio.setAdapter(this.adapter);
        if (getAllImages().size() == 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("--------getallaudio");
            stringBuilder.append(getAllImages().size());
            Log.e("tk", stringBuilder.toString());
            this.textnoaudio.setVisibility(View.VISIBLE);
            this.recyclerViewAudio.setVisibility(View.GONE);
            return;
        }
        this.textnoaudio.setVisibility(View.GONE);
        this.recyclerViewAudio.setVisibility(View.VISIBLE);
    }
}
