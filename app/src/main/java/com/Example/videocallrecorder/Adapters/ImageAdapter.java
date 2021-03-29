package com.Example.videocallrecorder.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.Example.videocallrecorder.Activities.ImageViewActivity;
import com.Example.videocallrecorder.Models.Image;
import com.Example.videocallrecorder.R;
import com.Example.videocallrecorder.Utils.MyImageHolder;
import java.io.File;
import java.util.ArrayList;

public class ImageAdapter extends Adapter<MyImageHolder> {
    private final StringBuilder arrayList1;
    private Context context;
    private Intent dialogInterface1;
    private ArrayList<Image> listOfImages = new ArrayList();
    private final String path;
    private StringBuilder view1;
    private StringBuilder view2;
    private Intent view21;

    class C03794 implements OnClickListener {
        C03794() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    }

    public ImageAdapter(ArrayList<Image> arrayList, FragmentActivity fragmentActivity) {
        this.listOfImages = arrayList;
        this.context = fragmentActivity;
        this.arrayList1 = new StringBuilder();
        this.arrayList1.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
        this.arrayList1.append(File.separator);
        this.arrayList1.append("Screens");
        this.path = this.arrayList1.toString();
    }

    public void deleteAudioDialog(final File file, final int ii) {
        Builder builder = new Builder(this.context);
        builder.setPositiveButton((CharSequence) "Delete", new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                if (file.exists()) {
                    file.delete();
                    ImageAdapter.this.listOfImages.remove(ii);
                    ImageAdapter.this.notifyItemRemoved(ii);
                    ImageAdapter.this.notifyItemRangeChanged(ii, ImageAdapter.this.listOfImages.size());
                    ImageAdapter.this.dialogInterface1 = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                    ImageAdapter.this.dialogInterface1.setData(Uri.fromFile(file));
                    ImageAdapter.this.context.sendBroadcast(ImageAdapter.this.dialogInterface1);
                    ImageAdapter.this.notifyDataSetChanged();
                    Toast.makeText(ImageAdapter.this.context, "Screenshot Deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton((CharSequence) "Cancel", new C03794());
        builder.setMessage((CharSequence) "Are you sure to delete this screenshot?");
        builder.create().show();
    }

    public int getItemCount() {
        return this.listOfImages.size();
    }

    public void onBindViewHolder(@NonNull MyImageHolder myImageHolder, final int i) {
        final Image image = (Image) this.listOfImages.get(i);
        myImageHolder.imageThumbnail.setImageBitmap(image.getBitmap());
        myImageHolder.txtImageName.setText(image.getName());
        myImageHolder.imageDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ImageAdapter.this.view1 = new StringBuilder();
                ImageAdapter.this.view1.append(ImageAdapter.this.path);
                ImageAdapter.this.view1.append(File.separator);
                ImageAdapter.this.view1.append(image.getName());
                ImageAdapter.this.deleteAudioDialog(new File(ImageAdapter.this.view1.toString()), i);
            }
        });
        myImageHolder.imageThumbnail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ImageAdapter.this.view2 = new StringBuilder();
                ImageAdapter.this.view2.append(ImageAdapter.this.path);
                ImageAdapter.this.view2.append(File.separator);
                ImageAdapter.this.view2.append(image.getName());
                ImageAdapter.this.context.startActivity(new Intent(ImageAdapter.this.context, ImageViewActivity.class).putExtra("img", new File(ImageAdapter.this.view2.toString()).getPath()));
            }
        });
    }

    @NonNull
    public MyImageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyImageHolder(LayoutInflater.from(this.context).inflate(R.layout.layout_image_item, viewGroup, false));
    }
}
