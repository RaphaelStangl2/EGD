package com.example.egd.ui.bottomNav.statistics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.utils.DataUtils
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartData
import com.example.egd.ui.EGDViewModel
import com.example.egd.ui.dialogues.AddCostsDialogue

@Composable
fun LineChartDiagram(modifier: Modifier = Modifier, viewModel: EGDViewModel){
    val barChartListSize = 5
    val maxRange = 1000; // maxrange = der am meisten geld hat
    val barChartDataa = DataUtils.getBarChartData(barChartListSize, maxRange)
    
    var showCostsScreen = viewModel.costsState.collectAsState().value.showCosts

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
            Button(onClick = {viewModel.setShowCosts(true)}){
                Text("Add Costs")
            }
        if (showCostsScreen){
            AddCostsDialogue(viewModel = viewModel)
        }
        Spacer(modifier = Modifier.height(16.dp))
        BarChart(modifier = Modifier.height(350.dp), barChartData = barChartData)

    }


}