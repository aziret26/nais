package kg.nais.service;

import kg.nais.controllers.ChickController;
import kg.nais.controllers.ChickFeedConsumeController;
import kg.nais.facade.OrderFacade;
import kg.nais.models.Chick;
import kg.nais.models.Orders;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

/**
 * Created by aziret on 7/12/17.
 */
public class OrdersUpdateService implements Job {
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        new UpdateMethods().updateOrdersData();
    }

}
