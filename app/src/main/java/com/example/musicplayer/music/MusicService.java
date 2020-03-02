package com.example.musicplayer.music;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.musicplayer.MusicAIDL;
import com.example.musicplayer.models.PlayBackTrack;
import com.example.musicplayer.songdb.SongPlayStatus;
import com.example.musicplayer.util.AxUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MusicService extends Service {

    private static final String TAG = "MusicService";

    private final IBinder I_BINDER = new SubStub(this);
    public static ArrayList<PlayBackTrack> mPlayList = new ArrayList<>(100);
    private SongPlayStatus mSongPlayStatus;

    @Override
    public void onCreate() {
        Log.v(TAG, "onCreate");
        super.onCreate();
        mSongPlayStatus = SongPlayStatus.getInstance(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.v(TAG, "onCreate");
        return I_BINDER;
    }


    //////////All method

    private void open(long[] list, int position, long sourceId, AxUtil.IdType idType) {

        synchronized (this){

            int mLenght = list.length;
            boolean newList = true;
            if (mLenght == mPlayList.size()){
                newList = false;
                for (int i=0;i<mLenght;i++){
                    if (list[i] != mPlayList.get(i).mId){
                        newList = true;
                        break;
                    }
                }
            }
            if (newList){
                addToPlayList(list,-1,sourceId,idType);
                mSongPlayStatus.saveSongInDb(mPlayList);
            }
        }


    }

    private void addToPlayList(long[] list, int position, long sourceId, AxUtil.IdType idType) {

        int addLenght = list.length;
        mPlayList.ensureCapacity(mPlayList.size()+addLenght);

        ArrayList<PlayBackTrack> mList = new ArrayList<>(addLenght);

        for (int i=0;i<addLenght;i++){
            mList.add(new PlayBackTrack(list[i], sourceId, idType,i));
        }

        mPlayList.addAll(mList);
        Log.v(TAG," " +mPlayList.size());


    }

    ///////////////All method

    private static final class SubStub extends MusicAIDL.Stub{
        private WeakReference<MusicService> mService;

        public SubStub(MusicService service) {
            this.mService = new WeakReference<>(service);
        }

        @Override
        public void open(long[] list, int position, long sourceId, int type) throws RemoteException {
            mService.get().open(list,position,sourceId, AxUtil.IdType.getInstance(type));
        }

        @Override
        public void play() throws RemoteException {

        }

        @Override
        public void stop() throws RemoteException {

        }
    }



}
