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
import com.example.musicplayer.adapters.ArtistAdapter;
import com.example.musicplayer.adapters.SongAdapter;
import com.example.musicplayer.dataloader.ArtistLoader;
import com.example.musicplayer.dataloader.SongLoader;


public class ArtistFragment extends Fragment {


    private RecyclerView recyclerView;
    private ArtistAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_artist, container, false);
        recyclerView = view.findViewById(R.id.arr);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        new loadData().execute("");

        return view;
    }

    public class loadData extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {

            if (getActivity()!=null){
                adapter = new ArtistAdapter(getActivity(), new ArtistLoader().aritsList(getActivity()));
            }

            return "Executed";
        }

        @Override
        protected void onPostExecute(String s) {
            if (getActivity()!= null){
                recyclerView.setAdapter(adapter);
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

}
