package sample.view;

import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import sample.Main;
import sample.inheritance.CurvedFittedAreaChart;

public class Controller extends Main{

    @FXML
    Button buttonShowDiagram;

    @FXML
    AreaChart areaChartView;


    @FXML
    private void initialize() {

        areaChartView = new AreaChart(new NumberAxis(), new NumberAxis());
        areaChartView.setTitle("Diagrams");

        areaChartView.getXAxis().setAutoRanging(true);

        areaChartView.setLegendVisible(true);
        areaChartView.setHorizontalGridLinesVisible(false);
        areaChartView.setVerticalGridLinesVisible(false);
        areaChartView.setAlternativeColumnFillVisible(false);
        areaChartView.setAlternativeRowFillVisible(false);

        final XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
        series.setName("sin(x)");

        for(int i=0; i<30; i++){
            series.getData().addAll(
                    new XYChart.Data<Number,Number>(i, Math.sin(i))
            );
        }

        areaChartView.getData().add(series);


    }



    @FXML
    private void onButtonShowDiagram(){
        buttonShowDiagram.setText(getTitle());

        areaChartView.getData().addAll(generateDiagramSin());

    }
}
