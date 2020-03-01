package com.example.musicplayer.fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.adapters.AlbumAdapter;
import com.example.musicplayer.adapters.AlbumSongAdapter;
import com.example.musicplayer.dataloader.AlbumLoader;
import com.example.musicplayer.dataloader.AlbumSongLoader;
import com.example.musicplayer.models.Album;
import com.example.musicplayer.models.Song;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import static com.example.musicplayer.adapters.SongAdapter.getImage;


public class AlbumDetailsFragment extends Fragment {


    private static final String TAG = "AlbumDetailsFragment";
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private long album_id;

    private List<Song> songList = new ArrayList<>();
    private Album album;

    private ImageView imageView;
    private ImageView squareImageView;
    private TextView anaam, ade;
    private RecyclerView recyclerView;
    private AlbumSongAdapter adapter;

    public static  AlbumDetailsFragment newInstance(long id) {

        Bundle args = new Bundle();
        args.putLong("_ID", id);
        AlbumDetailsFragment fragment = new AlbumDetailsFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        album_id = getArguments().getLong("_ID");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_album_details, container, false);

        squareImageView = rootView.findViewById(R.id.aaimg);
        imageView = rootView.findViewById(R.id.bigart);
        anaam = rootView.findViewById(R.id.atrnaam);
        ade = rootView.findViewById(R.id.albumDetails);
        collapsingToolbarLayout = rootView.findViewById(R.id.collapsinglayout);
        recyclerView = rootView.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        album = new AlbumLoader().getAlbum(getActivity(), album_id);
        setupAlbumlist();
        setDetails();

        return rootView;
    }

    private void setupAlbumlist() {

        songList = AlbumSongLoader.getAllAlbumSongs(getActivity(),album_id);
        adapter = new AlbumSongAdapter(getActivity(),songList);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

    }

    private void setDetails() {

        collapsingToolbarLayout.setTitle(album.albumName);
        anaam.setText(album.albumName);
        ade.setText(album.artistName+ " " + album.year+ " song: "+ album.numSong);
        ImageLoader.getInstance().displayImage(getImage(album.id).toString(), imageView,
                new DisplayImageOptions.Builder().cacheInMemory(true).showImageOnLoading(R.drawable.note)
                        .resetViewBeforeLoading(true).build());

        ImageLoader.getInstance().displayImage(getImage(album.id).toString(), squareImageView,
                new DisplayImageOptions.Builder().cacheInMemory(true).showImageOnLoading(R.drawable.note)
                        .resetViewBeforeLoading(true).build());

    }

}
