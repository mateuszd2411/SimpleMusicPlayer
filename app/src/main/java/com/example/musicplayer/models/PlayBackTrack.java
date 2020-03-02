package com.example.musicplayer.models;

import com.example.musicplayer.util.AxUtil;

public class PlayBackTrack {

    public long mId;
    public long sourceId;
    public AxUtil.IdType mIdType;
    public int mCurrentPos;

    public PlayBackTrack(long mId, long sourceId, AxUtil.IdType mIdType, int mCurrentPos) {
        this.mId = mId;
        this.sourceId = sourceId;
        this.mIdType = mIdType;
        this.mCurrentPos = mCurrentPos;
    }
}
