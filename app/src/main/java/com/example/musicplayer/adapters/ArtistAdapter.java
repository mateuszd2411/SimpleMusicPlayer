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
import com.example.musicplayer.models.Artis;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import static com.example.musicplayer.adapters.SongAdapter.getImage;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ARV> {

    private Context context;
    private List<Artis> artisList;

    public ArtistAdapter(Context context, List<Artis> artisList) {
        this.context = context;
        this.artisList = artisList;
    }

    @NonNull
    @Override
    public ARV onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//false tr
        return new ARV(LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ARV holder, int position) {

        Artis artis = artisList.get(position);
        if (artis!= null){
            holder.artNaam.setText(artis.artName);
            ImageLoader.getInstance().displayImage(getImage(artis.id).toString(), holder.artimg,
                    new DisplayImageOptions.Builder().cacheInMemory(true).showImageOnLoading(R.drawable.note)
                            .resetViewBeforeLoading(true).build());
        }

    }

    @Override
    public int getItemCount() {
        return artisList!= null?artisList.size():0;
    }

    public class ARV extends RecyclerView.ViewHolder {

        private ImageView artimg;
        private TextView artNaam;

        public ARV(@NonNull View itemView) {
            super(itemView);

            artimg = itemView.findViewById(R.id.artthum);
            artNaam = itemView.findViewById(R.id.artname);
        }
    }
}
