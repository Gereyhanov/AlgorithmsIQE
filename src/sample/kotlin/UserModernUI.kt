package sample.kotlin

class UserModernUI {

    private var stepX = 0.0
    private var stepSinY = 0.0

    fun calcSin(amp: Int, freq: Int, arrayLength: Int): ChartXYArrays {
        var arrayChartData = ChartXYArrays(arrayLength)
        for (item in arrayChartData.xStep.indices) {
            arrayChartData.xStep[item] = stepX
            arrayChartData.yStep[item] = amp * Math.sin(stepSinY)
            stepX = (stepX + 0.1)
            stepSinY = (stepSinY + 0.1)
        }
        stepSinY--
        stepX = 0.0

        return arrayChartData
    }
    /*
    fun parseColorToCssStyle(color: String): String {
        val parseColor = "#" + color.substring(2, color.length)
        return parseColor
    }*/
}


