package kg.nais.controllers;

import kg.nais.facade.ChickFacade;
import kg.nais.facade.FeedFacade;
import kg.nais.facade.OrderFacade;
import kg.nais.models.Chick;
import kg.nais.models.Feed;
import kg.nais.models.Orders;
import kg.nais.tools.BasicFunctions;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by aziret on 7/17/17.
 */
@ManagedBean
@ViewScoped
public class DemandController {
    private Calendar today = Calendar.getInstance(),tillDate = Calendar.getInstance();
    private int tillDay,tillMonth,tillYear;

    private List<Integer> dayList = new ArrayList<>(),
                    monthList = new ArrayList<>(),
                    yearList = new ArrayList<>();

    private List<String> dates = new ArrayList<>();

    private List<Chick> chickList = new ArrayList<>();

    private OrdersController ordersController = new OrdersController();

    private HashMap<Integer, HashMap<String, Integer>> demandsMap = new HashMap<>();
    @PostConstruct
    public void init(){
        yearList = new ArrayList<>();
        for (int i = today.get(Calendar.YEAR);i < 2030;i++){
            getYearList().add(i);
        }
        setTillYear(today.get(Calendar.YEAR));
        setTillMonth(today.get(Calendar.MONTH)+1);
        setTillDay(today.get(Calendar.DAY_OF_MONTH));

        List<Feed> feed = new FeedFacade().findAll();
        for(Feed f : feed){
            demandsMap.put(f.getFeedId(),new HashMap<>());
        }
    }

    public DemandController(){
        init();
    }

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
        if(dayList.size() == 0 || tillMonth != this.tillMonth) {
            this.tillMonth = tillMonth;
            int from = BasicFunctions.monthDaysBegin(tillYear,tillMonth);
            int to = BasicFunctions.monthDaysEnd(tillYear, tillMonth);
            dayList = new ArrayList<>();
            for (int i = from; i <= to; i++) {
                dayList.add(i);
            }
        }
    }

    public int getTillYear() {
        return tillYear;
    }

    public void setTillYear(int tillYear) {
        if(this.tillYear != tillYear || monthList.size() == 0){
            int from = BasicFunctions.monthBegin(tillYear);
            int to = 12;
            monthList = new ArrayList<>();
            for (int i = from; i <= to; i++) {
                monthList.add(i);
            }
            this.tillYear = tillYear;
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

    public Calendar getTillDate() {
        return tillDate;
    }

    public void setTillDate(Calendar tillDate) {
        this.tillDate = tillDate;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    public HashMap<Integer, HashMap<String, Integer>> getDemandsMap() {
        return demandsMap;
    }

    public void setDemandsMap(HashMap<Integer, HashMap<String, Integer>> demandsMap) {
        this.demandsMap = demandsMap;
    }

    public void processDemand(){
        if(tillYear == 0 || tillMonth == 0 || tillDay == 0)
            return;

        dates = new ArrayList<>();
        tillDate = BasicFunctions.createCalendar(tillYear,tillMonth,tillDay);
        Calendar now = Calendar.getInstance();

        while (true){
            dates.add(BasicFunctions.calendarToString(now));
            if(BasicFunctions.isSameDate(now,tillDate))
                break;
            now.add(Calendar.DAY_OF_YEAR,1);
        }

        List<Feed> feedList = new FeedFacade().findAll();
        for(Feed feed : feedList){
            System.out.println("calculating for fid: "+feed.getFeedId());
            List<Chick> chickList = new ChickController().getActiveChicksForFeedBelow(feed);
            printChicks(chickList);
            calculateDemand(chickList,feed,tillDate);
        }
    }

    private void calculateDemand(List<Chick> chickList, Feed feed, Calendar date){
        double amount = 0;
        ChickFeedConsumeController consumeController = new ChickFeedConsumeController();
        ChickController chickController = new ChickController();
        Calendar now = Calendar.getInstance();
        OrdersController ordersController = new OrdersController();


        while(true){
            System.out.println("now: "+BasicFunctions.calendarToString(now));
            List<Chick> chickListForCurrentFeed = chickController.getChicksForFeed(chickList,feed);

            for(Chick chick : chickListForCurrentFeed) {
                if(ordersController.hasResourcesByDate(chick.getClient(),feed,now))
                    continue;
                amount += consumeController.getConsumeAmount(chick);
            }
            demandsMap.get(feed.getFeedId()).put(BasicFunctions.calendarToString(now),(int) amount);

            if(BasicFunctions.isSameDate(now,date))
                break;
            now.add(Calendar.DAY_OF_YEAR,1);
            chickList.forEach(chickController::increaseChicksAgeByDay);
        }
        System.out.println("--------\n");
    }

    public int getDemand(int feedId,String date){
        return demandsMap.get(feedId).get(date);
    }

    private void printChicks(List<Chick> chicks){
        System.out.println("print chick list");
        for(Chick c: chicks){
            System.out.printf("cid: %d \t cAge: %d \t cfid: %d \t cdob: %s",
                    c.getChickId(),c.getAge(),c.getFeed().getFeedId(),BasicFunctions.calendarToString(c.getDob()));
        }
    }
}
