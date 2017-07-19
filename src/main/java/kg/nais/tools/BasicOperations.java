package kg.nais.tools;

import java.util.Calendar;

/**
 * Created by aziret on 7/19/17.
 */
public class BasicOperations {
    public static int differencesInDays(Calendar c1,Calendar c2){
        return Math.max(c1.get(Calendar.DAY_OF_YEAR),c2.get(Calendar.DAY_OF_YEAR)) -
                Math.min(c1.get(Calendar.DAY_OF_YEAR),c2.get(Calendar.DAY_OF_YEAR));
    }
    public static boolean isSameDate(Calendar c1,Calendar c2){
        if(c1 == null || c2 == null)
            return false;
        return c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR);
    }
}
