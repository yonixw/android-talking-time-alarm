package us.cpluspl.yonixw.talkingalarm;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final String CLOSE_AFTER_PLAY = "close_after";
    TextToSpeech english;
    TimePicker pickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pickTime = (TimePicker) findViewById(R.id.pickTime);

        // Enforce portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Set Language
        english = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    english.setLanguage(Locale.UK); // UK is more natural than US!!

                    // Only from here we can assume TextToSpeach engine is bound!
                    speakAndClose();
                }
                else  {
                    Toast.makeText(MainActivity.this, "Cant init speak " + status
                            , Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set listener
        english.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {
            }

            @Override
            public void onDone(String utteranceId) {
                MainActivity.this.resumeVolume();
                if (utteranceId.equals(CLOSE_AFTER_PLAY)){
                    finish();
                }
            }

            @Override
            public void onError(String utteranceId) {
                Log.e("TalkingClock", "error on " + utteranceId);
            }
        });

        audiManager = (AudioManager)getSystemService(getApplicationContext().AUDIO_SERVICE);


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    int currentVolume = 0;
    AudioManager audiManager;

    public void setMaxVolume() {
        currentVolume = audiManager.getStreamVolume(audiManager.STREAM_MUSIC);
        int amStreamMusicMaxVol =
                (int) (audiManager.getStreamMaxVolume(audiManager.STREAM_MUSIC) * 0.60f);

        audiManager.setStreamVolume(audiManager.STREAM_MUSIC, amStreamMusicMaxVol, 0);
    }

    public void resumeVolume() {
        audiManager.setStreamVolume(audiManager.STREAM_MUSIC, currentVolume, 0);
    }


    public void clickspeak(View view) throws InterruptedException  {
        Calendar g =  GregorianCalendar.getInstance();

        //Date trialTime = new Date();
        //g.setTime(trialTime);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            g.set(Calendar.MINUTE, pickTime.getMinute());
            g.set(Calendar.HOUR, pickTime.getHour());
        }
        else  {
            g.set(Calendar.MINUTE, pickTime.getCurrentMinute());
            g.set(Calendar.HOUR, pickTime.getCurrentHour());
        }

        Speek(g , null);
    }

    public void speakAndClose() {
        Calendar g =  GregorianCalendar.getInstance();
        Date trialTime = new Date();
        g.setTime(trialTime);

        try {
            Speek(g, CLOSE_AFTER_PLAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d("D/Yoni", "Now should speak and close");
    }

    private void Speek(Calendar g, String tag ) throws InterruptedException {
        // Get hebrew text of time:
        String timeHebrewText =  TimeSpeakingConstant.dateToText(g);

        // Set max volume
        MainActivity.this.setMaxVolume();
        Thread.sleep(200,0); // let volume adjust

        // Speak text:
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            english.speak(timeHebrewText, TextToSpeech.QUEUE_ADD, null, tag);
        }
        else  {
            english.speak(timeHebrewText, TextToSpeech.QUEUE_ADD, null);
        }
    }
}
