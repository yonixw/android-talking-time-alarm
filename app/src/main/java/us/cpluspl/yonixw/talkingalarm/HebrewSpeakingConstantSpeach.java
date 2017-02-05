package us.cpluspl.yonixw.talkingalarm;


        import android.util.Log;

        import java.util.Calendar;
        import java.util.Date;
        import java.util.GregorianCalendar;
        import java.util.StringTokenizer;


/**
 * Created by YoniWas on 06/02/2017.
 */
public class HebrewSpeakingConstantSpeach {

    static String[] hoursWord = {
            "eeffess",
            "ehhaad",
            "shtaeeim",
            "shalosh",
            "arrba",
            "hhamesh",
            "shesh",
            "shaeeva",
            "shmonaa",
            "teshaa",
            "esserr",
            "ehhaad esserr",
            "shteam esseree"
    };

    static String zeroWord = "eeffess eeffess";
    static String andWord = " vea ";
    static String minutesWord = "dakot";
    static String hourWord = "shahot";

    static String[] minutesTensWord = {
            " ", // No Zero word
            "esserr",
            "esseerream",
            "shhloshhim",
            "arbbaeam",
            "ha mishim"
    };

    public static String dateToText(Calendar g) {
        String result = "";

        int hour = g.get(Calendar.HOUR_OF_DAY);
        hour = (hour > 12) ? hour -12: hour;
        int minute = g.get(Calendar.MINUTE);

        // Hours
        if (hour == 0 ) {
            result += zeroWord;
        }else{
            result += hoursWord[hour];
        }

        result += hourWord + andWord;

        // Minutes:
        if (minute == 0 ) {
            result += zeroWord ;
        }
        else if (minute < 10) {
            result += hoursWord[minute];
        }
        else  {
            int minuteTenValue = minute /10;
            int minuteOnesValue = minute % 10;

            if (minuteOnesValue > 0 ) {
                result += minutesTensWord[minuteTenValue] + andWord + hoursWord[minuteOnesValue];
            }
            else
            {
                result += minutesTensWord[minuteTenValue];
            }
        }

        result +=  " " +  minutesWord;

        return  result;
    }
}
