package com.Example.videocallrecorder.Utils;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ImageView;
import com.Example.videocallrecorder.R;

public class BottomNavigationViewHolder extends ViewHolder {
    public ImageView imageNavItem;

    public BottomNavigationViewHolder(View view) {
        super(view);
        this.imageNavItem = (ImageView) view.findViewById(R.id.image_nav_item);
    }
}
