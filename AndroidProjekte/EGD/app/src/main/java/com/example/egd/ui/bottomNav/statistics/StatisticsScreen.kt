




package com.example.egd.ui


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.egd.data.entities.Costs
import com.example.egd.data.entities.Drive
import com.example.egd.ui.bottomNav.statistics.CircleDiagram
import com.google.accompanist.pager.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


@Composable
fun ScheduleField(viewModel: EGDViewModel, date: LocalDate, identifier: String, text:String, modifier: Modifier) {
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }


    Column(
        modifier = modifier
    ) {
        val date = date
        val datePicker = DatePickerDialog(
            LocalContext.current,
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
                selectedDate = LocalDate.of(selectedYear, selectedMonth +1, selectedDayOfMonth)
                if (selectedDate != null){
                    viewModel.setDate(selectedDate!!, identifier)
                }
                //appointmentEvent(AppointmentEvents.SetDateEvent(selectedDate))
            }, date.year, date.monthValue -1, date.dayOfMonth
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
        ) {
            OutlinedTextField(
                value = date.toString(),
                onValueChange = { },
                label = {
                    Text(text)
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.DateRange,
                        contentDescription = "Date"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .clickable {
                        datePicker.show()
                    },
                readOnly = true,
                enabled = false
            )


        }
    }
}
//
//@Composable
//private fun formatDate(date: Date): String {
//    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
//    return dateFormat.format(date)
//}



@SuppressLint("ResourceAsColor")
@OptIn(ExperimentalTextApi::class)
@Composable
fun DonutPieChartWithSlices(
    slices: List<PieChartData.Slice>,
    modifier: Modifier = Modifier,
    donutChartConfig: PieChartConfig
) {
    val textMeasurer = rememberTextMeasurer()


    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(color = androidx.compose.material.MaterialTheme.colors.background)
    ) {
        val center = Offset(size.width /2F, 1F)
        val radius = size.width / 4
        val fixedRectSize = Size(25.dp.toPx(), 25.dp.toPx()) // Replace with your desired size
        var rectAllSize = size.width/3

        var i = 0;
        var h = 0;
        slices.forEach { slice ->

            drawRect(
                color = slice.color,
                size = fixedRectSize,
                topLeft = Offset( 100+i*rectAllSize-fixedRectSize.width, h+fixedRectSize.height)
            )
            drawText(
                textMeasurer = textMeasurer,
                text = slice.label,
                topLeft = Offset(50+i*rectAllSize+fixedRectSize.width,   h+fixedRectSize.height),
                style = TextStyle.Default,
                overflow = TextOverflow.Visible,
                softWrap = false,
                maxLines = Int.MAX_VALUE,
                maxSize = IntSize.Zero
            )


            if (i == 2){
                i=-1
                h +=150
            }
            i++;


        }
    }
}

@SuppressLint("SuspiciousIndentation")
@ExperimentalPagerApi
@Composable
fun StatisticsScreen(
    viewModel: EGDViewModel,
    modifier: Modifier = Modifier
) {


    val pagerState = rememberPagerState(initialPage = 2)
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    var drivesList: List<Drive>? = null
    var costsList: List<Costs>? = null
    val statsState = viewModel.statsState.collectAsState().value
    if (statsState.driveStatistics != null) {
        drivesList = statsState.driveStatistics.toList()
    }
    if (statsState.costsStatistics != null) {
        costsList = statsState.costsStatistics.toList()
    }

    val fromDate = statsState.fromDate
    val toDate = statsState.toDate

    Column(
        modifier = modifier
            .fillMaxHeight()
            .verticalScroll(scrollState)
    ) {

        val car = statsState.car

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ScheduleField(
                viewModel, fromDate, "fromDate", "From", Modifier
                    .fillMaxWidth(0.5f)
                    .padding(1.dp)
            )
            ScheduleField(
                viewModel, toDate, "toDate", "To", Modifier
                    .fillMaxWidth(1f)
                    .padding(1.dp)
            )



            HorizontalPager(
                count = 2,
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()


            ) { page ->
                when (page) {
                    0 ->
                        CircleDiagram(
                            modifier = Modifier
                                .background(color = Color.White)
                                // .fillMaxWidth()
                                .fillMaxWidth()
                                .padding(30.dp),
                            viewModel = viewModel,
                            donutChartData = userToPieChartData(drivesList),
                            header = "Drives",
                        )
                    1 -> CircleDiagram(
                        modifier = Modifier
                            .background(color = Color.White)
                            // .fillMaxWidth()
                            .fillMaxWidth()
                            .padding(30.dp),
                        viewModel = viewModel,
                        donutChartData = costsToPieChartData(costsList),
                        header = "Costs"
                    )
                }
            }
            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )


        }
    }

    fun userToPieChartData(drives: List<Drive>?): PieChartData {
        // Create a map to store the sum of kilometers for each user
        val userKilometersMap = mutableMapOf<String, Pair<Double, Long?>>()

        if (drives != null) {
            for (drive in drives) {
                val userName = drive.userCar?.user?.userName
                val userCarId = drive.userCar?.id
                val kilometers = drive.kilometers ?: 0.0

                if (userName != null) {
                    val existingKilometers = userKilometersMap[userName]
                    if (existingKilometers != null) {
                        val (totalKilometers, _) = existingKilometers
                        userKilometersMap[userName] = Pair(totalKilometers + kilometers, userCarId)
                    } else {
                        userKilometersMap[userName] = Pair(kilometers, userCarId)
                    }
                }
            }
        }

        // Convert the map entries to PieChartData slices
        val slices = userKilometersMap.entries.mapIndexed { index, (userName, kilometersWithId) ->
            val (totalKilometers, userCarId) = kilometersWithId
            val color = generateColor(index)
            val description = userCarId?.toString() ?: "0"
            PieChartData.Slice(userName, totalKilometers.toFloat(), color) { _ -> description }
        }

        // Create and return PieChartData
        return PieChartData(slices, PlotType.Donut)
    }


    // Helper function to generate distinct colors for each slice
    fun generateColor(index: Int): Color {
        // You can customize the color generation logic based on your preference
        val colors = listOf(
            Color(0xFF5F0A87),
            Color(0xFF20BF55),
            Color(0xFFEC9F05),
            Color(0xFFF53844)
        )

        return colors.get(index % colors.size);
    }


    fun costsToPieChartData(costsList: List<Costs>?): PieChartData {
        // Create a map to store the sum of costs for each user
        val userCostsMap = mutableMapOf<String, Pair<Double, Long?>>()

        if (costsList != null) {
            for (cost in costsList) {
                val userName = cost.userCar?.user?.userName
                val userCarId = cost.userCar?.id
                val costValue = cost.costs

                if (userName != null) {
                    val existingCosts = userCostsMap[userName]
                    if (existingCosts != null) {
                        val (totalCosts, _) = existingCosts
                        userCostsMap[userName] = Pair(totalCosts + costValue, userCarId)
                    } else {
                        userCostsMap[userName] = Pair(costValue, userCarId)
                    }
                }
            }
        }

        // Convert the map entries to PieChartData slices
        val slices = userCostsMap.entries.mapIndexed { index, (userName, costsWithId) ->
            val (costs, userCarId) = costsWithId
            val color = generateColor(index)
            val description = userCarId?.toString() ?: "0"
            PieChartData.Slice(userName, costs.toFloat(), color) { _ -> description }
        }

        // Create and return PieChartData
        return PieChartData(slices, PlotType.Donut)
    }
}
