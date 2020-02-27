package com.example.musicplayer.fragments;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musicplayer.R;
import com.example.musicplayer.adapters.AlbumAdapter;
import com.example.musicplayer.adapters.GridSpacingItemDecoration;
import com.example.musicplayer.adapters.SongAdapter;
import com.example.musicplayer.dataloader.AlbumLoader;
import com.example.musicplayer.dataloader.SongLoader;


public class AlbumFragment extends Fragment {

    private RecyclerView recyclerView;
    private AlbumAdapter albumAdapter;
    int spanCount =2;  //columns
    int spacing = 20;  //px
    boolean includeEdge = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_album, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.ar);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),spanCount));

        new LoadData().execute("");
        return view;
    }

    public class LoadData extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {

            if (getActivity()!=null){
                albumAdapter = new AlbumAdapter(getActivity(), new AlbumLoader().albumsList(getActivity()));
            }

            return "Executed";
        }

        @Override
        protected void onPostExecute(String s) {
            recyclerView.setAdapter(albumAdapter);
            if (getActivity()!= null){
                recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount,spacing,includeEdge));
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

}
