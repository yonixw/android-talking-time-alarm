package us.cpluspl.yonixw.talkingalarm;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by YoniWas on 05/02/2017.
 */
public class SoundHelper {

    Context myContext;
    ArrayList<MediaPlayer> loadedSounds = new ArrayList<MediaPlayer>() ;

    public SoundHelper(Context context) {
        myContext =context;
    }

    private MediaPlayer getMediaPlayer( String localFileName) {
        return  MediaPlayer.create(
                myContext,
                Uri.fromFile(new File (IOHelper.getStorageDir(myContext), localFileName))
        );
    }

    private void addSound(String shortName) {
        loadedSounds.add(getMediaPlayer(shortName));
    }

    public void addWAVSound(String shortName) {
        addSound(shortName + ".wav");
    }

    public void releaseAllSounds() {
        for (MediaPlayer m : loadedSounds) {
            m.release();
        }
        loadedSounds.clear();
    }

    public void playAllAsync() {
        Thread r = new Thread() {


            @Override
            public void  run() {
                int counter = 0;
                for (MediaPlayer m : loadedSounds) {
                    Log.d(MainActivity.LOG_TAG, "Playing sound: " + counter);
                    m.start();
                    while (m.isPlaying()) {}
                    counter++;
                }

                raisePlayBackFinishEvent();
            }
         };


        r.start();
    }

    // Event when all activites done.
    public interface PlaybackFinishListener {
        public void onPlaybackFinish() ;
    }

    private PlaybackFinishListener myPlaybackFinishListener = null;
    public void setPlaybackFinishListener(PlaybackFinishListener listener) {
        myPlaybackFinishListener = listener;
    }

    private void raisePlayBackFinishEvent() {
        Log.d(MainActivity.LOG_TAG, "Playback Finished!");
        if(myPlaybackFinishListener !=null) {
            myPlaybackFinishListener.onPlaybackFinish();
        }
    }
}
