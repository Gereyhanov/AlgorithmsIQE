package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SceneBuilder;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import sample.inheritance.CurvedFittedAreaChart;

import java.io.IOException;

public class Main extends Application {

    @Override public void start(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("view/sample.fxml"));
        primaryStage.setScene(SceneBuilder.create()
                .root(root)
                .width(1500).height(800)
               //  .stylesheets(Main.class.getResource("style/CurveFittedChart.css").toExternalForm())
                .build()
        );

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public  String getTitle() {
        return "Title";
    }
/*
    public XYChart.Series <Number, Number> generateDiagramSin(){

        Stage stageDiagramSinView = new Stage();

        // create chart and set as center content
        CurvedFittedAreaChart chart = new CurvedFittedAreaChart(
                new NumberAxis(), new NumberAxis());
        chart.setLegendVisible(true);
        chart.setHorizontalGridLinesVisible(false);
        chart.setVerticalGridLinesVisible(false);
        chart.setAlternativeColumnFillVisible(false);
        chart.setAlternativeRowFillVisible(false);

        final XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
        series.setName("sin(x)");

        for(int i=0; i<30; i++){
            series.getData().addAll(
                    new XYChart.Data<Number,Number>(i, Math.cbrt(i))
            );
        }


        chart.getData().add(series);

        stageDiagramSinView.setScene(SceneBuilder.create()
                .root(chart)
                .width(1200).height(800)
                .build()
        );
        stageDiagramSinView.show();


        return series;
    }*/
}
