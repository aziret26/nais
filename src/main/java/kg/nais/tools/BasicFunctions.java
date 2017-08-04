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
/*    public static HashMap<Integer, String> getMonthList(){
        HashMap<Integer, String> months= new HashMap<>();
        months.put(0,"January");
        months.put(1,"");
        months.put();
        months.put();
        months.put();
        months.put();
        months.put();
        months.put();
        months.put();
        months.put();
        months.put();
        return months;
    }
*/

    /**
     * calculate when month should starts
     * @param year
     * @param month this parameter should be called with getter method, for example: getMonth()
     *              count starts from 1
     * @return month's starting day is returned
     */
    public static int monthDaysBegin(int year,int month){
        if(today.get(Calendar.YEAR) == year &&
                today.get(Calendar.MONTH)+1 == month)
            return today.get(Calendar.DAY_OF_MONTH);
        return 1;
    }

    /**
     * calculate since when month ends
     * @param year
     * @param month this parameter should be called with getter method, for example: getMonth()
     *              count starts from 1
     * @return month's end day is returned
     */
    public static int monthDaysEnd(int year,int month){
        int length = 0;
        switch (month) {
            case 12: case 1: case 3: case 5:
            case 10: case 7: case 8:
                length = 31; break;

            case 4: case 6: case 9: case 11:
                length = 30; break;

            case 2:
                if (year % 4 == 0) {
                    length = 29;
                } else {
                    length = 28;
                }
        }
        return length;
    }

    /**
     * calculate the begin of month according to the given year
     * @param year
     * @return
     */
    public static int monthBegin(int year){
        if(today.get(Calendar.YEAR) == year)
            return today.get(Calendar.MONTH)+1;
        return 1;
    }

    /**
     * calculate the end of month according to the given year
     * @param year
     * @return
     */
    public static int monthEnd(int year){

        if(today.get(Calendar.YEAR) > year)
            return today.get(Calendar.MONTH)+1;
        return 11;
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

    public static Calendar createCalendar(int year,int month, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month-1,day);
        return calendar;
    }
}
