package kg.nais.controllers;

import kg.nais.facade.ClientFacade;
import kg.nais.facade.ClientSurveyFacade;
import kg.nais.models.Client;
import kg.nais.models.ClientSurvey;
import org.primefaces.model.chart.*;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@ManagedBean
@ViewScoped
public class ClientChartController extends GeneralController  implements Serializable {
    private ClientSurveyFacade clientSurveyFacade;
    private ClientFacade clientFacade;
    private List<ClientSurvey> surveyList;
    private LineChartModel eggLayingChart,eggWeightChart,chickenWeightChart,feedWeightChart;

    @ManagedProperty(value = "#{sessionController}")
    private SessionController sessionController;

    @PostConstruct
    public void init(){
        eggLayingChart = new LineChartModel();
        eggWeightChart = new LineChartModel();
        chickenWeightChart = new LineChartModel();
        feedWeightChart = new LineChartModel();
        surveyList = new ArrayList<>();
        if(clientId == 0 && sessionController.getUser().isClient()){
            clientId = sessionController.getUser().getClient().getClientId();
        }
        if(clientId == 0){
            return;
        }else{
            clientSurveyFacade = new ClientSurveyFacade();
            clientFacade = new ClientFacade();
            Client client = clientFacade.findById(clientId);
            surveyList = clientSurveyFacade.findByClient(client);

            if(surveyList == null || surveyList.size() == 0){
                return;
            }
        }
        createLineModels();
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

    @Override
    public void setClientId(int clientId) {
        super.setClientId(clientId);
        clientSurveyFacade = new ClientSurveyFacade();
        clientFacade = new ClientFacade();
        Client client = clientFacade.findById(clientId);
        surveyList = clientSurveyFacade.findByClient(client);

        if(surveyList == null || surveyList.size() == 0){
            return;
        }
        createLineModels();
    }

    public LineChartModel getEggLayingChart() {
        return eggLayingChart;
    }

    public LineChartModel getEggWeightChart() {
        return eggWeightChart;
    }

    public LineChartModel getChickenWeightChart() {
        return chickenWeightChart;
    }

    public LineChartModel getFeedWeightChart() {
        return feedWeightChart;
    }

    private void createLineModels(){
        eggLayingChart = fillEggLayingChart();

        eggLayingChart.setTitle("Egg Laying");
        eggLayingChart.setShowPointLabels(true);
        eggLayingChart.getAxes().put(AxisType.X, new CategoryAxis("Time"));
        Axis yAxis = eggLayingChart.getAxis(AxisType.Y);
        yAxis.setLabel("Percentage %");
        yAxis.setMin(0);
        yAxis.setMax(110);

        eggWeightChart = fillEggWeightChart();
        eggWeightChart.setTitle("Egg Weight");
        eggWeightChart.setShowPointLabels(true);
        eggWeightChart.getAxes().put(AxisType.X, new CategoryAxis("Time"));
        yAxis = eggWeightChart.getAxis(AxisType.Y);
        yAxis.setLabel("Weight in grams");
        yAxis.setMin(20);
        yAxis.setMax(100);


        chickenWeightChart = fillEggWeightChart();
        chickenWeightChart.setTitle("Chicken weight");
        chickenWeightChart.setShowPointLabels(true);
        chickenWeightChart.getAxes().put(AxisType.X, new CategoryAxis("Time"));
        yAxis = chickenWeightChart.getAxis(AxisType.Y);
        yAxis.setLabel("Weight in grams");
        yAxis.setMin(20);
        yAxis.setMax(100);

        feedWeightChart = fillEggWeightChart();
        feedWeightChart.setTitle("Feed Weight");
        feedWeightChart.setShowPointLabels(true);
        feedWeightChart.getAxes().put(AxisType.X, new CategoryAxis("Time"));
        yAxis = feedWeightChart.getAxis(AxisType.Y);
        yAxis.setLabel("Weight in grams");
        yAxis.setMin(20);
        yAxis.setMax(100);
    }

    private LineChartModel fillEggLayingChart(){
        LineChartModel model = new LineChartModel();

        LineChartSeries series = new LineChartSeries();
        series.setLabel("Egg Laying");

        surveyList.forEach( cs ->addToLineChart(series,cs.getSurveyDate(),cs.getEggLaying()));

        model.addSeries(series);

        return model;
    }

    private LineChartModel fillEggWeightChart(){
        LineChartModel model = new LineChartModel();

        LineChartSeries series = new LineChartSeries();
        series.setLabel("Egg Weight");

        surveyList.forEach( cs ->addToLineChart(series,cs.getSurveyDate(),cs.getEggAvgWeight()));

        model.addSeries(series);

        return model;
    }

    private LineChartModel fillChickenWeightChart(){
        LineChartModel model = new LineChartModel();

        LineChartSeries series = new LineChartSeries();
        series.setLabel("Chicken weight");

        surveyList.forEach( cs ->addToLineChart(series,cs.getSurveyDate(),cs.getChickenAvgWeight()));

        model.addSeries(series);

        return model;
    }
    private LineChartModel fillFeedConsumptionChart(){
        LineChartModel model = new LineChartModel();

        LineChartSeries series = new LineChartSeries();
        series.setLabel("Feed consumption");

        surveyList.forEach( cs ->addToLineChart(series,cs.getSurveyDate(),cs.getFeedConsumption()));

        model.addSeries(series);

        return model;
    }
    private void addToLineChart( LineChartSeries series, Calendar calendar,Double y){
        String x = (calendar.get(Calendar.DAY_OF_MONTH) + "-"
                + calendar.get(Calendar.MONTH) + 1) + "-"
                + calendar.get(Calendar.YEAR);
        series.set(x,y);
    }

}
