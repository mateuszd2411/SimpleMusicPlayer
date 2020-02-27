package com.example.musicplayer.adapters;

import android.content.ContentUris;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.R;
import com.example.musicplayer.models.Song;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.VH> {

    private List<Song> songsList;

    public SongAdapter(List<Song> songsList) {
        this.songsList = songsList;
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

    public class VH extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView titl, arts;

        public VH(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView)itemView.findViewById(R.id.songthumb);
            titl = (TextView) itemView.findViewById(R.id.songname);
            arts = (TextView) itemView.findViewById(R.id.artistname);


        }
    }
}
