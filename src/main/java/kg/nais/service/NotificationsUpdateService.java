package kg.nais.service;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by aziret on 7/16/17.
 */
public class NotificationsUpdateService implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        new UpdateMethods().updateNotificationsList();
    }
}
