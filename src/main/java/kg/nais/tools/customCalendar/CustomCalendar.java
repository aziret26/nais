package kg.nais.tools.customCalendar;

//import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by aziret on 8/14/2017.
 */
public class CustomCalendar implements Comparable<CustomCalendar>,Cloneable{
    Calendar calendarInstance = Calendar.getInstance();
    private int day,year,month;
    private List<Integer> dayList = new ArrayList<>(),
            monthList = new ArrayList<>(),
            yearList = new ArrayList<>();

    public CustomCalendar() {
        yearList();
        copyFromCalendar(calendarInstance);
    }

    public CustomCalendar(CustomCalendar customCalendar) {
        yearList();
        setDay(customCalendar.getDay());
        setMonth(customCalendar.getMonth());
        setYear(customCalendar.getYear());
    }

    public CustomCalendar(int year, int month,int day) {
        calendarInstance.set(year,month-1,day);
        yearList();
        copyFromCalendar(calendarInstance);
    }

    public CustomCalendar(Calendar calendar){
        if(calendar == null)
            calendar = Calendar.getInstance();
        yearList();
        copyFromCalendar(calendarInstance);
    }

    private void yearList(){
        setYearList(new ArrayList<>());
        for (int i = 2000; i < 2030; i++){
            getYearList().add(i);
        }
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if(getYear() != year || getMonthList().size() == 0){
            int to = 12;
            setMonthList(new ArrayList<>());
            for (int i = 1; i <= to; i++) {
                getMonthList().add(i);
            }
        }
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        if(month > 0 && month < 13)
            this.month = month;
        int to = monthDaysEnd(getYear(), month);
        setDayList(new ArrayList<>());
        for (int i = 1; i <= to; i++) {
            getDayList().add(i);
        }
    }

    public List<Integer> getDayList() {
        return dayList;
    }

    public void setDayList(List<Integer> dayList) {
        this.dayList = dayList;
    }

    public List<Integer> getMonthList() {
        return monthList;
    }

    public void setMonthList(List<Integer> monthList) {
        this.monthList = monthList;
    }

    public List<Integer> getYearList() {
        return yearList;
    }

    public void setYearList(List<Integer> yearList) {
        this.yearList = yearList;
    }

    /**
     * calculate since when month ends
     * @param year
     * @param month this parameter should be called with getter method, for example: getMonth()
     *              count starts from 1
     * @return month's end day is returned
     */
    public int monthDaysEnd(int year,int month){
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
     * calculate when month should starts
     * @param year
     * @param month this parameter should be called with getter method, for example: getMonth()
     *              count starts from 1
     * @return month's starting day is returned
     */
    public int monthDaysBegin(int year,int month){
        if(calendarInstance.get(Calendar.YEAR) == year &&
                calendarInstance.get(Calendar.MONTH)+1 == month)
            return calendarInstance.get(Calendar.DAY_OF_MONTH);
        return 1;
    }

    /**
     * calculate the begin of month according to the given year
     * @param year
     * @return
     */
    public int monthBegin(int year){
        if(calendarInstance.get(Calendar.YEAR) == year)
            return calendarInstance.get(Calendar.MONTH)+1;
        return 1;
    }

    /**
     * calculate the end of month according to the given year
     * @param year
     * @return
     */
    public int monthEnd(int year){
        if(calendarInstance.get(Calendar.YEAR) > year)
            return calendarInstance.get(Calendar.MONTH)+1;
        return 11;
    }

    public Calendar createCalendar(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month-1,day);
        return calendar;
    }

    public static Calendar createCalendar(int year,int month, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month-1,day);
        return calendar;
    }

    public static Calendar convertToCalendar(CustomCalendar customCalendar){
        Calendar calendar = Calendar.getInstance();
        calendar.set(customCalendar.getYear(),customCalendar.getMonth()-1,customCalendar.getDay());
        return calendar;
    }

    public static CustomCalendar convertFromCalendar(Calendar calendar){
        CustomCalendar cc = new CustomCalendar();
        cc.setYear(calendar.get(Calendar.YEAR));
        cc.setMonth(calendar.get(Calendar.MONTH)+1);
        cc.setDay(calendar.get(Calendar.DAY_OF_MONTH));
        return cc;
    }

    public CustomCalendar copyFromCalendar(Calendar calendar){
        setYear(calendar.get(Calendar.YEAR));
        setMonth(calendar.get(Calendar.MONTH)+1);
        setDay(calendar.get(Calendar.DAY_OF_MONTH));
        return this;
    }

    public boolean before(){
        CustomCalendar now = new CustomCalendar();
        return before(now);
    }

    public boolean after(){
        CustomCalendar now = new CustomCalendar();
        return after(now);
    }

    public boolean before(CustomCalendar cc){
        if(cc == null)
            return false;
        return compareTo(cc) > 0;
    }

    public boolean after(CustomCalendar cc){
        if(cc == null)
            return false;
        return compareTo(cc) < 0;
    }

    public boolean before(Calendar calendar){
        if(calendar == null)
            return false;
        CustomCalendar cs = new CustomCalendar(calendar);
        return before(cs);
    }

    public boolean after(Calendar calendar){
        if(calendar == null)
            return false;
        CustomCalendar cs = new CustomCalendar(calendar);
        return after(cs);
    }

    /**
     * returns value 0, if given argument represents same date does
     * returns value less than 0, if given argument represents date after than this CustomCalendar class does
     * returns value greater than 0, if given argument represents date before than this CustomCalendar class does
     * @param o
     * @return
     */
    @Override
    public int compareTo(CustomCalendar o) {
//        public int compareTo(@NotNull CustomCalendar o) {
        if(getYear() < o.getYear()) return -1;

        if(getYear() > o.getYear()) return 1;

        if(getMonth() < o.getMonth()) return -1;

        if(getMonth() > o.getMonth()) return 1;

        if(getDay() < o.getDay()) return -1;

        if(getDay() > o.getDay()) return 1;

        return 0;
    }

    public void addYear(){
        addYears(1);
    }

    public void addYears(int y){
        Calendar calendar = convertToCalendar(this);
        calendar.add(Calendar.YEAR, y);
        copyFromCalendar(calendar);
    }

    public void addMonth(){
        addMonths(1);
    }

    public void addMonths(int m){
        Calendar calendar = convertToCalendar(this);
        calendar.add(Calendar.MONTH, m);
        copyFromCalendar(calendar);

    }

    public void addDay(){
        addDays(1);
    }

    public void addDays(int d){
        Calendar calendar = convertToCalendar(this);
        calendar.add(Calendar.DAY_OF_YEAR, d);
        copyFromCalendar(calendar);
    }

    @Override
    public boolean equals(Object obj) {
        if(! (obj instanceof CustomCalendar)) return false;
        CustomCalendar cc = (CustomCalendar) obj;
        if(cc.getYear() == getYear() &&
                cc.getMonth() == getMonth() &&
                cc.getDay() == getDay())
            return true;
        return  false;
    }

    public int differenceInDays(Calendar calendar){
        return differenceInDays(new CustomCalendar(calendar));
    }

    public int differenceInDays(CustomCalendar cc){
        CustomCalendar past = new CustomCalendar(), future = new CustomCalendar();

        if(compareTo(cc) < 0){
            future = (CustomCalendar) cc.clone();  past = (CustomCalendar) clone();
        }else
        if(compareTo(cc) > 0){
            future = (CustomCalendar) clone();  past = (CustomCalendar) cc.clone();
        }else
        if(compareTo(cc) == 0){
            return 0;
        }
        int difference = 0;
        if(past.getYear() == future.getYear() && past.getMonth() == future.getMonth()){
            return future.getDay() - past.getDay();
        }
        difference += monthDaysEnd(past.getYear(),past.getMonth()) - past.getDay();
        past.addMonth();
        while (true){
            if(past.getYear() == future.getYear() && past.getMonth() == future.getMonth()){
                difference += future.getDay();
                return difference;
            }
            difference += past.monthDaysEnd(past.getYear(),past.getMonth());
            past.addMonth();
        }
    }

    public Object clone(){
        try {
            CustomCalendar other = (CustomCalendar) super.clone();
            other.setDay(getDay());
            other.setMonth(getMonth());
            other.setYear(getYear());

            other.setYearList(getYearList());

            return other;
        }
        catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }
    }

    @Override
    public String toString() {
        return month+"-"+day+"-"+year;
    }
}
