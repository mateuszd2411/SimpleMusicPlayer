package com.example.musicplayer.adapters;

import android.content.ContentUris;
import android.net.Uri;
import android.os.Handler;
import android.os.RemoteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.R;
import com.example.musicplayer.models.Song;
import com.example.musicplayer.util.AxUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import static com.example.musicplayer.music.PlayerServices.playAll;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.VH> {

    private List<Song> songsList;
    private long[] mIds;

    public SongAdapter(List<Song> songsList) {
        this.songsList = songsList;
        mIds = getIds();
    }



    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.song_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        Song song = songsList.get(position);

        if (song!=null){
            holder.titl.setText(song.title);
            holder.arts.setText(song.artistName);
            ImageLoader.getInstance().displayImage(getImage(song.albumId).toString(), holder.imageView,
                    new DisplayImageOptions.Builder().cacheInMemory(true).showImageOnLoading(R.drawable.note)
                    .resetViewBeforeLoading(true).build());
        }

    }

    public static Uri getImage(long albumId) {
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId);

    }

    @Override
    public int getItemCount() {
        return songsList!=null?songsList.size():0;
    }

    private long[] getIds() {

        long[] res = new long[getItemCount()];
        for (int i=0;i<getItemCount();i++){
            res[i] = songsList.get(i).id;
        }
        return res;
    }

    public class VH extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private TextView titl, arts;

        public VH(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView)itemView.findViewById(R.id.songthumb);
            titl = (TextView) itemView.findViewById(R.id.songname);
            arts = (TextView) itemView.findViewById(R.id.artistname);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        playAll(mIds,getAdapterPosition(),songsList.get(getAdapterPosition()).id, AxUtil.IdType.NA);
                    }catch (RemoteException e){
                        e.printStackTrace();
                    }
                }
            },100);


        }
    }
}
