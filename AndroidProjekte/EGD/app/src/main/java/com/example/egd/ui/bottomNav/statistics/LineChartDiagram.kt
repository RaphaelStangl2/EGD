package com.example.egd.ui.bottomNav.statistics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.common.utils.DataUtils
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartData
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import co.yml.charts.ui.piechart.charts.DonutPieChart
import com.example.egd.ui.DonutPieChartWithSlices

@Composable
fun LineChartDiagram(modifier: Modifier = Modifier){
    val barChartListSize = 5
    val maxRange = 1000; // maxrange = der am meisten geld hat
    val barChartDataa = DataUtils.getBarChartData(barChartListSize, maxRange)

    val xAxisData = AxisData.Builder()
        .axisStepSize(30.dp)
        .steps(barChartDataa.size - 1)
        .bottomPadding(40.dp)
        .axisLabelAngle(20f)
        .labelData { index ->
            barChartDataa[index].label
        }
        .build()

    val yAxisData = AxisData.Builder()
        .steps(10)
        .labelAndAxisLinePadding(20.dp)
        .axisOffset(20.dp)
        .labelData { index -> (index * (maxRange / 10)).toString() }
        .build()

    val barChartData = BarChartData(
        chartData = barChartDataa,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        tapPadding = 20.dp,
        showYAxis = true

    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Costs",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        BarChart(modifier = Modifier.height(350.dp), barChartData = barChartData)

    }


}