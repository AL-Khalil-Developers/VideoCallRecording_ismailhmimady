package com.Example.videocallrecorder.Utils;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.Example.videocallrecorder.R;

public class MyImageHolder extends ViewHolder {
    public ImageView imageDelete;
    public ImageView imageThumbnail;
    public TextView txtImageName;

    public MyImageHolder(View view) {
        super(view);
        this.imageThumbnail = (ImageView) view.findViewById(R.id.image_thumbnail);
        this.imageDelete = (ImageView) view.findViewById(R.id.image_delete);
        this.txtImageName = (TextView) view.findViewById(R.id.txt_image_name);
    }
}
