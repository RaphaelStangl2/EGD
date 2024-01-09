package com.example.egd.ui.bottomNav.statistics

import android.text.TextUtils
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.egd.ui.DonutPieChartWithSlices
import com.example.egd.ui.EGDViewModel
import com.example.egd.ui.ScheduleField


data class Drive(
    val date: String,
    val kmCount: Int
)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CircleDiagram(modifier: Modifier = Modifier, viewModel: EGDViewModel){

    val donutChartData = PieChartData(
        slices = listOf(
            PieChartData.Slice("HP", 15f, Color(0xFF5F0A87)),
            PieChartData.Slice("Dell", 30f, Color(0xFF20BF55)),
            PieChartData.Slice("Lenovo", 40f,  Color(0xFFEC9F05)),
            PieChartData.Slice("Asus", 10f, Color(0xFFF53844))
        ),
        plotType = PlotType.Donut

    )
    val donutChartConfig = PieChartConfig(
        percentVisible = true,
        percentageFontSize = 42.sp,
        strokeWidth = 120f,
        percentColor = Color.Black,
        activeSliceAlpha = .9f,
        isAnimationEnable = true,
        showSliceLabels = false,
        sliceLabelEllipsizeAt = TextUtils.TruncateAt.START,
        sliceLabelTextColor = Color.Black
        )

    var clickedSlice by remember { mutableStateOf<PieChartData.Slice?>(null) }


    val drivesList: List<Drive>
    clickedSlice?.let { slice ->

        //hier von berdan aufrufen und auf drivesList setzen
        drivesList = listOf(
                Drive(date = "2023-01-01", kmCount = 100),
        Drive(date = "2023-01-05", kmCount = 150),
        Drive(date = "2023-01-10", kmCount = 120),
        Drive(date = "2023-01-01", kmCount = 100),
        Drive(date = "2023-01-05", kmCount = 150),
        Drive(date = "2023-01-10", kmCount = 120),
        Drive(date = "2023-01-01", kmCount = 100),
        Drive(date = "2023-01-05", kmCount = 150),
        Drive(date = "2023-01-10", kmCount = 120),
        Drive(date = "2023-01-01", kmCount = 100),
        Drive(date = "2023-01-05", kmCount = 150),
        Drive(date = "2023-01-10", kmCount = 120),
        // ... Weitere Elemente hinzufügen
        )


        AlertDialog(
            modifier = Modifier
                .width(400.dp)
                .height(550.dp),
            onDismissRequest = {
                // Dismiss the dialog when clicked outside
                clickedSlice = null
            },
            title = {
                Text("")
            },
            text = {
                LazyColumn {
                    items(items = drivesList) { drive ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "Date: ${drive.date}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                            Text(
                                text = "KM Count: ${drive.kmCount}",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Divider(color = Color.Gray, thickness = 1.dp)
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    // Dismiss the dialog when the button is clicked
                    clickedSlice = null
                }) {
                    Text("OK")
                }
            }
        )
    }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Drives",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            DonutPieChart(
                modifier = modifier,
                donutChartData,
                donutChartConfig,
                onSliceClick = { slice ->
                    clickedSlice = slice
                }
            )
        DonutPieChartWithSlices(slices = donutChartData.slices, donutChartConfig = donutChartConfig)
    }

}

