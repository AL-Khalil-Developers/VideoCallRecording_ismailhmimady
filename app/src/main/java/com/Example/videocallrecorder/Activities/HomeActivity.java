package com.Example.videocallrecorder.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.Example.videocallrecorder.Fragments.AudioActivity;
import com.Example.videocallrecorder.Fragments.ScreenshotsActivity;
import com.Example.videocallrecorder.Fragments.VideoActivity;
import com.Example.videocallrecorder.Main.activities.MainsplshActivity;
import com.Example.videocallrecorder.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAdListener;


public class HomeActivity extends AppCompatActivity {
    private Button btn_audio;
    private Button btn_enable;
    private Button btn_ss;
    private Button btn_video;
    private ImageView iv_back;


    public void onBackPressed() {
      startActivity(new Intent(HomeActivity.this, MainsplshActivity.class));
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.quore_home);


        RelativeLayout rel = (RelativeLayout) findViewById(R.id.banner);

        fb_baner(rel, HomeActivity.this);

        this.btn_video = (Button) findViewById(R.id.btn_video);
        this.btn_ss = (Button) findViewById(R.id.btn_ss);
        this.btn_audio = (Button) findViewById(R.id.btn_audio);
        this.btn_enable = (Button) findViewById(R.id.btn_enable);
        this.iv_back = (ImageView) findViewById(R.id.iv_back);
        this.iv_back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                HomeActivity.this.finish();
            }
        });
        this.btn_enable.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                HomeActivity.this.startActivity(new Intent(HomeActivity.this, MainActivity.class));
//                fb_interstal(HomeActivity.this);
            }
        });
        this.btn_video.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                HomeActivity.this.startActivity(new Intent(HomeActivity.this, VideoActivity.class));
                fb_interstal(HomeActivity.this);
            }
        });
        this.btn_ss.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                HomeActivity.this.startActivity(new Intent(HomeActivity.this, ScreenshotsActivity.class));
//                fb_interstal(HomeActivity.this);
            }
        });
        this.btn_audio.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                HomeActivity.this.startActivity(new Intent(HomeActivity.this, AudioActivity.class));
//                fb_interstal(HomeActivity.this);
            }
        });
    }

    protected void onResume() {

        super.onResume();
    }


    public void fb_interstal(final Context context) {


        // TODO Auto-generated method stub
        try {

            // // intertial ad code //////

            final com.facebook.ads.InterstitialAd interstitialAd = new com.facebook.ads.InterstitialAd(
                    context, getString(R.string.fb_inter));

            InterstitialAdListener interstitialAdListener =   new InterstitialAdListener() {
                @Override
                public void onInterstitialDisplayed(Ad ad) {

                }

                @Override
                public void onInterstitialDismissed(Ad ad) {

                }

                @Override
                public void onError(Ad ad, AdError adError) {



                }

                @Override
                public void onAdLoaded(Ad ad) {
                    interstitialAd.show();
                }

                @Override
                public void onAdClicked(Ad ad) {

                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            };
            interstitialAd.loadAd(
                    interstitialAd.buildLoadAdConfig()
                            .withAdListener(interstitialAdListener)
                            .build());
        } catch (Exception e) {

        }

    }


    public void fb_baner(final RelativeLayout ad_layout, final Context context) {


        com.facebook.ads.AdView adView = new com.facebook.ads.AdView(context, getString(R.string.fb_bnr),
                com.facebook.ads.AdSize.BANNER_HEIGHT_50);

        ad_layout.addView(adView);

        ad_layout.setVisibility(View.VISIBLE);

        adView.loadAd();



    }

}
