package sample.kotlin

import javafx.collections.FXCollections
import javafx.scene.chart.XYChart
/*
* example
* var userChartSin = UserChartFunctions("sin")
* (@FXML)lineChartViewOne.setData(userChartSin.lineChartData)
* userChartSin.setChartStep(xStep, yStep)
* userChartSin.show()
* */
class UserChartFunctions(val nameChart: String) {

    var lineChartData = FXCollections.observableArrayList<XYChart.Series<Number, Number>>()

    private var xStep = arrayOfNulls<Double>(200)
    private var yStep = arrayOfNulls<Double>(200)

    fun getTitleKotlin(): String = this.toString()

    fun setChartStep(data: ChartXYArrays) {
        if (data.xStep == null) return
        if (data.yStep == null) return
        xStep = arrayOfNulls<Double>(data.xStep.size)
        yStep = arrayOfNulls<Double>(data.yStep.size)
        xStep = data.xStep
        yStep = data.yStep
    }

    fun show() {
        if(!lineChartData.isEmpty()) lineChartData.removeAt(0)
        lineChartData.add(createSeriesAndSetDataDiagram())
    }
    fun showLineTwo() {

        if(lineChartData.size > 1) lineChartData.removeAt(1)
        lineChartData.add(createSeriesAndSetDataDiagram())

    }
    fun showLineThree() {
        if(lineChartData.size > 2) lineChartData.removeAt(2)
        lineChartData.add(createSeriesAndSetDataDiagram())
    }

    fun createSeriesAndSetDataDiagram(): XYChart.Series<Number, Number> {

        val series = XYChart.Series<Number, Number>()
        series.name = nameChart
        for (i in xStep.indices) {
            series.data.addAll(XYChart.Data<Number, Number>(xStep[i], yStep[i]))
        }
        return series
    }
}

