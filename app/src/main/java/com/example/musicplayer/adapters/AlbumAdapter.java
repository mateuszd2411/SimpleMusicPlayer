package com.example.musicplayer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.R;
import com.example.musicplayer.models.Album;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import static com.example.musicplayer.adapters.SongAdapter.getImage;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AV> {
    private Context context;
    private List<Album> albumList;

    public AlbumAdapter(Context context, List<Album> albumList) {
        this.context = context;
        this.albumList = albumList;
    }

    @NonNull
    @Override
    public AV onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AV(LayoutInflater.from(parent.getContext()).inflate(R.layout.album_gride,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AV holder, int position) {
        Album album = albumList.get(position);

        if (album!=null){
            holder.alN.setText(album.albumName);
            holder.artN.setText(album.artistName);
            ImageLoader.getInstance().displayImage(getImage(album.id).toString(), holder.alimg,
                    new DisplayImageOptions.Builder().cacheInMemory(true).showImageOnLoading(R.drawable.note)
                            .resetViewBeforeLoading(true).build());
        }

    }

    @Override
    public int getItemCount() {
        return albumList!=null?albumList.size():0;
    }

    public class AV extends RecyclerView.ViewHolder {
        private ImageView alimg;
        private TextView alN, artN;


        public AV(@NonNull View itemView) {
            super(itemView);

            alimg = (ImageView)itemView.findViewById(R.id.albumimg);
            alN = (TextView)itemView.findViewById(R.id.albumn);
            artN = (TextView)itemView.findViewById(R.id.artn);
        }
    }
}
