package com.Example.videocallrecorder.Services;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.ImageReader.OnImageAvailableListener;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjection.Callback;
import android.net.Uri;
import android.os.Binder;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Example.videocallrecorder.Activities.MainActivity;
import com.Example.videocallrecorder.Activities.NonActivity;
import com.Example.videocallrecorder.Activities.NonScreenshotActivity;
import com.Example.videocallrecorder.Adapters.PaintToolbarAdapter;
import com.Example.videocallrecorder.R;
import com.Example.videocallrecorder.Main.Global.Globals;
import com.Example.videocallrecorder.Main.activities.MainsplshActivity;
import com.Example.videocallrecorder.Utils.CustomView;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;

import com.facebook.ads.InterstitialAdListener;
import com.orhanobut.hawk.Hawk;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.Buffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class RecordingService extends Service implements OnClickListener {
    public static final int MEDIA_TYPE_AUDIO = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static final String SCREENCAP_NAME = "screencap";
    private static final int VIRTUAL_DISPLAY_FLAGS = 9;
    private static File i;
    private static StringBuilder i6;
    private static MediaProjection sMediaProjection;
    private Adapter adapter;
    private MediaRecorder audioRecorder;
    private Image bitmap22;
    private View collapsedControllerView;
    private View collapsedView;
    private int counter = 0;
    private View counterView;
    private CustomView customView;
    private int dpi;
    private View expandedControllerView;
    private View expandedView;
    private View floatingView;
    private int height = 1080;
    private ImageView imageCaptureScreenshot;
    private ImageView imageCloseExpandedService;
    private ImageView mainiconexpandclose;
    private ImageView imageCloseService;
    private ImageView imageEditVideo;
    private ImageView imageStartAudioRecording;
    private ImageView imageStartVideoRecording;
    private ImageView homeiconexpandclose;
    private ImageView imageStopAudioRecording;
    private ImageView imageStopVideoRecording;
    private boolean isBitmapAdded = false;
    private int mDensity;
    private Display mDisplay;
    private Handler mHandler;
    private int mHeight;
    private ImageReader mImageReader;
    private OrientationChangeCallback mOrientationChangeCallback;
    private int mRotation;
    private VirtualDisplay mVirtualDisplay;
    private int mWidth;
    private LinearLayoutManager manager;
    private MediaProjection mediaProjection;
    private MediaRecorder mediaRecorder;
    private int motionEvent1;
    private int motionEvent2;
    private View paintView;
    private LayoutParams paintViewParams;
    private LayoutParams params;
    private RecyclerView recyclerView;
    private boolean running;
    private Chronometer txtCounter;
    private LayoutParams txtCounterParams;
    private TextView txtStartAudioRecording;
    private TextView txtStopAudioRecording;
    private LayoutParams videoControllerParams;
    private View videoControllerView;
    private String videoUri;
    private int view1;
    private int view12;
    private Intent view123;
    private Intent view124;
    private int view2;
    private VirtualDisplay virtualDisplay;
    private int width = 720;
    private WindowManager windowManager;

    class forthread extends Thread {
        forthread() {
        }

        public void run() {
            Looper.prepare();
            RecordingService.this.mHandler = new Handler();
            Looper.loop();
        }
    }

    class imgecloseservicesaction implements OnClickListener {
        imgecloseservicesaction() {
        }

        public void onClick(View view) {

            RecordingService.this.stopSelf();
            MainActivity.switchServiceStatus.setOn(false);
            Globals.setPrefbuttonBoolean(RecordingService.this, "On_Off", false);
        }
    }

    class startrecordingaction implements OnClickListener {
        startrecordingaction() {
        }

        public void onClick(View view) {
            RecordingService.this.expandedView.setVisibility(View.GONE);
            RecordingService.this.collapsedView.setVisibility(View.VISIBLE);
            RecordingService.this.showCounter();
        }
    }

    class closeexpandedservicesaction implements OnClickListener {
        closeexpandedservicesaction() {
        }

        public void onClick(View view) {
            RecordingService.this.collapsedView.setVisibility(View.VISIBLE);
            RecordingService.this.expandedView.setVisibility(View.GONE);
        }
    }

    class floatingviewtouchactoin implements OnTouchListener {
        private float initialTouchX;
        private float initialTouchY;
        private int initialX;
        private int initialY;

        floatingviewtouchactoin() {
        }


        @SuppressLint("ClickableViewAccessibility")
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case 0:
                    this.initialX = RecordingService.this.params.x;
                    this.initialY = RecordingService.this.params.y;
                    this.initialTouchX = motionEvent.getRawX();
                    this.initialTouchY = motionEvent.getRawY();
                    return true;
                case 1:
                    RecordingService.this.view1 = (int) (motionEvent.getRawX() - this.initialTouchX);
                    RecordingService.this.motionEvent1 = (int) (motionEvent.getRawY() - this.initialTouchY);
                    if (RecordingService.this.view1 < 10 && RecordingService.this.motionEvent1 < 10 && RecordingService.this.isViewCollapsed()) {
                        RecordingService.this.collapsedView.setVisibility(View.GONE);
                        RecordingService.this.expandedView.setVisibility(View.VISIBLE);
                    }
                    return true;
                case 2:
                    RecordingService.this.params.x = this.initialX + ((int) (motionEvent.getRawX() - this.initialTouchX));
                    RecordingService.this.params.y = this.initialY + ((int) (motionEvent.getRawY() - this.initialTouchY));
                    RecordingService.this.windowManager.updateViewLayout(RecordingService.this.floatingView, RecordingService.this.params);
                    return true;
                default:
                    return false;
            }
        }
    }

    class imageeditvideoaction implements OnClickListener {
        imageeditvideoaction() {
        }

        public void onClick(View view) {
            RecordingService.this.showPaintView();
        }
    }
    class homeexpandcloseaction implements OnClickListener {
        homeexpandcloseaction() {
        }

        public void onClick(View view) {
            RecordingService.this.expandedControllerView.setVisibility(View.GONE);
            RecordingService.this.collapsedControllerView.setVisibility(View.VISIBLE);
        }
    }

    class imgstoprecordclickaction implements OnClickListener {
        imgstoprecordclickaction() {
        }

        @RequiresApi(api = 19)
        public void onClick(View view) {
            RecordingService.this.stopRecord();
            if (RecordingService.this.videoControllerView != null) {
                RecordingService.this.windowManager.removeView(RecordingService.this.videoControllerView);
            }
            RecordingService.this.collapsedView.setVisibility(View.VISIBLE);
            Intent intent = new Intent(RecordingService.this, MainsplshActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            RecordingService.this.startActivity(intent);

            fb_interstal(RecordingService.this);
        }
    }


    public void fb_interstal(final Context context) {


        // TODO Auto-generated method stub
        try {

            // // intertial ad code //////

            final com.facebook.ads.InterstitialAd interstitialAd = new com.facebook.ads.InterstitialAd(
                    context, getString(R.string.fb_inter));

            InterstitialAdListener interstitialAdListener=new InterstitialAdListener() {
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

    class videocontrollertouchaction implements OnTouchListener {
        private float initialTouchX;
        private float initialTouchY;
        private int initialX;
        private int initialY;

        videocontrollertouchaction() {
        }

        @SuppressLint("ClickableViewAccessibility")
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case 0:
                    this.initialX = RecordingService.this.videoControllerParams.x;
                    this.initialY = RecordingService.this.videoControllerParams.y;
                    this.initialTouchX = motionEvent.getRawX();
                    this.initialTouchY = motionEvent.getRawY();
//                    RecordingService.this.collapsedControllerView.setVisibility(View.VISIBLE);
//                    RecordingService.this.expandedControllerView.setVisibility(View.GONE);
                    return true;
                case 1:
                    RecordingService.this.view2 = (int) (motionEvent.getRawX() - this.initialTouchX);
                    RecordingService.this.motionEvent2 = (int) (motionEvent.getRawY() - this.initialTouchY);
                    if (RecordingService.this.view2 < 10 && RecordingService.this.motionEvent2 < 10 && RecordingService.this.isControllerViewCollapsed()) {
                        RecordingService.this.collapsedControllerView.setVisibility(View.GONE);
                        RecordingService.this.expandedControllerView.setVisibility(View.VISIBLE);
                    }
                    return true;
                case 2:
                    RecordingService.this.videoControllerParams.x = this.initialX + ((int) (motionEvent.getRawX() - this.initialTouchX));
                    RecordingService.this.videoControllerParams.y = this.initialY + ((int) (motionEvent.getRawY() - this.initialTouchY));
                    RecordingService.this.windowManager.updateViewLayout(RecordingService.this.videoControllerView, RecordingService.this.videoControllerParams);
                    return true;
                default:
                    return false;
            }
        }
    }


    @TargetApi(19)
    private class ImageAvailableListener implements OnImageAvailableListener {
        private ImageAvailableListener() {
        }


        public void onImageAvailable(ImageReader imageReader) {
            FileOutputStream fileOutputStream;
            Throwable e;

            Image acquireLatestImage = imageReader.acquireLatestImage();
            if (acquireLatestImage != null) {

                Image.Plane[] planes = acquireLatestImage.getPlanes();
                Buffer buffer = planes[0].getBuffer();
                int pixelStride = planes[0].getPixelStride();
                int r = RecordingService.this.mWidth + ((planes[0].getRowStride() - (RecordingService.this.mWidth * pixelStride)) / pixelStride);
                int s = RecordingService.this.mHeight;

                acquireLatestImage.close();
                Bitmap createBitmap = Bitmap.createBitmap(r, s, Bitmap.Config.ARGB_8888);

                createBitmap.copyPixelsFromBuffer(buffer);
                if (!RecordingService.this.isBitmapAdded) {
                    try {
                        fileOutputStream = new FileOutputStream(RecordingService.getOutputMediaFile(3).getAbsolutePath());
                        createBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                        intent.setData(Uri.fromFile(new File(RecordingService.getOutputMediaFile(3).getAbsolutePath())));
                        RecordingService.this.sendBroadcast(intent);
                        RecordingService.this.isBitmapAdded = true;
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
                    return;
                }


            }


        }
    }

    @TargetApi(21)
    private class MediaProjectionStopCallback extends Callback {

        class C04011 implements Runnable {
            C04011() {
            }

            public void run() {
                if (RecordingService.this.mVirtualDisplay != null) {
                    RecordingService.this.mVirtualDisplay.release();
                }
                if (RecordingService.this.mImageReader != null) {
                    RecordingService.this.mImageReader.setOnImageAvailableListener(null, null);
                }
                if (RecordingService.this.mOrientationChangeCallback != null) {
                    RecordingService.this.mOrientationChangeCallback.disable();
                }
                RecordingService.sMediaProjection.unregisterCallback(MediaProjectionStopCallback.this);
            }
        }

        private MediaProjectionStopCallback() {
        }


        public void onStop() {
            Log.e("ScreenCapture", "stopping projection.");
            RecordingService.this.mHandler.post(new C04011());
        }
    }

    private class OrientationChangeCallback extends OrientationEventListener {
        OrientationChangeCallback(Context context) {
            super(context);
        }

        @RequiresApi(api = 19)
        public void onOrientationChanged(int i) {
            i = RecordingService.this.mDisplay.getRotation();
            if (i != RecordingService.this.mRotation) {
                RecordingService.this.mRotation = i;
                if (RecordingService.this.mVirtualDisplay != null) {
                    RecordingService.this.mVirtualDisplay.release();
                }
                if (RecordingService.this.mImageReader != null) {
                    RecordingService.this.mImageReader.setOnImageAvailableListener(null, null);
                }
                RecordingService.this.createVirtualDisplay();
            }
        }
    }

    public class RecordBinder extends Binder {
        public RecordingService getRecordingService() {
            return RecordingService.this;
        }
    }

    private void addVideoToGallery() {
        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        intent.setData(Uri.fromFile(new File(this.videoUri)));
        sendBroadcast(intent);
    }

    @TargetApi(21)
    @RequiresApi(api = 19)
    private void createScreenshotVirtualDisplay() {
        Point point = new Point();
        this.mDisplay.getSize(point);
        this.mWidth = point.x;
        this.mHeight = point.y;
        this.mImageReader = ImageReader.newInstance(this.mWidth, this.mHeight, 1, 2);
        this.mVirtualDisplay = sMediaProjection.createVirtualDisplay(SCREENCAP_NAME, this.mWidth, this.mHeight, this.mDensity, 9, this.mImageReader.getSurface(), null, this.mHandler);
        this.mImageReader.setOnImageAvailableListener(new ImageAvailableListener(), this.mHandler);
    }

    private void createVirtualDisplay() {
        if (VERSION.SDK_INT >= 21) {
            this.virtualDisplay = this.mediaProjection.createVirtualDisplay("MainScreen", this.width, this.height, this.dpi, 16, this.mediaRecorder.getSurface(), null, null);
        }
    }

    private static File getOutputMediaFile(int i) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Recordings");
        if (file.exists() || file.mkdirs()) {
            @SuppressLint("SimpleDateFormat") String format = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            StringBuilder stringBuilder;
            if (i == 1) {
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Audio");
                if (file.exists() || file.mkdirs()) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(file.getPath());
                    stringBuilder.append(File.separator);
                    stringBuilder.append("AUDIO_");
                    stringBuilder.append(format);
                    stringBuilder.append(".m4a");
                    file = new File(stringBuilder.toString());
                }
            } else if (i == 3) {
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Screens");
                if (file.exists() || file.mkdirs()) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(file.getPath());
                    stringBuilder.append(File.separator);
                    stringBuilder.append("Screenshot");
                    stringBuilder.append(format);
                    stringBuilder.append(".png");
                    file = new File(stringBuilder.toString());
                }
            } else if (i != 2) {
                return null;
            } else {
                i6 = new StringBuilder();
                i6.append(file.getPath());
                i6.append(File.separator);
                i6.append("MP4_");
                i6.append(format);
                i6.append(".mp4");
                file = new File(i6.toString());
                return file;
            }

            return file;
        }
        Log.d("MyCameraApp", "failed to create directory");
        return null;
    }

    private void initRecorder() {
        this.mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        this.mediaRecorder.setVideoSource(2);
        this.mediaRecorder.setOutputFormat(1);
        this.videoUri = getOutputMediaFile(2).getAbsolutePath();
        this.mediaRecorder.setOutputFile(this.videoUri);
        this.mediaRecorder.setVideoSize(this.width, this.height);
        this.mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT); /*2*/
        this.mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB); /*1*/
        this.mediaRecorder.setVideoEncodingBitRate(5242880);
        this.mediaRecorder.setVideoFrameRate(30);


        try {
            mediaRecorder.prepare();
            Thread.sleep(1000);
        } catch (Exception e) {
            // TODO: handle exception


            Log.e("Exception", "" + e.toString());
            e.printStackTrace();
        }
    }

    private boolean isControllerViewCollapsed() {
        return this.videoControllerView == null || this.videoControllerView.findViewById(R.id.collapsed_controller_view).getVisibility() == View.VISIBLE;
    }

    private boolean isViewCollapsed() {
        return this.floatingView == null || this.floatingView.findViewById(R.id.collapsed_view).getVisibility() == View.VISIBLE;
    }

    @SuppressLint("InflateParams")
    private void showCounter() {
        LayoutParams layoutParams;
        this.counterView = LayoutInflater.from(this).inflate(R.layout.layout_counter_textview, null);
        if (VERSION.SDK_INT >= 26) {
            layoutParams = new LayoutParams(-1, -1, 2038, 8, -3);
        } else {
            layoutParams = new LayoutParams(-1, -1, AdError.CACHE_ERROR_CODE, 8, -3);
        }
        this.txtCounterParams = layoutParams;
        this.txtCounterParams.gravity = 17;
        this.windowManager.addView(this.counterView, this.txtCounterParams);
        final Handler handler = new Handler();
        final TextView textView = (TextView) this.counterView.findViewById(R.id.txt_counter);
        final AtomicInteger atomicInteger = new AtomicInteger(3);
        handler.postDelayed(new Runnable() {

            class C03961 implements AnimatorUpdateListener {
                C03961() {
                }

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    textView.setTextSize(((Float) valueAnimator.getAnimatedValue()).floatValue());
                }
            }

            @SuppressLint("SetTextI18n")
            public void run() {
                textView.setText(Integer.toString(atomicInteger.get()));
                ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{30.0f, 50.0f});
                ofFloat.setDuration(600);
                ofFloat.addUpdateListener(new C03961());
                ofFloat.start();
                if (atomicInteger.getAndDecrement() >= 1) {
                    handler.postDelayed(this, 1000);
                    return;
                }
                if (RecordingService.this.counterView != null) {
                    RecordingService.this.windowManager.removeView(RecordingService.this.counterView);
                }

                Log.e("QuoreNonActivity", "befre");
                Intent intent = new Intent(getApplicationContext(), NonActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK
                RecordingService.this.startActivity(intent);
                RecordingService.this.showVideoControllerView();
                Log.e("QuoreNonActivity", "start");
            }
        }, 500);
    }

    @SuppressLint("InflateParams")
    private void showPaintView() {
        LayoutParams layoutParams;
        this.expandedControllerView.setVisibility(View.GONE);
        this.paintView = LayoutInflater.from(this).inflate(R.layout.layout_paint_view, null);
        if (VERSION.SDK_INT >= 26) {
            layoutParams = new LayoutParams(-1, -1, 2038, 8, -3);
        } else {
            layoutParams = new LayoutParams(-1, -1, AdError.CACHE_ERROR_CODE, 8, -3);
        }
        this.paintViewParams = layoutParams;
        this.windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        this.windowManager.addView(this.paintView, this.paintViewParams);
        this.recyclerView = (RecyclerView) this.paintView.findViewById(R.id.recycler_view_paint_tools);
        this.customView = (CustomView) this.paintView.findViewById(R.id.custom_view);
        this.manager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        this.recyclerView.setLayoutManager(this.manager);
        this.adapter = new PaintToolbarAdapter(this, this.customView);
        this.recyclerView.setAdapter(this.adapter);
        ((Button) this.paintView.findViewById(R.id.btn_done_painting)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (RecordingService.this.paintView != null) {
                    RecordingService.this.windowManager.removeView(RecordingService.this.paintView);
                }
                RecordingService.this.collapsedControllerView.setVisibility(View.VISIBLE);
            }
        });
    }


    @SuppressLint("InflateParams")
    private void showVideoControllerView() {
        LayoutParams layoutParams;
        this.collapsedView.setVisibility(View.GONE);
        this.videoControllerView = LayoutInflater.from(this).inflate(R.layout.layout_floating_video_controller, null);
        if (VERSION.SDK_INT >= 26) {
            layoutParams = new LayoutParams(-2, -2, 2038, 8, -3);
        } else {
            layoutParams = new LayoutParams(-2, -2, AdError.CACHE_ERROR_CODE, 8, -3);
        }
        this.videoControllerParams = layoutParams;
        this.videoControllerParams.gravity = 51;
        this.videoControllerParams.x = 0;
        this.videoControllerParams.y = 100;
        this.windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        this.windowManager.addView(this.videoControllerView, this.videoControllerParams);
        this.collapsedControllerView = this.videoControllerView.findViewById(R.id.collapsed_controller_view);
        this.expandedControllerView = this.videoControllerView.findViewById(R.id.expanded_controller_view);
        this.imageEditVideo = (ImageView) this.videoControllerView.findViewById(R.id.image_edit_video);
        this.imageEditVideo.setOnClickListener(new imageeditvideoaction());
        this.imageStopVideoRecording = (ImageView) this.videoControllerView.findViewById(R.id.image_stop_video_recording);
        this.imageStopVideoRecording.setOnClickListener(new imgstoprecordclickaction());
        this.videoControllerView.findViewById(R.id.video_controller_container).setOnTouchListener(new videocontrollertouchaction());

        
        this.homeiconexpandclose = (ImageView) this.videoControllerView.findViewById(R.id.imageView3);
        this.homeiconexpandclose.setOnClickListener(new homeexpandcloseaction());
    }

    public boolean isRunning() {
        return this.running;
    }

    public IBinder onBind(Intent intent) {
        return new RecordBinder();
    }

    public void onClick(View view) {
        this.view12 = view.getId();
        if (this.view12 == R.id.image_capture_screenshot) {
            this.isBitmapAdded = false;
            this.expandedView.setVisibility(View.GONE);
            this.view123 = new Intent(this, NonScreenshotActivity.class);
            this.view123.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(this.view123);


        } else if (this.view12 == R.id.image_start_audio_recording) {
            this.audioRecorder = new MediaRecorder();
            this.audioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            this.audioRecorder.setOutputFormat(2);
            this.audioRecorder.setOutputFile(getOutputMediaFile(1).getAbsolutePath());
            this.audioRecorder.setAudioEncoder(1);
            try {
                this.audioRecorder.prepare();
            } catch (Throwable e) {
                e.printStackTrace();
            }
            this.audioRecorder.start();
            this.collapsedView.setVisibility(View.VISIBLE);
            this.expandedView.setVisibility(View.GONE);
            this.txtCounter.setVisibility(View.VISIBLE);
            this.txtCounter.setBase(SystemClock.elapsedRealtime());
            this.txtCounter.start();
            this.txtStartAudioRecording.setVisibility(View.GONE);
            this.imageStartAudioRecording.setVisibility(View.GONE);
            this.txtStopAudioRecording.setVisibility(View.VISIBLE);
            this.imageStopAudioRecording.setVisibility(View.VISIBLE);
        } else {
            if (this.view12 == R.id.image_stop_audio_recording) {
                this.audioRecorder.stop();
                this.audioRecorder.release();
                this.collapsedView.setVisibility(View.VISIBLE);
                this.expandedView.setVisibility(View.GONE);
                this.txtCounter.setVisibility(View.GONE);
                this.txtCounter.stop();
                this.txtStartAudioRecording.setVisibility(View.VISIBLE);
                this.imageStartAudioRecording.setVisibility(View.VISIBLE);
                this.txtStopAudioRecording.setVisibility(View.GONE);
                this.imageStopAudioRecording.setVisibility(View.GONE);
                this.view124 = new Intent(this, MainsplshActivity.class);
                this.view124.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                this.view124.putExtra("index", 2);
                startActivity(this.view124);
                Toast.makeText(this, "Recording Completed...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("InflateParams")
    public void onCreate() {
        super.onCreate();
        // Hawk.init(this).build();
        Hawk.put(NotificationCompat.CATEGORY_SERVICE, Boolean.TRUE);


        new forthread().start();
        new HandlerThread("service_thread", 10).start();
        this.running = false;
        this.mediaRecorder = new MediaRecorder();
        this.floatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_view, null);
        LayoutParams layoutParams;
        if (VERSION.SDK_INT >= 26) {
            layoutParams = new LayoutParams(-2, -2, 2038, 8, -3);
        } else {
            layoutParams = new LayoutParams(-2, -2, AdError.CACHE_ERROR_CODE, 8, -3);
        }
        this.params = layoutParams;
        this.params.gravity = 51;
        this.params.x = 0;
        this.params.y = 100;
        this.windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        this.windowManager.addView(this.floatingView, this.params);
        this.collapsedView = this.floatingView.findViewById(R.id.collapsed_view);
        this.expandedView = this.floatingView.findViewById(R.id.expanded_view);
        this.imageCloseService = (ImageView) this.floatingView.findViewById(R.id.image_close_service);
        this.imageCloseService.setOnClickListener(new imgecloseservicesaction());
        this.imageStartVideoRecording = (ImageView) this.floatingView.findViewById(R.id.image_start_video_recording);
        this.imageStartVideoRecording.setOnClickListener(new startrecordingaction());
        this.imageStartAudioRecording = (ImageView) this.floatingView.findViewById(R.id.image_start_audio_recording);
        this.imageStartAudioRecording.setOnClickListener(this);
        this.imageStopAudioRecording = (ImageView) this.floatingView.findViewById(R.id.image_stop_audio_recording);
        this.imageStopAudioRecording.setOnClickListener(this);
        this.txtStartAudioRecording = (TextView) this.floatingView.findViewById(R.id.txt_audio_recording);
        this.txtStopAudioRecording = (TextView) this.floatingView.findViewById(R.id.txt_stop_audio_recording);
        this.imageCloseExpandedService = (ImageView) this.floatingView.findViewById(R.id.image_close_expanded_service);
        this.mainiconexpandclose = (ImageView) this.floatingView.findViewById(R.id.image_service_expanded);
        this.imageCloseExpandedService.setOnClickListener(new closeexpandedservicesaction());
        this.mainiconexpandclose.setOnClickListener(new closeexpandedservicesaction());
        this.txtCounter = (Chronometer) this.floatingView.findViewById(R.id.txt_counter);
        this.imageCaptureScreenshot = (ImageView) this.floatingView.findViewById(R.id.image_capture_screenshot);
        this.imageCaptureScreenshot.setOnClickListener(this);
        this.floatingView.findViewById(R.id.main_container).setOnTouchListener(new floatingviewtouchactoin());




    }

    public void onDestroy() {
        super.onDestroy();
        if (this.floatingView != null && MainActivity.switchServiceStatus != null) {
            this.windowManager.removeView(this.floatingView);
            MainActivity.switchServiceStatus.setOn(false);
        }
        Hawk.delete(NotificationCompat.CATEGORY_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int i, int i2) {


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

        mBuilder.setContentTitle(getString(R.string.app_name))
                .setSmallIcon(R.drawable.ic_launchersmall)
                .setContentText("Video Recorder Running...")
                .setAutoCancel(false)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);


        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("10001", "Video Recorder", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            mBuilder.setChannelId("10001");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        // mNotificationManager.notify(0 /* Request Code */, mBuilder.build());

        startForeground(1, mBuilder.build());


        return START_STICKY;
    }

    public void setConfig(int i, int i2, int i3) {
        this.width = i;
        this.height = i2;
        this.dpi = i3;
    }

    public void setMediaProject(MediaProjection mediaProjection) {
        this.mediaProjection = mediaProjection;
    }

    @RequiresApi(api = 21)
    public void setScreenShotMediaProject(MediaProjection mediaProjection) {
        sMediaProjection = mediaProjection;
        if (sMediaProjection != null) {
            this.mDensity = getResources().getDisplayMetrics().densityDpi;
            this.mDisplay = this.windowManager.getDefaultDisplay();
            createScreenshotVirtualDisplay();
            this.mOrientationChangeCallback = new OrientationChangeCallback(this);
            if (this.mOrientationChangeCallback.canDetectOrientation()) {
                this.mOrientationChangeCallback.enable();
            }
            sMediaProjection.registerCallback(new MediaProjectionStopCallback(), this.mHandler);
            this.collapsedView.setVisibility(View.VISIBLE);
        }
    }

    public boolean startRecord() {
        if (this.mediaProjection == null || this.running) {
            return false;
        }
        initRecorder();
        createVirtualDisplay();
        this.mediaRecorder.start();
        this.running = true;
        return true;
    }

    @RequiresApi(api = 19)
    public boolean stopRecord() {
        if (!this.running) {
            return false;
        }
        this.running = false;


        try {
            this.mediaRecorder.stop();
        } catch (RuntimeException stopException) {
            Log.e("RuntimeException", "" + stopException.toString());
        }


        this.mediaRecorder.reset();
        this.virtualDisplay.release();
        if (VERSION.SDK_INT >= 21) {
            this.mediaProjection.stop();
        }
        addVideoToGallery();
        return true;
    }
}
