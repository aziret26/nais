package kg.nais.tools;

import java.time.Year;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by aziret on 7/19/17.
 */
public class BasicFunctions {
    private static Calendar today = Calendar.getInstance();
    public static int differencesInDays(Calendar c1,Calendar c2){
        return Math.max(c1.get(Calendar.DAY_OF_YEAR),c2.get(Calendar.DAY_OF_YEAR)) -
                Math.min(c1.get(Calendar.DAY_OF_YEAR),c2.get(Calendar.DAY_OF_YEAR));
    }
    public static boolean isSameDate(Calendar c1,Calendar c2){
        if(c1 == null || c2 == null)
            return false;
        return c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR) &&
                c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR);
    }

    public static String calendarToString(Calendar calendar){
        return calendar.get(Calendar.DAY_OF_MONTH)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.YEAR);
    }

    public static Calendar stringToCalendar(String date){
        Calendar calendar = Calendar.getInstance();
        String s[] = date.split("-");
        int y = Integer.parseInt(s[0]);
        int m = Integer.parseInt(s[1])-1;
        int d = Integer.parseInt(s[3]);
        calendar.set(y,m,d);
        return calendar;
    }

}
