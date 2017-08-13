package kg.nais.service;


import kg.nais.facade.ServiceUpdateFacade;
import kg.nais.models.ServiceUpdate;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Calendar;

/**
 * Created by aziret on 7/11/17.
 */
@WebListener
public class StartUpService  implements ServletContextListener {


    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            schedulerStart();
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    private void schedulerStart() throws SchedulerException{
        System.out.println("Scheduler started...");
        Trigger chickUpdTrigger, ordersUpdTrigger,notificationsUpdTrigger;

        ServiceUpdateFacade suf = new ServiceUpdateFacade();
        ServiceUpdate su = suf.findById(1);
        java.util.Calendar now = java.util.Calendar.getInstance();
        String cronExpression = "0 0 8 1/1 * ? *";
        if(su.getUpdateTime() != null && su.getUpdateTime().split(":").length == 2){
            String[] time = su.getUpdateTime().split(":");
            cronExpression = "0 " + time[1] + " " + time[0] + " 1/1 * ? *";
        }

        if(isUpdateNeeded(now,su.getChicksLastUpd(),su.getUpdateTime()))
            new UpdateMethods().updateChickData();

        if(isUpdateNeeded(now,su.getOrdersLastUpd(),su.getUpdateTime()))
            new UpdateMethods().updateOrdersData();

        if(isUpdateNeeded(now,su.getNotificationsLastUpd(),su.getUpdateTime()))
            new UpdateMethods().updateNotificationsList();

        chickUpdTrigger = createCronTrigger("chicks",cronExpression);
        JobDetail chickUpdJob = createJob(new ChickUpdateService(),"chicks");

        ordersUpdTrigger = createCronTrigger("orders",cronExpression);
        JobDetail ordersUpdJob = createJob(new OrdersUpdateService(),"orders");

        notificationsUpdTrigger = createCronTrigger("notifications",cronExpression);
        JobDetail notificationsUpdJob = createJob(new NotificationsUpdateService(),"notifications");

        SchedulerFactory schfa = new StdSchedulerFactory();
        Scheduler sch = schfa.getScheduler();
        sch.scheduleJob(chickUpdJob,chickUpdTrigger);
        sch.scheduleJob(ordersUpdJob,ordersUpdTrigger);
        sch.scheduleJob(notificationsUpdJob,notificationsUpdTrigger);

        sch.start();

    }

    /**
     * method to determine if it's time for update
     * @param now       Calendar type, represents current datetime
     * @param updTime   String type, should provide update time in hh:mm format
     * @return          boolean type, true if current time is greater than update time, otherwise false
     */
    private boolean isUpdateTime(Calendar now, String updTime){
        int updHour = Integer.parseInt(updTime.split(":")[0]);
        int updMin = Integer.parseInt(updTime.split(":")[1]);
        return now.get(Calendar.HOUR_OF_DAY) > updHour && now.get(Calendar.MINUTE) > updMin;
    }

    private boolean isUpdateNeeded(Calendar now, Calendar lastUpd, String updTime) {
        return lastUpd == null ||
                (now.get(Calendar.DAY_OF_YEAR) - lastUpd.get(Calendar.DAY_OF_YEAR)) > 1 ||
                (now.get(Calendar.DAY_OF_YEAR) - lastUpd.get(Calendar.DAY_OF_YEAR)) == 1 &&
                        isUpdateTime(now, updTime);
    }

    private ScheduleBuilder createSchedule(String cronExpression){
        return CronScheduleBuilder.cronSchedule(cronExpression).
                withMisfireHandlingInstructionFireAndProceed();
    }

    private Trigger createCronTrigger(String name, String cronExpression){
        String triggerName = name + "UpdTrigger",
                jobName = name + "UpdJob",
                groupName = "service";
        return TriggerBuilder.newTrigger()
                .withIdentity(triggerName, groupName)
                .withSchedule(createSchedule(cronExpression))
                .forJob(jobName, groupName)
                .build();
    }

    private JobDetail createJob(Job job,String name){
        return JobBuilder.newJob(job.getClass()).withIdentity(name + "UpdJob", "service").build();
    }
}
