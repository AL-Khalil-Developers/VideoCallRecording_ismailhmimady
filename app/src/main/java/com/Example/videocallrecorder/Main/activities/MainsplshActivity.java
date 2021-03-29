package com.Example.videocallrecorder.Main.activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.Example.videocallrecorder.Activities.HomeActivity;
import com.Example.videocallrecorder.Activities.MainActivity;
import com.Example.videocallrecorder.R;
import com.Example.videocallrecorder.Utils.Glob;
import com.Example.videocallrecorder.pref.PreferencesUtils;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAdListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainsplshActivity extends AppCompatActivity implements OnClickListener {
    private static final int MY_REQUEST_CODE = 3;
    private static final int REQ_CODE_GALLERY_CAMERA = 124;
    private String TAG;
    ImageView k;
    ImageView l;

    ImageView m;
    ImageView n;
    boolean p = false;
    private PreferencesUtils pref;
    private ImageView share;
    private int t;

    private boolean addPermission(List<String> list, String str) {
        if (VERSION.SDK_INT >= 23 && checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
            list.add(str);
            if (!shouldShowRequestPermissionRationale(str)) {
                return false;
            }
        }
        return true;
    }

    private void checkMultiplePermissions() {
        if (VERSION.SDK_INT >= 23) {
            List arrayList = new ArrayList();
            List arrayList2 = new ArrayList();
            if (!addPermission(arrayList2, "android.permission.READ_EXTERNAL_STORAGE")) {
                arrayList.add("Read Storage");
            }
            if (!addPermission(arrayList2, "android.permission.WRITE_EXTERNAL_STORAGE")) {
                arrayList.add("Write Storage");
            }
            if (!addPermission(arrayList2, "android.permission.RECORD_AUDIO")) {
                arrayList.add("Record Audio");
            }
            if (arrayList2.size() > 0) {
                requestPermissions((String[]) arrayList2.toArray(new String[arrayList2.size()]), 111);
            }
        }
    }

    private void openGallery() {
        startActivity(new Intent(this, HomeActivity.class));
        fb_interstal(MainsplshActivity.this);
    }


    private void share() {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        intent.putExtra("android.intent.extra.TEXT", Glob.app_link + getPackageName());
        // intent.putExtra("android.intent.extra.STREAM", Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), BitmapFactory.decodeResource(getResources(), R.drawable.banner), null, null)));
        startActivity(Intent.createChooser(intent, "Share Application"));
    }

    public void gotoStore() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("market://details?id=");
        stringBuilder.append(getPackageName());
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder.toString())));
        } catch (ActivityNotFoundException unused) {
            Toast.makeText(this, "You don't have Google Play installed", Toast.LENGTH_LONG).show();
        }
    }

    public boolean isOnline() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 3) {
            setResult(-1);
            finish();
        }
    }

    public void onBackPressed() {
        if (!TextUtils.isEmpty(this.pref.getPrefString(PreferencesUtils.EXIT_JSON)) || isOnline()) {

            moveTaskToBack(true);
        } else if (this.p) {
            moveTaskToBack(true);
        } else {
            this.p = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    MainsplshActivity.this.p = false;
                }
            }, 2000);
        }
    }

    @SuppressLint("ShowToast")
    public void onClick(View view) {

        Toast makeText;
        int id = view.getId();
        if (id == R.id.More) {
            try {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(Glob.acc_link + getString(R.string.accountname))));
                return;
            } catch (ActivityNotFoundException unused) {
                makeText = Toast.makeText(this, "You don't have Google Play installed", Toast.LENGTH_LONG);
            }
        } else if (id != R.id.Start) {
            if (id == R.id.pravicy) {

                startActivity(new Intent(getApplicationContext(), WebActivity.class));

                return;
            } else if (id == R.id.rate1) {
                if (isOnline()) {
                    gotoStore();
                    return;
                }
                makeText = Toast.makeText(this, "Chek Your Internet Connection", Toast.LENGTH_SHORT);
            } else {
                return;
            }
        } else if (VERSION.SDK_INT < 23 || checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED) {
            openGallery();
            return;
        } else if (checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 3);
            return;
        } else {
            return;
        }
        makeText.show();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.quore_splashversion);

        this.pref = PreferencesUtils.getInstance(this);


        RelativeLayout rel = (RelativeLayout) findViewById(R.id.banner);
        fb_baner(rel, MainsplshActivity.this);


        this.k = (ImageView) findViewById(R.id.rate1);
        this.l = (ImageView) findViewById(R.id.pravicy);
        this.m = (ImageView) findViewById(R.id.Start);
        this.n = (ImageView) findViewById(R.id.More);
        this.share = (ImageView) findViewById(R.id.share);
        this.k.setOnClickListener(this);
        this.l.setOnClickListener(this);
        this.m.setOnClickListener(this);
        this.n.setOnClickListener(this);

        this.share.setOnClickListener(new OnClickListener() {

            public void onClick(View view) {

                MainsplshActivity.this.share();

            }
        });
        if (VERSION.SDK_INT >= 23) {
            checkMultiplePermissions();
        }

    }

    @TargetApi(23)
    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        int i2 = 0;
        if (i != 1) {
            if (i != REQ_CODE_GALLERY_CAMERA) {
                super.onRequestPermissionsResult(i, strArr, iArr);
                return;
            }
            Map hashMap = new HashMap();
            hashMap.put("android.permission.WRITE_EXTERNAL_STORAGE", Integer.valueOf(0));
            hashMap.put("android.permission.READ_EXTERNAL_STORAGE", Integer.valueOf(0));
            while (i2 < strArr.length) {
                hashMap.put(strArr[i2], Integer.valueOf(iArr[i2]));
                i2++;
            }
            if (!(((Integer) hashMap.get("android.permission.READ_EXTERNAL_STORAGE")).intValue() == 0 && ((Integer) hashMap.get("android.permission.WRITE_EXTERNAL_STORAGE")).intValue() == 0) && VERSION.SDK_INT >= 23) {
                Toast.makeText(getApplicationContext(), "My App cannot run without Storage Permissions.\nRelaunch My App or allow permissions in Applications Settings", Toast.LENGTH_LONG).show();
                finish();
            }
        } else if (iArr[0] == 0) {
            openGallery();
        } else {
            if (checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 1);
            }
        }
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

            InterstitialAdListener interstitialAdListener= new InterstitialAdListener() {
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
