
package com.example.musicplayer;



interface MusicAIDL {

            void open(in long[] list, int position, long sourceId, int type);
            void play();
            void stop();

}
