package com.example.musicplayer.music;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.example.musicplayer.MusicAIDL;

import java.lang.ref.WeakReference;

public class MusicService extends Service {

    private final IBinder I_BINDER = new SubStub(this);


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return I_BINDER;
    }


    private static final class SubStub extends MusicAIDL.Stub{
        private WeakReference<MusicService> mService;

        public SubStub(MusicService service) {
            this.mService = new WeakReference<>(service);
        }

        @Override
        public void play() throws RemoteException {

        }

        @Override
        public void stop() throws RemoteException {

        }
    }

}
