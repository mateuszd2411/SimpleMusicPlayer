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
import com.example.musicplayer.adapters.AlbumSongAdapter;
import com.example.musicplayer.dataloader.AlbumLoader;
import com.example.musicplayer.dataloader.AlbumSongLoader;
import com.example.musicplayer.dataloader.ArtistLoader;
import com.example.musicplayer.dataloader.ArtistSongLoader;
import com.example.musicplayer.models.Album;
import com.example.musicplayer.models.Artis;
import com.example.musicplayer.models.Song;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import static com.example.musicplayer.adapters.SongAdapter.getImage;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistDetalsFragment extends Fragment {

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private long artistId;

    private List<Song> songList = new ArrayList<>();
    private Artis artist;

    private ImageView imageView;
    private ImageView squareImageView;
    private TextView anaam, ade;
    private RecyclerView recyclerView;
    private AlbumSongAdapter adapter;

    public static  ArtistDetalsFragment newInstance(long artist_id) {

        Bundle args = new Bundle();
        args.putLong("_ID", artist_id);
        ArtistDetalsFragment fragment = new ArtistDetalsFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        artistId = getArguments().getLong("_ID");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_artist_detals, container, false);

        squareImageView = rootView.findViewById(R.id.artistimg);
        imageView = rootView.findViewById(R.id.bigartist);
        anaam = rootView.findViewById(R.id.artistrnaam);
        ade = rootView.findViewById(R.id.artistDetails);
        collapsingToolbarLayout = rootView.findViewById(R.id.artistcollapsinglayout);
        recyclerView = rootView.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        artist = ArtistLoader.getArtis(getActivity(),artistId);
        setDetails();
        setupAlbumlist();
        return rootView;
    }

    private void setupAlbumlist() {

        songList = ArtistSongLoader.getAllArtistSongs(getActivity(),artistId);
        adapter = new AlbumSongAdapter(getActivity(),songList);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

    }

    private void setDetails() {

        collapsingToolbarLayout.setTitle(artist.artName);
        anaam.setText(artist.artName);
        ade.setText(" song: "+ artist.songCount);
        ImageLoader.getInstance().displayImage(getImage(artist.id).toString(), imageView,
                new DisplayImageOptions.Builder().cacheInMemory(true).showImageOnLoading(R.drawable.note)
                        .resetViewBeforeLoading(true).build());

        ImageLoader.getInstance().displayImage(getImage(artist.id).toString(), squareImageView,
                new DisplayImageOptions.Builder().cacheInMemory(true).showImageOnLoading(R.drawable.note)
                        .resetViewBeforeLoading(true).build());

    }

}
