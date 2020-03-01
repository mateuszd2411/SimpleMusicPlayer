package com.example.musicplayer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.R;
import com.example.musicplayer.fragments.AlbumDetailsFragment;
import com.example.musicplayer.models.Album;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import static com.example.musicplayer.adapters.SongAdapter.getImage;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {


    private List<Album> albumList;
    private Context context;

    public AlbumAdapter(Context context, List<Album> albumList) {
        this.context = context;
        this.albumList = albumList;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AlbumViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.album_gride,
                parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {

        Album album = albumList.get(position);

        if (album!=null){
            holder.albumT.setText(album.albumName);
            holder.albumA.setText(album.artistName);



            ImageLoader.getInstance().displayImage(getImage(album.id).toString(), holder.img,
                    new DisplayImageOptions.Builder().cacheInMemory(true).showImageOnLoading(R.drawable.note)
                            .resetViewBeforeLoading(true).build());
        }

    }

    @Override
    public int getItemCount() {
        return albumList!=null?albumList.size():0;
    }

    public class AlbumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView img;
        private TextView albumT, albumA;


        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView)itemView.findViewById(R.id.albumimg);
            albumT = (TextView)itemView.findViewById(R.id.albumn);
            albumA = (TextView)itemView.findViewById(R.id.artn);
            itemView.setOnClickListener(this);  /// 25 55   7part
        }

        @Override
        public void onClick(View view) {

            long albumId = albumList.get(getAdapterPosition()).id;

            FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            Fragment fragment;

            transaction.setCustomAnimations(R.anim.layout_fad_in, R.anim.layout_fad_out,
                    R.anim.layout_fad_in, R.anim.layout_fad_out);

            fragment = AlbumDetailsFragment.newInstance(albumId);

            transaction.hide(((AppCompatActivity)context).getSupportFragmentManager()
                    .findFragmentById(R.id.main_container));


            transaction.add(R.id.main_container, fragment);
            transaction.addToBackStack(null).commit();

        }



    }
}
