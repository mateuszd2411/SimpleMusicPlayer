package com.example.musicplayer.music;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.musicplayer.MusicAIDL;
import com.example.musicplayer.util.AxUtil;

import java.util.WeakHashMap;

public class PlayerServices {

    public static MusicAIDL mRemot = null;


    private static final WeakHashMap<Context, ServiceBinder> mHashMap;

    static {
        mHashMap = new WeakHashMap<>();
    }


    public static final ServiceToken bindToService(Context context, ServiceConnection serviceConnection){

        Activity realActivity = ((Activity) context).getParent();
        if (realActivity==null){
            realActivity = (Activity) context;
        }

        ContextWrapper mWrapper = new ContextWrapper(realActivity);
        mWrapper.startService(new Intent(mWrapper,MusicService.class));
        ServiceBinder binder = new ServiceBinder(serviceConnection, mWrapper.getApplicationContext());

        if (mWrapper.bindService(new Intent().setClass(mWrapper,MusicService.class),binder,0)){
            mHashMap.put(mWrapper,binder);
            return new ServiceToken(mWrapper);
        }

        return null;
    }

    public static void unbindToService(ServiceToken token){
        if (token==null){
            return;
        }

        ContextWrapper mWrapper = token.contextWrapper;
        ServiceBinder binder = mHashMap.remove(mWrapper);
        if (binder==null){
            return;
        }

        mWrapper.unbindService(binder);
        if (mHashMap.isEmpty()){
            binder = null;
        }
    }


    /////all method////////////////


    public static void playAll(long[] list, int position, long sourceId, AxUtil.IdType type) throws RemoteException {

        if (list.length==0 && list == null && mRemot ==null){
            return;
        }

        mRemot.open(list,position,sourceId,type.mId);

    }



    /////////all method/////////end



    public static class ServiceToken{
        private ContextWrapper contextWrapper;

        public ServiceToken(ContextWrapper contextWrapper) {
            this.contextWrapper = contextWrapper;
        }
    }
    public static final class ServiceBinder implements ServiceConnection {
        private ServiceConnection mService;
        private Context mContext;

        public ServiceBinder(ServiceConnection mService, Context mContext) {
            this.mService = mService;
            this.mContext = mContext;
        }

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            mRemot = MusicAIDL.Stub.asInterface(iBinder);
            if (mService!=null){
                mService.onServiceConnected(componentName,iBinder);
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

            if (mService!= null){
                mService.onServiceDisconnected(componentName);
            }

            mRemot =null;

        }
    }
}
