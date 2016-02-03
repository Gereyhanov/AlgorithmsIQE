package sample.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.effect.FloatMap;
import javafx.scene.layout.Pane;

import javafx.util.Duration;
import sample.Main;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller extends Main{


    private ExecutorService executor;
    private AddToQueue addToQueue;
    private Timeline timeline2;
    private SequentialTransition animation;

    private ObservableList<XYChart.Series<Number, Number>> lineChartData = FXCollections.observableArrayList();

    private AreaChart.Series<Number, Number> series1 = new AreaChart.Series<Number, Number>();
    private AreaChart.Series<Number, Number> series2 = new AreaChart.Series<Number, Number>();

    Timer uploadCheckerTimer = new Timer(true);

    Double count = 0.0;
    Double count2 = 0.0;

    @FXML
    JFXButton button;

    @FXML
    JFXTextField textEditOne;

    @FXML
    JFXTextField textEditTwo;


    @FXML
    LineChart areaChartView = new LineChart(
            new NumberAxis(), new NumberAxis());

    @FXML
    Pane root;

    @FXML
    JFXColorPicker colorPicker = new JFXColorPicker();

    Double[] xStep = new Double[100], yStep = new Double[100];
    Double[] xStep2 = new Double[100], yStep2 = new Double[100];
    Double[] xStep3 = new Double[100], yStep3 = new Double[100];

    int time = 200;


    @FXML
    private void initialize() {

        //-- Prepare Executor Services
        executor = Executors.newCachedThreadPool();
        addToQueue=new AddToQueue();
        executor.execute(addToQueue);


        //-- Prepare Timeline
        prepareTimeline();

        for(int i=0; i<xStep.length; i++){
            xStep[i] = count2 + 0.2;
            yStep[i] = Math.sin(count + 0.2);
            count = count + 0.2;
            count2 = count2 + 0.2;
        }

        areaChartView.setAnimated(false);

        series1.setName("Series 1");



        lineChartData.add(series1);

        areaChartView.setData(lineChartData);
        areaChartView.createSymbolsProperty();

        button.setStyle("-fx-background-color: cornflowerblue");

       // setUploadCheckerTimer();

    }

    public void render(){
        lineChartData.clear();

        for(int i=0; i<xStep.length; i++){
            xStep[i] = count2 + 0.2;
            yStep[i] = Math.sin(count + 0.2);
            count = count + 0.2;
            count2 = count2 + 0.2;

            //   System.out.println(xStep[i]);
            //   System.out.println(yStep[i]);
        }
        count2 = 1.0;
        count--;

        lineChartData.add(updateDiagram(xStep, yStep));

        for(int i=0; i<xStep2.length; i++){
            xStep2[i] = count2 + 0.2;
            yStep2[i] = Math.sin(count + 0.2);
            count = count + 0.2;
            count2 = count2 + 0.2;

            //   System.out.println(xStep[i]);
            //   System.out.println(yStep[i]);
        }
        count2 = 2.0;
        count--;

        lineChartData.add(updateDiagram(xStep2, yStep2));

        for(int i=0; i<xStep3.length; i++){
            xStep3[i] = count2 + 0.2;
            yStep3[i] = Math.sin(count + 0.2);
            count = count + 0.2;
            count2 = count2 + 0.2;

            //   System.out.println(xStep[i]);
            //   System.out.println(yStep[i]);
        }
        count2 = 0.0;
        count--;

        lineChartData.add(updateDiagram(xStep3, yStep3));
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
        timeline2 = new Timeline();
        //-- This timeline is indefinite.
        timeline2.setCycleCount(Animation.INDEFINITE);

        timeline2.getKeyFrames().add(
                new KeyFrame(Duration.millis(time), new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent actionEvent) {
                        render();

                    }
                })
        );

        //-- Set Animation- Timeline is created now.
        animation = new SequentialTransition();
        animation.getChildren().addAll(timeline2);
        animation.play();

    }

   //public


    @FXML
    private void onButtonShowDiagram(){
        button.setStyle("-fx-background-color: " +  parseColorToCssStyle(colorPicker.getValue().toString()));
        System.out.println(parseColorToCssStyle(colorPicker.getValue().toString()));

        series1.getData().add(new XYChart.Data<Number, Number>(2.9, 0.5));

       // uploadCheckerTimer.cancel();

        time = Integer.parseInt(textEditOne.getText());


    }



    private String parseColorToCssStyle(String color){
        String parseColor = "#" + color.substring(2, color.length());
        return parseColor;
    }

    public void setUploadCheckerTimer(){
        uploadCheckerTimer.scheduleAtFixedRate(
                new TimerTask() {
                    public void run() {

                    }
                }, 0, 100);
    }

    public XYChart.Series <Number, Number> updateDiagram(Double[] xStep, Double[] yStep){

        final XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
        series.setName("sin(x)");

        for(int i=0; i<xStep.length; i++){
            series.getData().addAll(
                    new XYChart.Data<Number,Number>(xStep[i], yStep[i])
            );
        }

        return series;
    }


}
