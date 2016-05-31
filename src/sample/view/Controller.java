package sample.view;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.util.Duration;
import sample.kotlinFun.UserChartFunctions;
import sample.kotlinFun.UserModernUI;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {



    UserChartFunctions userChartSin = new UserChartFunctions("sin");
    UserChartFunctions userChartFft = new UserChartFunctions("fft");
    UserChartFunctions userChartDist = new UserChartFunctions("dist");

    UserModernUI userModernUI = new UserModernUI();

    private ExecutorService executor;
    private AddToQueue addToQueue;
    private Timeline timeline;
    private SequentialTransition animation;

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

    int timeRendering = 100;

    Boolean aBoolean = true;

    @FXML
    private void initialize() {
        //-- Prepare Executor Services
        executor = Executors.newCachedThreadPool();
        addToQueue = new AddToQueue();
        executor.execute(addToQueue);

        lineChartViewOne.setAnimated(false);
        lineChartViewOne.setData(userChartSin.getLineChartData());

        lineChartViewOne.createSymbolsProperty();

        lineChartViewTwo.setAnimated(false);
        lineChartViewTwo.setData(userChartFft.getLineChartData());
        lineChartViewTwo.createSymbolsProperty();

        lineChartViewThree.setAnimated(false);
        lineChartViewThree.setData(userChartDist.getLineChartData());
        lineChartViewThree.createSymbolsProperty();

        button.setStyle("-fx-background-color: cornflowerblue; -fx-text-fill: white; -fx-font-size: 20px");
        buttonSpeedMore.setStyle("-fx-background-color: indianred; -fx-text-fill: white");
        buttonSpeedLess.setStyle("-fx-background-color: indianred; -fx-text-fill: white");

    }

    @FXML
    private void onButtonShowDiagram() {
        //-- Prepare Timeline
        if (aBoolean) {
            button.setText("Stop render");
            prepareTimeline();
            aBoolean = false;
        } else {
            button.setText("Start render");
            animation.stop();
            aBoolean = true;
        }

        button.setText(userChartSin.getTitleKotlin());
    }

    @FXML
    private void onButtonSpeedMore() {
        if (timeRendering > 20) timeRendering = timeRendering - 10;
        animation.stop();
        prepareTimeline();
    }

    @FXML
    private void onButtonSpeedLess() {
        timeRendering = timeRendering + 10;
        animation.stop();
        prepareTimeline();
    }

    public void render() {

        userChartSin.setChartStep(userModernUI.calcSin(110, 200, 200));
        userChartFft.setChartStep(userModernUI.calcSin(10, 200, 200));
        userChartDist.setChartStep(userModernUI.calcSin(55, 200, 200));


        userChartSin.show();
        userChartFft.show();
        userChartDist.show();
    }

    private class AddToQueue extends Thread {
        public void run() {
            try {
                Thread.currentThread().setName(Thread.currentThread().getId() + "-DataAdder");
                Thread.sleep(50);
                executor.execute(addToQueue);
            } catch (InterruptedException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //-- Timeline gets called in the JavaFX Main thread
    private void prepareTimeline() {
        //-- Second slower timeline
        timeline = new Timeline();
        //-- This timeline is indefinite.
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(timeRendering), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
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


}
