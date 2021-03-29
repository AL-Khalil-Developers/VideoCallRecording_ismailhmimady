package com.Example.videocallrecorder.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.Example.videocallrecorder.R;
import com.Example.videocallrecorder.Utils.Glob;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import java.io.File;

public class ImageViewActivity extends AppCompatActivity {
    private String i1;
    private ImageView iv_back;
    private ImageView iv_image;
    private ImageView iv_share;

    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.image_layout);

        RelativeLayout rel = (RelativeLayout) findViewById(R.id.banner);

        fb_baner(rel, ImageViewActivity.this);


        this.iv_image = (ImageView) findViewById(R.id.iv_image);
        this.iv_share = (ImageView) findViewById(R.id.iv_share);
        this.i1 = getIntent().getStringExtra("img");
        this.iv_image.setImageURI(Uri.parse(this.i1));
        this.iv_share.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("image/*");
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(Glob.app_name);
                stringBuilder.append(" Create By : ");
                stringBuilder.append(Glob.acc_link+getString(R.string.accountname));
                intent.putExtra("android.intent.extra.TEXT", stringBuilder.toString());
                intent.putExtra("android.intent.extra.STREAM", FileProvider.getUriForFile(ImageViewActivity.this, getPackageName()+".provider", new File(ImageViewActivity.this.i1)));
                ImageViewActivity.this.startActivity(Intent.createChooser(intent, "Share Image using"));
            }
        });
        this.iv_back = (ImageView) findViewById(R.id.iv_back);
        this.iv_back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ImageViewActivity.this.finish();
            }
        });
    }


    public void fb_baner(final RelativeLayout ad_layout, final Context context) {


        com.facebook.ads.AdView adView = new com.facebook.ads.AdView(context, getString(R.string.fb_bnr),
                com.facebook.ads.AdSize.BANNER_HEIGHT_50);

        ad_layout.addView(adView);

        ad_layout.setVisibility(View.VISIBLE);

        adView.loadAd();

    }
}
