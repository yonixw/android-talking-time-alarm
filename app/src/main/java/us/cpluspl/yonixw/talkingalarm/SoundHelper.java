package us.cpluspl.yonixw.talkingalarm;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;

import java.io.File;

/**
 * Created by YoniWas on 05/02/2017.
 */
public class SoundHelper {

    public static final int MAX_STREAMS = 10;

    public  static  SoundPool makeSoundPool() {
        //http://stackoverflow.com/questions/17069955/

        SoundPool result = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            result = new SoundPool.Builder()
                    .setAudioAttributes(attributes)
                    .setMaxStreams(MAX_STREAMS)
                    .build();
        }
        else  {
            result = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0); // count, type, no-use
        }

        return  result;
    }

    public static MediaPlayer getMediaPlayer(Context context, String localFileName) {
        return  MediaPlayer.create(
                context,
                Uri.fromFile(new File (IOHelper.getStorageDir(context), localFileName))
        );
    }
}
