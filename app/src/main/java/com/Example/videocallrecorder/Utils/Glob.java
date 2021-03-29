package com.Example.videocallrecorder.Utils;

import android.media.session.PlaybackState;
import android.os.Environment;

import android.util.Log;
import java.io.File;
import java.util.ArrayList;

public class Glob {
    public static String DEVICE_ID = "";
    public static String Edit_Folder_name = "Video Call Recorder";
    public static ArrayList<String> IMAGEALLARY = new ArrayList();
    public static String acc_link = "https://play.google.com/store/apps/developer?id=";

    public static String app_link = "https://play.google.com/store/apps/details?id=";
    public static String app_name = "Video Call Recorder";

    public static String privacy_link = "";

    public static boolean createDirIfNotExists(String str) {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/");
        stringBuilder.append(str);
        File file = new File(externalStorageDirectory, stringBuilder.toString());
        if (file.exists()) {
            System.out.println("Can't create folder");
        } else {
            file.mkdir();
            if (!file.mkdirs()) {
                return false;
            }
        }
        return true;
    }

    public static void listAllImages(File file) {
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (int length = listFiles.length - 1; length >= 0; length--) {
                String file2 = listFiles[length].toString();
                File file3 = new File(file2);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(file3.length());
                String stringBuilder2 = stringBuilder.toString();
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append("");
                stringBuilder3.append(file3.length());
                Log.d(stringBuilder2, stringBuilder3.toString());
                if (file3.length() <= PlaybackState.ACTION_PLAY_FROM_MEDIA_ID) {
                    Log.i("Invalid Image", "Delete Image");
                } else if (file3.toString().contains(".jpg") || file3.toString().contains(".png") || file3.toString().contains(".jpeg")) {
                    IMAGEALLARY.add(file2);
                }
                System.out.println(file2);
            }
            return;
        }
        System.out.println("Empty Folder");
    }
}
