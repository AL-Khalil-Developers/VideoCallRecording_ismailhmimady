package com.Example.videocallrecorder.Utils;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.Example.videocallrecorder.R;

public class MyAudioHolder extends ViewHolder {
    public ImageView imageAudioThumbnail;
    public ImageView imageDeleteAudio;
    public ImageView imageNewBadge;
    public ImageView imageShareAudio;
    public ImageView iv_play;
    public TextView txtAudioDuration;
    public TextView txtAudioName;
    public TextView txtAudioSize;

    public MyAudioHolder(View view) {
        super(view);
        this.imageAudioThumbnail = (ImageView) view.findViewById(R.id.image_audio_thumbnail);
        this.imageDeleteAudio = (ImageView) view.findViewById(R.id.image_delete_audio);
        this.imageShareAudio = (ImageView) view.findViewById(R.id.image_share_audio);
        this.imageNewBadge = (ImageView) view.findViewById(R.id.image_new_badge);
        this.txtAudioName = (TextView) view.findViewById(R.id.txt_audio_name);
        this.txtAudioDuration = (TextView) view.findViewById(R.id.txt_audio_duration);
        this.txtAudioSize = (TextView) view.findViewById(R.id.txt_audio_size);
        this.iv_play = (ImageView) view.findViewById(R.id.iv_play);
    }
}
