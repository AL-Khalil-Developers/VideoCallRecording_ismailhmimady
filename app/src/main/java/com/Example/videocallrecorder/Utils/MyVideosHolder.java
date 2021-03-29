package com.Example.videocallrecorder.Utils;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.Example.videocallrecorder.R;

public class MyVideosHolder extends ViewHolder {
    public ImageView imageDeleteVideo;
    public ImageView imageNewBadge;
    public ImageView imageShareVideo;
    public ImageView imageTrimVideo;
    public ImageView imageVideoThumbnail;
    public TextView txtVideoDuration;
    public TextView txtVideoName;
    public TextView txtVideoSize;

    public MyVideosHolder(View view) {
        super(view);
        this.imageVideoThumbnail = (ImageView) view.findViewById(R.id.image_video_thumbnail);
        this.imageDeleteVideo = (ImageView) view.findViewById(R.id.image_delete_video);
        this.imageTrimVideo = (ImageView) view.findViewById(R.id.image_trim_video);
        this.imageShareVideo = (ImageView) view.findViewById(R.id.image_share_video);
        this.imageNewBadge = (ImageView) view.findViewById(R.id.image_new_badge);
        this.txtVideoName = (TextView) view.findViewById(R.id.txt_video_name);
        this.txtVideoDuration = (TextView) view.findViewById(R.id.txt_video_duration);
        this.txtVideoSize = (TextView) view.findViewById(R.id.txt_video_size);
    }
}
