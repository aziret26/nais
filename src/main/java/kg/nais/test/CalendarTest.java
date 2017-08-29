package kg.nais.test;

import kg.nais.tools.customCalendar.CustomCalendar;

import java.util.Calendar;

/**
 * Created by aziret on 8/28/17.
 */
public class CalendarTest {
    public static void main(String[] args) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        CustomCalendar cc1 = new CustomCalendar(c1);
        c2.add(Calendar.DAY_OF_YEAR,1);
        CustomCalendar cc2 = new CustomCalendar(c2);

//        System.out.printf("%s | %s\t%b",cc1,cc2,cc1.after(cc2),c1.before());
    }
}
