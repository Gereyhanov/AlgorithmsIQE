package sample.view;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import javafx.util.Duration;
import sample.Main;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller extends Main{


    private ExecutorService executor;
    private AddToQueue addToQueue;
    private Timeline timeline;
    private SequentialTransition animation;

    private ObservableList<XYChart.Series<Number, Number>> lineChartDataOne = FXCollections.observableArrayList();
    private ObservableList<XYChart.Series<Number, Number>> lineChartDataTwo = FXCollections.observableArrayList();
    private ObservableList<XYChart.Series<Number, Number>> lineChartDataThree = FXCollections.observableArrayList();

    Double count = 0.0;
    Double count2 = 0.0;

    @FXML
    JFXButton button;

    @FXML
    JFXButton buttonSpeedMore, buttonSpeedLess;

    @FXML
    LineChart lineChartViewOne = new LineChart(new NumberAxis(), new NumberAxis());
    @FXML
    LineChart lineChartViewTwo = new LineChart(new NumberAxis(), new NumberAxis());
    @FXML
    LineChart lineChartViewThree = new LineChart(new NumberAxis(), new NumberAxis());

    Double[] xStep = new Double[100], yStep = new Double[100];
    Double[] xStep2 = new Double[100], yStep2 = new Double[100];
    Double[] xStep3 = new Double[100], yStep3 = new Double[100];

    int timeRendering = 200;

    Boolean aBoolean = true;

    @FXML
    private void initialize() {
        //-- Prepare Executor Services
        executor = Executors.newCachedThreadPool();
        addToQueue=new AddToQueue();
        executor.execute(addToQueue);

        //-- Prepare Timeline
        //prepareTimeline();

        lineChartViewOne.setAnimated(false);
        lineChartViewOne.setData(lineChartDataOne);
        lineChartViewOne.createSymbolsProperty();

        lineChartViewTwo.setAnimated(false);
        lineChartViewTwo.setData(lineChartDataTwo);
        lineChartViewTwo.createSymbolsProperty();

        lineChartViewThree.setAnimated(false);
        lineChartViewThree.setData(lineChartDataThree);
        lineChartViewThree.createSymbolsProperty();

        button.setStyle("-fx-background-color: cornflowerblue; -fx-text-fill: white; -fx-font-size: 20px");
        buttonSpeedMore.setStyle("-fx-background-color: indianred; -fx-text-fill: white");
        buttonSpeedLess.setStyle("-fx-background-color: indianred; -fx-text-fill: white");
    }

    @FXML
    private void onButtonShowDiagram(){
        //-- Prepare Timeline
        if (aBoolean){
            button.setText("Stop render");
            prepareTimeline();
            aBoolean = false;
        }else {
            button.setText("Start render");
            animation.stop();
            aBoolean = true;
        }
    }

    @FXML
    private void onButtonSpeedMore(){
        if(timeRendering > 20) timeRendering = timeRendering - 10;
        animation.stop();
        prepareTimeline();
    }
    @FXML
    private void onButtonSpeedLess(){
        timeRendering = timeRendering + 10;
        animation.stop();
        prepareTimeline();
    }

    public void render(){
        lineChartDataOne.clear();
        lineChartDataTwo.clear();
        lineChartDataThree.clear();

        //----------Line one-------------------------------------------------------
        for(int i=0; i<xStep.length; i++){
            xStep[i] = count2 + 0.2;
            yStep[i] = Math.sin(count + 0.2);
            count = count + 0.2;
            count2 = count2 + 0.2;
        }
        count2 = 1.0;
        count--;

        lineChartDataOne.add(createSeriesAndSetDataDiagram(xStep, yStep, "sinOne"));

        //----------Line two-------------------------------------------------------
        for(int i=0; i<xStep2.length; i++){
            xStep2[i] = count2 + 0.2;
            yStep2[i] = Math.sin(count + 0.2);
            count = count + 0.2;
            count2 = count2 + 0.2;
        }
        count2 = 2.0;
        count--;

        lineChartDataTwo.add(createSeriesAndSetDataDiagram(xStep2, yStep2, "sinTwo"));

        //----------Line three-------------------------------------------------------
        for(int i=0; i<xStep3.length; i++){
            xStep3[i] = count2 + 0.2;
            yStep3[i] = Math.sin(count + 0.2);
            count = count + 0.2;
            count2 = count2 + 0.2;
        }
        count2 = 0.0;
        count--;

        lineChartDataThree.add(createSeriesAndSetDataDiagram(xStep3, yStep3, "sinThree"));
    }

    private class AddToQueue extends Thread {
        public void run(){
            try {
                Thread.currentThread().setName(Thread.currentThread().getId()+"-DataAdder");
                Thread.sleep(50);
                executor.execute(addToQueue);
            } catch (InterruptedException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //-- Timeline gets called in the JavaFX Main thread
    private void prepareTimeline(){
        //-- Second slower timeline
        timeline = new Timeline();
        //-- This timeline is indefinite.
        timeline.setCycleCount(Animation.INDEFINITE);

        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(timeRendering), new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent actionEvent) {
                        //TODO: Вызов функции рендеринга графиков
                        render();
                    }
                })
        );


        //-- Set Animation- Timeline is created now.
        animation = new SequentialTransition();
        animation.getChildren().addAll(timeline);
        animation.play();
    }

    public XYChart.Series <Number, Number> createSeriesAndSetDataDiagram(Double[] xStep, Double[] yStep, String nameSeries){

        final XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
        series.setName(nameSeries);

        for(int i=0; i<xStep.length; i++){
            series.getData().addAll(
                    new XYChart.Data<Number,Number>(xStep[i], yStep[i])
            );
        }
        return series;
    }

    private String parseColorToCssStyle(String color){
        String parseColor = "#" + color.substring(2, color.length());
        return parseColor;
    }
}
