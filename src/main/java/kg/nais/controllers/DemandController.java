package kg.nais.controllers;

import kg.nais.facade.FeedFacade;
import kg.nais.models.Chick;
import kg.nais.models.Feed;
import kg.nais.tools.BasicFunctions;
import kg.nais.tools.customCalendar.CustomCalendar;
import kg.nais.tools.customCalendar.CustomCalendarFuture;

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
    private CustomCalendar customCalendar;

    private List<String> dates = new ArrayList<>();

    private List<Chick> chickList = new ArrayList<>();

    private OrdersController ordersController = new OrdersController();

    private HashMap<Integer, HashMap<String, Integer>> demandsMap = new HashMap<>();
    @PostConstruct
    public void init(){
        customCalendar = new CustomCalendarFuture();
        List<Feed> feed = new FeedFacade().findAll();
        for(Feed f : feed){
            demandsMap.put(f.getFeedId(),new HashMap<>());
        }
    }

    public DemandController(){
        init();
    }

    public CustomCalendar getCustomCalendar() {
        return customCalendar;
    }

    public void setCustomCalendar(CustomCalendar customCalendar) {
        this.customCalendar = customCalendar;
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
        if(customCalendar.getYear() == 0 ||
                customCalendar.getMonth() == 0 ||
                customCalendar.getDay() == 0)
            return;

        dates = new ArrayList<>();
        tillDate = customCalendar.createCalendar();
        Calendar now = Calendar.getInstance();

        while (true){
            dates.add(BasicFunctions.calendarToString(now));
            if(BasicFunctions.isSameDate(now,tillDate))
                break;
            now.add(Calendar.DAY_OF_YEAR,1);
        }

        List<Feed> feedList = new FeedFacade().findAll();
        for(Feed feed : feedList){
            List<Chick> chickList = new ChickController().getActiveChicksForFeedBelow(feed);
            if(chickList == null || chickList.size() == 0)
                continue;
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
    }

    public int getDemand(int feedId,String date){
        if(demandsMap == null ||
                demandsMap.get(feedId) == null ||
                demandsMap.get(feedId).get(date) == null)
            return 0;
        return demandsMap.get(feedId).get(date);
    }
}
