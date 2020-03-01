package com.example.musicplayer.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.R;
import com.example.musicplayer.models.Song;

import java.util.List;

public class AlbumSongAdapter extends RecyclerView.Adapter<AlbumSongAdapter.ASVH> {

    private Activity context;
    private List<Song> albumSongList;

    public AlbumSongAdapter(Activity context, List<Song> albumSongList) {
        this.context = context;
        this.albumSongList = albumSongList;
    }

    @NonNull
    @Override
    public ASVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ASVH(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_list_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ASVH holder, int position) {

        Song song = albumSongList.get(position);

        if (song!=null){
           
            holder.atv.setText(song.title);
            holder.dtv.setText(song.artistName);
        }

    }

    @Override
    public int getItemCount() {
        return albumSongList!=null?albumSongList.size():0;
    }

    public class ASVH extends RecyclerView.ViewHolder {

        private TextView atv,ntv,dtv;

        public ASVH(@NonNull View view) {
            super(view);

            atv = view.findViewById(R.id.songTitle);
            ntv = view.findViewById(R.id.number);
            dtv = view.findViewById(R.id.detail);

        }
    }
}
