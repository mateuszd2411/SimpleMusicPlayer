package com.example.musicplayer.music;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.musicplayer.MusicAIDL;
import com.example.musicplayer.models.PlayBackTrack;
import com.example.musicplayer.songdb.SongPlayStatus;
import com.example.musicplayer.util.AxUtil;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MusicService extends Service {

    private static final String TAG = "MusicService";
    private static final int SERVIRE_DIE = 10;
    private static final int FADE_UP = 11;
    private static final int FADE_DOWN = 12;
    private static final int FOCUSE_CHANGE = 12;

    private final IBinder I_BINDER = new SubStub(this);
    public static ArrayList<PlayBackTrack> mPlayList = new ArrayList<>(100);
    private SongPlayStatus mSongPlayStatus;
    private int mPlayPos = -1;

    @Override
    public void onCreate() {
        Log.v(TAG, "onCreate");
        super.onCreate();
        mSongPlayStatus = SongPlayStatus.getInstance(this);
        mPlayList = mSongPlayStatus.getSongToDb();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.v(TAG, "onBind");
        return I_BINDER;
    }


    //////////All method

    private void open(long[] list, int position, long sourceId, AxUtil.IdType idType) {

        synchronized (this){

            int mLenght = list.length;
            boolean newList = true;
            if (mPlayList.size() == mLenght){
                newList = false;
                Log.v(TAG, " " + mPlayList.size());
                for (int i=0; i<mLenght; i++){

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

            if (position >= 0){
                mPlayPos = position;
            }
        }


    }

    private void addToPlayList(long[] list, int position, long sourceId, AxUtil.IdType idType) {

        int addLenght = list.length;
        if (position < 0){
            mPlayList.clear();
            position = 0;
        }
        mPlayList.ensureCapacity(mPlayList.size()+addLenght);
        if (position > mPlayList.size()){
            position = mPlayList.size();
        }

        ArrayList<PlayBackTrack> mList = new ArrayList<>(addLenght);

        for (int i=0; i< addLenght; i++){
            mList.add(new PlayBackTrack(list[i], sourceId, idType,i));
        }

        mPlayList.addAll(mList);



    }


    public long getAudioId() {
        PlayBackTrack track = getCurrentTruck();
        if (track != null){
            return track.mId;
        }
        return -1;

    }

    public PlayBackTrack getCurrentTruck() {
        return getTrack(mPlayPos);
    }

    public synchronized PlayBackTrack getTrack(int index) {
        if (index != -1 && index < mPlayList.size()){
            return  mPlayList.get(index);
        }
        return null;
    }

    private int getQueuePosition() {
        synchronized (this) {
            return mPlayPos;
        }
    }

    public long[] getsaveIdList() {
        synchronized (this) {
            long[] idL = new long[mPlayList.size()];
            for (int i = 0; i < mPlayList.size(); i++){
                idL[i] = mPlayList.get(i).mId;
            }
            return idL;
        }

    }

    ///////////////All method

    ////mediaplayer


    public class MyMidea implements MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener{

        private WeakReference<MusicService> mService;
        private MediaPlayer mMediaPlayer = new MediaPlayer();
        private boolean mIsinitialized = false;
        private Handler mHandler;
        private float mVolume;


        public MyMidea(MusicService service) {
            this.mService = new WeakReference<>(service);
        }

        public void setDataSource(String path){
            mIsinitialized = setDataPath(mMediaPlayer,path);
        }

        private boolean setDataPath(MediaPlayer mMediaPlayer, String path) {

            try {
                mMediaPlayer.reset();
                mMediaPlayer.setOnPreparedListener(null);
                if (path.startsWith("content://")){
                    mMediaPlayer.setDataSource(mService.get(), Uri.parse(path));
                } else {
                    mMediaPlayer.setDataSource(path);
                }

                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mMediaPlayer.prepare();
                mMediaPlayer.setOnErrorListener(this);
                mMediaPlayer.setOnCompletionListener(this);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        }

        public boolean mInitialized(){
            return mIsinitialized;
        }

        private void setHandeler(Handler handeler){
            mHandler = handeler;
        }

        public void start(){
            mMediaPlayer.start();
        }

        public void stop(){
            mMediaPlayer.stop();
            mIsinitialized = false;
        }

        public void pause(){
            mMediaPlayer.pause();
        }

        public void release(){
            stop();
            mMediaPlayer.release();
        }



        public long duration(){
            if (mMediaPlayer!=null && mInitialized()){
                return mMediaPlayer.getDuration();
            }
            return -1;
        }


        public long position(){
            if (mMediaPlayer!=null && mInitialized()) {
                return mMediaPlayer.getCurrentPosition();
            }
            return 0;
        }


        public void setVolume(float vol){
            mMediaPlayer.setVolume(vol,vol);
            mVolume = vol;
        }

        public long seek(long whereto){
            mMediaPlayer.seekTo((int) whereto);
            return whereto;
        }





        @Override
        public void onCompletion(MediaPlayer mp) {

        }

        @Override
        public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {

            switch (what){
                case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                    mIsinitialized = false;
                        mediaPlayer.release();
                        mediaPlayer = new MediaPlayer();
                        Message message = mHandler.obtainMessage(SERVIRE_DIE);
                        mHandler.sendMessageDelayed(message,2000);
                        break;
                        default:
                            break;
            }

            return false;
        }
    }


    //// MediaPlayer

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

        @Override
        public long getAudioId() throws RemoteException {
            return mService.get().getAudioId();
        }

        @Override
        public int getCurrentPos() throws RemoteException {
            return mService.get().getQueuePosition();
        }

        @Override
        public long[] getsaveIdList() throws RemoteException {
            return mService.get().getsaveIdList();
        }
    }




}
