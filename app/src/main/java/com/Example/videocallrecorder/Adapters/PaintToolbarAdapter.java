package com.Example.videocallrecorder.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.Example.videocallrecorder.R;
import com.Example.videocallrecorder.Utils.BottomNavigationViewHolder;
import com.Example.videocallrecorder.Utils.CustomView;

public class PaintToolbarAdapter extends Adapter<BottomNavigationViewHolder> {
    int[] a = new int[]{R.drawable.ic_brush_white, R.drawable.ic_brush_black, R.drawable.ic_paint_undo, R.drawable.ic_paint_redo};
    private Context context;
    private CustomView customView;

    public PaintToolbarAdapter(Context context, CustomView customView) {
        this.context = context;
        this.customView = customView;
    }

    public int getItemCount() {
        return 4;
    }

    public void onBindViewHolder(@NonNull BottomNavigationViewHolder bottomNavigationViewHolder, final int i) {
        bottomNavigationViewHolder.imageNavItem.setBackgroundResource(this.a[i]);
        bottomNavigationViewHolder.imageNavItem.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                switch (i) {
                    case 0:
                        PaintToolbarAdapter.this.customView.paintColor = -1;
                        return;
                    case 1:
                        PaintToolbarAdapter.this.customView.paintColor = ViewCompat.MEASURED_STATE_MASK;
                        return;
                    case 2:
                        PaintToolbarAdapter.this.customView.onClickUndo();
                        return;
                    case 3:
                        PaintToolbarAdapter.this.customView.onClickRedo();
                        return;
                    default:
                        return;
                }
            }
        });
    }

    @NonNull
    public BottomNavigationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BottomNavigationViewHolder(LayoutInflater.from(this.context).inflate(R.layout.layout_bottom_navigation_view, viewGroup, false));
    }
}
