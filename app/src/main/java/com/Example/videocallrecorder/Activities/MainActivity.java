package com.Example.videocallrecorder.Activities;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;

import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Example.videocallrecorder.R;
import com.Example.videocallrecorder.Services.RecordingService;
import com.Example.videocallrecorder.Main.Global.Globals;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;


public class MainActivity extends AppCompatActivity {
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;
    private static final int REQUEST_PERMISSIONS = 10;
    public static LabeledSwitch switchServiceStatus;
    private ImageView iv_back;
    boolean k;
    private Intent toggleableView1;
    private TextView txtServiceStatus;
    private Intent view1;

    class C03623 implements OnClickListener {
        C03623() {
        }

        public void onClick(View view) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.RECORD_AUDIO"}, 10);
        }
    }

    class C03634 implements OnClickListener {
        C03634() {
        }

        public void onClick(View view) {
            MainActivity.this.view1 = new Intent();
            MainActivity.this.view1.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            MainActivity.this.view1.addCategory("android.intent.category.DEFAULT");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("package:");
            stringBuilder.append(MainActivity.this.getPackageName());
            MainActivity.this.view1.setData(Uri.parse(stringBuilder.toString()));
            MainActivity.this.view1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MainActivity.this.view1.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            MainActivity.this.view1.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            MainActivity.this.startActivity(MainActivity.this.view1);
        }
    }

    class C12702 implements OnToggledListener {
        C12702() {
        }

        public void onSwitched(ToggleableView toggleableView, boolean z) {
            MainActivity.this.toggleableView1 = new Intent(MainActivity.this, RecordingService.class);
            Globals.setPrefbuttonBoolean(MainActivity.this, "On_Off", z);
            if (z) {
                MainActivity.this.txtServiceStatus.setText(MainActivity.this.getResources().getString(R.string.txt_service_status_off));
                MainActivity.this.startService(MainActivity.this.toggleableView1);
                return;
            }
            MainActivity.this.txtServiceStatus.setText(MainActivity.this.getResources().getString(R.string.txt_service_status_on));
            MainActivity.this.stopService(MainActivity.this.toggleableView1);
        }
    }

    private void initializeView() {
        this.txtServiceStatus = (TextView) findViewById(R.id.txt_service_status);
        switchServiceStatus = (LabeledSwitch) findViewById(R.id.switch_service_status);
        switchServiceStatus.setVisibility(View.VISIBLE);
        if (this.k) {
            switchServiceStatus.setOn(true);
        } else {
            switchServiceStatus.setOn(false);
        }
        if (checkServiceRunning(RecordingService.class)) {
            switchServiceStatus.setOn(true);
        }
        switchServiceStatus.setOnToggledListener(new C12702());
    }



    public boolean checkServiceRunning(Class<?> serviceClass){
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
        {
            if (serviceClass.getName().equals(service.service.getClassName()))
            {
                return true;
            }
        }
        return false;
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        if (i != CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            super.onActivityResult(i, i2, intent);
            return;
        }
        if (i2 == -1) {
            initializeView();
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
       // Hawk.init(this).build();
        setContentView((int) R.layout.quore_main);



        initializeView();


        RelativeLayout rel = (RelativeLayout) findViewById(R.id.banner);

        fb_baner(rel, MainActivity.this);

        switchServiceStatus.setOn(false);
        this.k = Globals.getPrebuttonfBoolean(this, "On_Off");
        this.iv_back = (ImageView) findViewById(R.id.iv_back);
        this.iv_back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.finish();
            }
        });
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        if (i == 10) {
            if (iArr.length <= 0 || iArr[0] + iArr[1] != 0) { /*android.R.id.content*/
                Snackbar.make(findViewById(android.R.id.content), (int) R.string.txt_stop_audio_recording_text, BaseTransientBottomBar.LENGTH_INDEFINITE).setAction((CharSequence) "ENABLE", new C03634()).show();
            }
        }
    }

    public void fb_baner(final RelativeLayout ad_layout, final Context context) {


        com.facebook.ads.AdView adView = new com.facebook.ads.AdView(context, getString(R.string.fb_bnr),
                com.facebook.ads.AdSize.BANNER_HEIGHT_50);

        ad_layout.addView(adView);

        ad_layout.setVisibility(View.VISIBLE);

        adView.loadAd();

    }
    protected void onResume() {
        super.onResume();
        if (this.k) {
            switchServiceStatus.setOn(true);
        } else {
            switchServiceStatus.setOn(false);
        }
        if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") + ContextCompat.checkSelfPermission(this, "android.permission.RECORD_AUDIO") == 0) {
            if (VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(this)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("package:");
                stringBuilder.append(getPackageName());
                startActivityForResult(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse(stringBuilder.toString())), CODE_DRAW_OVER_OTHER_APP_PERMISSION);
            }
            initializeView();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.WRITE_EXTERNAL_STORAGE") || ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.RECORD_AUDIO")) {
            Snackbar.make(findViewById(android.R.id.content), (int) R.string.label_permissions, BaseTransientBottomBar.LENGTH_INDEFINITE).setAction((CharSequence) "ENABLE", new C03623()).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.RECORD_AUDIO"}, 10);
        }
    }
}
