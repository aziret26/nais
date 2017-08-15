package kg.nais.tools.customCalendar;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by azire on 8/14/2017.
 */
public class CustomCalendarFuture extends CustomCalendar {
    public CustomCalendarFuture() {
        setYearList(new ArrayList<>());
        for (int i = calendarInstance.get(Calendar.YEAR); i < 2030; i++){
            getYearList().add(i);
        }
        setYear(calendarInstance.get(Calendar.YEAR));
        setMonth(calendarInstance.get(Calendar.MONTH)+1);
        setDay(calendarInstance.get(Calendar.DAY_OF_MONTH));
    }

    public void setYear(int year) {
        if(getYear() != year || getMonthList().size() == 0){
            super.setYear(year);
            int from = monthBegin(year);
            int to = 12;
            setMonthList(new ArrayList<>());
            for (int i = from; i <= to; i++) {
                getMonthList().add(i);
            }
        }
    }

    public void setMonth(int month) {
        if(getDayList().size() == 0 || month != getMonth()) {
            super.setMonth(month);
            int from = monthDaysBegin(getYear(),month);
            int to = monthDaysEnd(getYear(), month);
            setDayList(new ArrayList<>());
            for (int i = from; i <= to; i++) {
                getDayList().add(i);
            }
        }
    }
}
