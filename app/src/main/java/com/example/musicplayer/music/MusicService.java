package com.example.musicplayer.music;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.musicplayer.MusicAIDL;
import com.example.musicplayer.util.AxUtil;

import java.lang.ref.WeakReference;

public class MusicService extends Service {

    private static final String TAG = "MusicService";

    private final IBinder I_BINDER = new SubStub(this);


    @Override
    public void onCreate() {
        Log.v(TAG, "onCreate");
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.v(TAG, "onCreate");
        return I_BINDER;
    }


    //////////All method

    private void open(long[] list, int position, long sourceId, AxUtil.IdType instance) {
        Log.v(TAG," " +list.length );


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
