package kg.nais.controllers;

import kg.nais.facade.ChickFacade;
import kg.nais.models.Chick;
import kg.nais.models.Feed;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by aziret on 7/17/17.
 */
@ManagedBean
@ViewScoped
public class DemandController {
    private Calendar today = Calendar.getInstance();
    private int tillDay = today.get(Calendar.DAY_OF_MONTH),
                tillMonth = today.get(Calendar.MONTH),
                tillYear = today.get(Calendar.YEAR);

    @PostConstruct
    public void init(){
        setTillYear(today.get(Calendar.YEAR));
        setTillMonth(today.get(Calendar.MONTH));
        setTillDay(today.get(Calendar.DAY_OF_MONTH));
    }

    private List<Integer> dayList,monthList, yearList;

    private List<String> dates = new ArrayList<>();

    private List<Chick> chickList;

    //<getters and setter for dates>
    public int getTillDay() {
        return tillDay;
    }

    public void setTillDay(int tillDay) {
        this.tillDay = tillDay;
    }

    public int getTillMonth() {
        return tillMonth;
    }

    public void setTillMonth(int tillMonth) {
        this.tillMonth = tillMonth;
        int length = 0;
        switch (tillMonth){
            case 12: case 1: case 3: case 5:
            case 10: case 7: case 8:
                length = 31; break;

            case 4: case 6: case 9: case 11:
                length = 30; break;

            case 2:
                if(tillYear%4==0){
                    length = 29;
                }else {
                    length = 28;
                }
        }
        int from = 1;
        Calendar now = Calendar.getInstance();
        if(tillYear == now.get(Calendar.YEAR) &&
                tillMonth == now.get(Calendar.MONTH)){
            from = now.get(Calendar.DAY_OF_MONTH)+1;
        }
        for (int i =from; i <= length; i++) {
            dayList.add(i);
        }
    }

    public int getTillYear() {
        return tillYear;
    }

    public void setTillYear(int tillYear) {
        Calendar now = Calendar.getInstance();
        if(tillYear == now.get(Calendar.YEAR)){
            int from = now.get(Calendar.MONTH)+1;
            int to = 12;
            for (int i = from; i <= to; i++) {
                dayList.add(i);
            }
        }
        this.tillYear = tillYear;
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
    //</getters and setter for dates>




    public List<Chick> chooseChicksForFeedByDate(List<Chick> chickList,Feed feed, Calendar calendar){
        List<Chick> resultList = new ArrayList<>();
        return chickList;
    }

    public void calculateDemand(){

    }


}
