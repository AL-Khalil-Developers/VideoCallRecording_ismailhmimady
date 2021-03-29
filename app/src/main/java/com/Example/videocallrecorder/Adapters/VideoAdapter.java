package com.Example.videocallrecorder.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;

import androidx.recyclerview.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.Example.videocallrecorder.Activities.VideoTrimActivity;
import com.Example.videocallrecorder.Activities.VideoViewActivity;
import com.Example.videocallrecorder.Models.Video;
import com.Example.videocallrecorder.R;
import com.Example.videocallrecorder.Utils.MyVideosHolder;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class VideoAdapter extends Adapter<MyVideosHolder> {
    private final StringBuilder arrayList1;
    private Context context;
    private Intent dialogInterface1;
    private String lastFileName;
    private ArrayList<String> listOfFileNames = new ArrayList();
    private ArrayList<Video> listOfVideos = new ArrayList();

    private final String path;
    private StringBuilder view1;
    private StringBuilder view2;
    private StringBuilder view3;
    private StringBuilder view4;
    private StringBuilder view5;
    private StringBuilder view6;
    private StringBuilder view7;

    class C03889 implements OnClickListener {
        C03889() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    }

    public VideoAdapter(ArrayList<Video> arrayList, FragmentActivity fragmentActivity) {
        this.listOfVideos = arrayList;
        this.context = fragmentActivity;
        this.arrayList1 = new StringBuilder();
        this.arrayList1.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
        this.arrayList1.append(File.separator);
        this.arrayList1.append("Recordings");
        this.path = this.arrayList1.toString();
        this.lastFileName = getLastFileName();
    }

    private String getLastFileName() {
        Iterator it = this.listOfVideos.iterator();
        while (it.hasNext()) {
            this.listOfFileNames.add(((Video) it.next()).getName());
        }
        return this.listOfFileNames.size() > 0 ? (String) this.listOfFileNames.get(this.listOfFileNames.size() - 1) : "";
    }





    public void deleteVideoDialog(final File file, final int ii) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setPositiveButton("Delete", new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                if (file.exists()) {
                    file.delete();
                    VideoAdapter.this.listOfVideos.remove(ii);
                    VideoAdapter.this.notifyItemRemoved(ii);
                    VideoAdapter.this.notifyItemRangeChanged(ii, VideoAdapter.this.listOfVideos.size());
                    VideoAdapter.this.dialogInterface1 = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                    VideoAdapter.this.dialogInterface1.setData(Uri.fromFile(file));
                    VideoAdapter.this.context.sendBroadcast(VideoAdapter.this.dialogInterface1);
                    VideoAdapter.this.notifyDataSetChanged();
                    Toast.makeText(VideoAdapter.this.context, "Video Deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new C03889());
        builder.setMessage("Are you sure to delete this video?");
        builder.create().show();
    }

    public int getItemCount() {
        return this.listOfVideos.size();
    }

    public void onBindViewHolder(@NonNull MyVideosHolder myVideosHolder, final int i) {
        ImageView imageView;
        int i2;
        final Video video = (Video) this.listOfVideos.get(i);
        Glide.with(this.context).load(video.getThumbnail()).into(myVideosHolder.imageVideoThumbnail);
        myVideosHolder.txtVideoName.setText(video.getName());
        TextView textView = myVideosHolder.txtVideoDuration;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Duration :  ");
        stringBuilder.append(video.getDuration());
        textView.setText(stringBuilder.toString());
        textView = myVideosHolder.txtVideoSize;
        stringBuilder = new StringBuilder();
        stringBuilder.append("Size : ");
        stringBuilder.append(video.getSize());
        textView.setText(stringBuilder.toString());
        if (((Video) this.listOfVideos.get(i)).getName().equalsIgnoreCase(this.lastFileName)) {
            imageView = myVideosHolder.imageNewBadge;
            i2 = 0;
        } else {
            imageView = myVideosHolder.imageNewBadge;
            i2 = 8;
        }
        imageView.setVisibility(i2);
        myVideosHolder.imageVideoThumbnail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                VideoAdapter.this.view1 = new StringBuilder();
                VideoAdapter.this.view1.append(VideoAdapter.this.path);
                VideoAdapter.this.view1.append(File.separator);
                VideoAdapter.this.view1.append(video.getName());
               // Hawk.put("uri", Uri.fromFile(new File(VideoAdapter.this.view1.toString())));

                Intent intent=new Intent(VideoAdapter.this.context, VideoViewActivity.class);
                intent.putExtra("uri",Uri.fromFile(new File(VideoAdapter.this.view1.toString())).toString());
                VideoAdapter.this.context.startActivity(intent);
//                VideoAdapter.this.showAdmobInterstitial();
            }
        });
        myVideosHolder.txtVideoName.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                VideoAdapter.this.view2 = new StringBuilder();
                VideoAdapter.this.view2.append(VideoAdapter.this.path);
                VideoAdapter.this.view2.append(File.separator);
                VideoAdapter.this.view2.append(video.getName());
                Intent intent=new Intent(VideoAdapter.this.context, VideoViewActivity.class);
                intent.putExtra("uri",Uri.fromFile(new File(VideoAdapter.this.view2.toString())).toString());
                VideoAdapter.this.context.startActivity(intent);
             }
        });
        myVideosHolder.txtVideoSize.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                VideoAdapter.this.view3 = new StringBuilder();
                VideoAdapter.this.view3.append(VideoAdapter.this.path);
                VideoAdapter.this.view3.append(File.separator);
                VideoAdapter.this.view3.append(video.getName());

                Intent intent=new Intent(VideoAdapter.this.context, VideoViewActivity.class);
                intent.putExtra("uri",Uri.fromFile(new File(VideoAdapter.this.view3.toString())).toString());
                VideoAdapter.this.context.startActivity(intent);
             }
        });
        myVideosHolder.txtVideoDuration.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                VideoAdapter.this.view4 = new StringBuilder();
                VideoAdapter.this.view4.append(VideoAdapter.this.path);
                VideoAdapter.this.view4.append(File.separator);
                VideoAdapter.this.view4.append(video.getName());

                Log.e("ID",""+view4.toString());

                Intent intent=new Intent(VideoAdapter.this.context, VideoViewActivity.class);
                intent.putExtra("uri",Uri.fromFile(new File(VideoAdapter.this.view4.toString())).toString());
                VideoAdapter.this.context.startActivity(intent);
             }
        });
        myVideosHolder.imageDeleteVideo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                VideoAdapter.this.view5 = new StringBuilder();
                VideoAdapter.this.view5.append(VideoAdapter.this.path);
                VideoAdapter.this.view5.append(File.separator);
                VideoAdapter.this.view5.append(video.getName());
                VideoAdapter.this.deleteVideoDialog(new File(VideoAdapter.this.view5.toString()), i);
            }
        });
        myVideosHolder.imageTrimVideo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                VideoAdapter.this.view6 = new StringBuilder();
                VideoAdapter.this.view6.append(VideoAdapter.this.path);
                VideoAdapter.this.view6.append(File.separator);
                VideoAdapter.this.view6.append(video.getName());

                Intent intent=new Intent(VideoAdapter.this.context, VideoTrimActivity.class);
                intent.putExtra("video",view6.toString());
                VideoAdapter.this.context.startActivity(intent);

            }
        });
        myVideosHolder.imageShareVideo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                VideoAdapter.this.view7 = new StringBuilder();
                VideoAdapter.this.view7.append(VideoAdapter.this.path);
                VideoAdapter.this.view7.append(File.separator);
                VideoAdapter.this.view7.append(video.getName());
                VideoAdapter.this.shareVideo(new File(VideoAdapter.this.view7.toString()));
            }
        });
    }

    @NonNull
    public MyVideosHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new MyVideosHolder(LayoutInflater.from(this.context).inflate(R.layout.layout_video_item, viewGroup, false));
    }

    public void shareVideo(File file) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.STREAM", FileProvider.getUriForFile(this.context, context.getPackageName()+".provider", new File(String.valueOf(file))));
        intent.setType("Video/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        this.context.startActivity(Intent.createChooser(intent, "Share Video File"));
    }
}
