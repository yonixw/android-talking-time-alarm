package us.cpluspl.yonixw.talkingalarm;

import java.util.Calendar;

/**
 * Created by YoniWas on 05/02/2017.
 */
public class HebrewSpeakingConstantSounds {
    static String[] hoursWords = {
            "h12",
            "h1",
            "h2",
            "h3",
            "h4",
            "h5",
            "h6",
            "h7",
            "h8",
            "h9",
            "h10", // like m10
            "h11",
            "h12",
    };

    static String zeroWord = "00";
    static String andWord = "ve";
    static String minutesWord = "dakot";
    static String hourWord = "shaot";

    static String tensPostfix = "esre";
    static String[] minutesTensWords = {
            "empty", // No Zero word
            "m10", // like h10
            "m20",
            "m30",
            "m40",
            "m50"
    };

    public static  void addDateSounds(Calendar g, SoundHelper sh) {
        // Examples:
        // 1:52 --> one hours (and 50) and 2    minutes.
        // 1:50 --> one hours (and 50)          minutes.
        // 2:00 --> two hours (      ) (and 00) minutes

        int hour = g.get(Calendar.HOUR_OF_DAY);
        hour = (hour > 12) ? hour -12: hour;
        int minute = g.get(Calendar.MINUTE);

        // Hours
        if (hour == 0 ) {
            sh.addWAVSound(zeroWord);
        }else{
            sh.addWAVSound(hoursWords[hour]);
        }

        sh.addWAVSound(hourWord);   // shaot
        sh.addWAVSound(andWord);       // ve -

        //Minutes
        if (minute == 0 ) {
            sh.addWAVSound(zeroWord); // efes efes
        }
        else if (minute <= 10) {
            sh.addWAVSound(hoursWords[minute]); // ex. shalosh ... eser
        }
        else if (minute < 20) {
            sh.addWAVSound(hoursWords[minute % 10]); // shalosh
            sh.addWAVSound(tensPostfix);             // esere
        }
        else  {
            int minuteTenValue = minute /10;
            int minuteOnesValue = minute % 10;

            if (minuteOnesValue > 0 ) {
                sh.addWAVSound(minutesTensWords[minuteTenValue]);   // eserim
                sh.addWAVSound(andWord);                            // ve
                sh.addWAVSound(hoursWords[minuteOnesValue]);        // ex. shalosh ... tesha
            }
            else
            {
                sh.addWAVSound(minutesTensWords[minuteTenValue]);   // eserim
            }
        }

        sh.addWAVSound(minutesWord);  // dakot.
    }
}
