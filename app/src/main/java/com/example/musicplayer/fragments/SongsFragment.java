package com.example.musicplayer.fragments;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musicplayer.R;
import com.example.musicplayer.adapters.SongAdapter;
import com.example.musicplayer.dataloader.SongLoader;

/**
 * A simple {@link Fragment} subclass.
 */
public class SongsFragment extends Fragment {

    private SongAdapter songAdapter;
    private RecyclerView recyclerView;

    public SongsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_songs, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.sr);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        new loadData().execute("");

        return view;
    }

    public class loadData extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {

            if (getActivity()!=null){
                songAdapter = new SongAdapter(new SongLoader().getAllSongs(getActivity()));
            }

            return "Executed";
        }

        @Override
        protected void onPostExecute(String s) {
            if (getActivity()!= null){
                recyclerView.setAdapter(songAdapter);
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

}
