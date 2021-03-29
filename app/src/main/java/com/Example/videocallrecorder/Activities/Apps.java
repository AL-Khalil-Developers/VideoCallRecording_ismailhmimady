package com.Example.videocallrecorder.Activities;

import android.app.Application;

import com.facebook.ads.AudienceNetworkAds;
import com.orhanobut.hawk.Hawk;

public class Apps extends Application {


        @Override
        public void onCreate() {
            super.onCreate();
            Hawk.init(this).build();

            AudienceNetworkAds.initialize(this);
        }
    }

