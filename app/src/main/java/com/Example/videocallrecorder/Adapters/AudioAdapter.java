package com.Example.videocallrecorder.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.os.StrictMode.VmPolicy.Builder;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.Example.videocallrecorder.Models.Audio;
import com.Example.videocallrecorder.R;
import com.Example.videocallrecorder.Utils.MyAudioHolder;
import com.bumptech.glide.Glide;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class AudioAdapter extends Adapter<MyAudioHolder> implements OnPreparedListener {
    StringBuilder a;
    String b;
    private Context context;
    private Intent dialogInterface1;
    private String lastFileName;
    private ArrayList<Audio> listOfAudios = new ArrayList();
    private ArrayList<String> listOfFileNames = new ArrayList();
    private MediaPlayer mediaPlayer = null;
    public MediaPlayer mp;
    private int previous_play;
    private StringBuilder view1;
    private StringBuilder view5;
    private StringBuilder view6;

    class C03758 implements OnClickListener {
        C03758() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    }

    class C04412 implements OnCompletionListener {
        C04412() {
        }

        public void onCompletion(MediaPlayer mediaPlayer) {
            AudioAdapter.this.notifyDataSetChanged();
        }
    }

    public AudioAdapter(ArrayList<Audio> arrayList, FragmentActivity fragmentActivity) {
        this.listOfAudios = arrayList;
        this.context = fragmentActivity;
        this.a = new StringBuilder();
        this.a.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
        this.a.append(File.separator);
        this.a.append("Audio");
        this.b = this.a.toString();
        this.lastFileName = getLastFileName();
        StrictMode.setVmPolicy(new Builder().build());
    }

    private String getLastFileName() {
        Iterator it = this.listOfAudios.iterator();
        while (it.hasNext()) {
            this.listOfFileNames.add(((Audio) it.next()).getName());
        }
        return this.listOfFileNames.size() > 0 ? (String) this.listOfFileNames.get(this.listOfFileNames.size() - 1) : "";
    }

    public void deleteAudioDialog(final File file, final int ii) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setPositiveButton((CharSequence) "Delete", new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                if (file.exists()) {
                    file.delete();
                    AudioAdapter.this.listOfAudios.remove(ii);
                    AudioAdapter.this.notifyItemRemoved(ii);
                    AudioAdapter.this.notifyItemRangeChanged(ii, AudioAdapter.this.listOfAudios.size());
                    AudioAdapter.this.dialogInterface1 = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                    AudioAdapter.this.dialogInterface1.setData(Uri.fromFile(file));
                    AudioAdapter.this.context.sendBroadcast(AudioAdapter.this.dialogInterface1);
                    AudioAdapter.this.notifyDataSetChanged();
                    Toast.makeText(AudioAdapter.this.context, "Audio Deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton((CharSequence) "Cancel", new C03758());
        builder.setMessage((CharSequence) "Are you sure to delete this audio?");
        builder.create().show();
    }

    public int getItemCount() {
        return this.listOfAudios.size();
    }

    public void onBindViewHolder(@NonNull final MyAudioHolder myAudioHolder, final int i) {
        ImageView imageView;
        int i2;
        final Audio audio = (Audio) this.listOfAudios.get(i);
        Glide.with(this.context).load(Integer.valueOf(R.drawable.ic_audio)).into(myAudioHolder.imageAudioThumbnail);
        myAudioHolder.txtAudioName.setText(audio.getName());
        TextView textView = myAudioHolder.txtAudioDuration;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Duration :  ");
        stringBuilder.append(audio.getDuration());
        textView.setText(stringBuilder.toString());
        textView = myAudioHolder.txtAudioSize;
        stringBuilder = new StringBuilder();
        stringBuilder.append("Size : ");
        stringBuilder.append(audio.getSize());
        textView.setText(stringBuilder.toString());
        if (((Audio) this.listOfAudios.get(i)).getName().equalsIgnoreCase(this.lastFileName)) {
            imageView = myAudioHolder.imageNewBadge;
            i2 = 0;
        } else {
            imageView = myAudioHolder.imageNewBadge;
            i2 = 8;
        }
        imageView.setVisibility(i2);
        if (this.mp.isPlaying()) {
            if (i == this.previous_play) {
                imageView = myAudioHolder.iv_play;
                i2 = R.drawable.l_pause;
            } else {
                imageView = myAudioHolder.iv_play;
                i2 = R.drawable.l_play;
            }
            imageView.setImageResource(i2);
        }
        myAudioHolder.iv_play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ImageView imageView;
                int i = 0;
                AudioAdapter.this.view1 = new StringBuilder();
                AudioAdapter.this.view1.append(AudioAdapter.this.b);
                AudioAdapter.this.view1.append(File.separator);
                AudioAdapter.this.view1.append(audio.getName());
                File file = new File(AudioAdapter.this.view1.toString());
                if (!AudioAdapter.this.mp.isPlaying()) {
                    AudioAdapter.this.previous_play = i;
                    AudioAdapter.this.notifyDataSetChanged();
                    AudioAdapter.this.playSong(AudioAdapter.this.view1.toString());
                    imageView = myAudioHolder.iv_play;
                    i = R.drawable.l_pause;
                } else if (i == AudioAdapter.this.previous_play) {
                    AudioAdapter.this.previous_play = i;
                    AudioAdapter.this.mp.stop();
                    imageView = myAudioHolder.iv_play;
                    i = R.drawable.l_play;
                } else {
                    AudioAdapter.this.previous_play = i;
                    AudioAdapter.this.notifyDataSetChanged();
                    AudioAdapter.this.playSong(AudioAdapter.this.view1.toString());
                    return;
                }
                imageView.setImageResource(i);
            }
        });
        myAudioHolder.imageDeleteAudio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AudioAdapter.this.view5 = new StringBuilder();
                AudioAdapter.this.view5.append(AudioAdapter.this.b);
                AudioAdapter.this.view5.append(File.separator);
                AudioAdapter.this.view5.append(audio.getName());
                AudioAdapter.this.deleteAudioDialog(new File(AudioAdapter.this.view5.toString()), i);
            }
        });
        myAudioHolder.imageShareAudio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AudioAdapter.this.view6 = new StringBuilder();
                AudioAdapter.this.view6.append(AudioAdapter.this.b);
                AudioAdapter.this.view6.append(File.separator);
                AudioAdapter.this.view6.append(audio.getName());
                AudioAdapter.this.shareAudio(new File(AudioAdapter.this.view6.toString()));
            }
        });
    }

    @NonNull
    public MyAudioHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.mp = new MediaPlayer();
        return new MyAudioHolder(LayoutInflater.from(this.context).inflate(R.layout.layout_audio_item, viewGroup, false));
    }

    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }

    public void playSong(String str) {
        this.mp.reset();
        this.mp = MediaPlayer.create(this.context, Uri.parse(str));
        this.mp.start();
        this.mp.setOnCompletionListener(new C04412());
    }

    public void shareAudio(File file) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.STREAM", FileProvider.getUriForFile(this.context, context.getPackageName()+".provider", file));
        intent.setType("Audio/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        this.context.startActivity(Intent.createChooser(intent, "Share Audio File"));
    }
}
