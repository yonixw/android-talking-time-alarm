package us.cpluspl.yonixw.talkingalarm;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

/**
 * Created by YoniWas on 05/02/2017.
 */
public class SoundHelper {

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
                    .build();
        }
        else  {
            result = new SoundPool(10, AudioManager.STREAM_MUSIC, 0); // count, type, no-use
        }

        return  result;
    }
}
