package com.example.egd.ui.bottomNav.statistics

import android.service.autofill.DateTransformation
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
import androidx.compose.runtime.collectAsState
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
import com.example.egd.data.entities.Costs
import com.example.egd.data.entities.Drive
import com.example.egd.ui.DonutPieChartWithSlices
import com.example.egd.ui.EGDViewModel
import com.example.egd.ui.ScheduleField
import com.example.egd.ui.dialogues.AddCostsDialogue
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CircleDiagram(modifier: Modifier = Modifier, viewModel: EGDViewModel, donutChartData: PieChartData, header:String){

    val showCostsScreen = viewModel.costsState.collectAsState().value.showCosts

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
    var statsState = viewModel.statsState.collectAsState().value

    var drivesList: List<Drive>
    var costsList: List<Costs>
    clickedSlice?.let { slice ->
        drivesList = emptyList()

        costsList = emptyList()
        if (header=="Costs"){
            //hier von berdan aufrufen und auf drivesList setzen

            costsList = listOf(
                Costs(id = 0, description = "Tank",costs=200.00,userCar = null)
            )
        }else if (header == "Drives"){
            //hier von berdan aufrufen und auf drivesList setzen
//            drivesList = listOf(
//                Drive(date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()),
//                    kilometers = 100.0, id = 0, userCar = null));
            if (statsState.popupDrives != null){
                drivesList = (statsState.popupDrives)!!.toList()
            }
        }

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
                LazyColumn {if (header == "Drives"){

                    // Define the desired date format
                    val desiredDateFormat = SimpleDateFormat("dd.MM.yyyy")


                    items(items = drivesList) { drive ->
                        // Format the date using the desired format
                        val formattedDate = desiredDateFormat.format(drive.date)
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(

                                text = "Date: $formattedDate",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                            Text(
                                text = "KM Count: ${drive.kilometers}",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Divider(color = Color.Gray, thickness = 1.dp)
                        }
                    }

                }else if( header == "Costs"){

                    items(items = costsList) { cost ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "Description: ${cost.description}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                            Text(
                                text = "Costs: ${cost.costs}",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Divider(color = Color.Gray, thickness = 1.dp)
                        }
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
                text = header,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            if (header == "Costs"){
                Button(onClick = {viewModel.setShowCosts(true)}){
                    Text("Add Costs")
                }
                if (showCostsScreen){
                    AddCostsDialogue(viewModel = viewModel)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))

            if (donutChartData.slices.isNotEmpty() && donutChartConfig != null ) {
                DonutPieChart(
                    modifier = modifier,
                    donutChartData,
                    donutChartConfig,
                    onSliceClick = { slice ->
                        viewModel.getDrivesByUserCar()
                        clickedSlice = slice
                    }
                )
            }
        DonutPieChartWithSlices(slices = donutChartData.slices, donutChartConfig = donutChartConfig)
    }

}


