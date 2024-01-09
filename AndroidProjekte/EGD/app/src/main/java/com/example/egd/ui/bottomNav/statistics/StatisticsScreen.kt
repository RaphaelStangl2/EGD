




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
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.egd.ui.bottomNav.statistics.CircleDiagram
import com.example.egd.ui.bottomNav.statistics.LineChartDiagram
import com.google.accompanist.pager.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


@Composable
fun ScheduleField(viewModel: EGDViewModel, modifier: Modifier) {
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }


    Column(
        modifier = modifier
    ) {
        val date = viewModel.statsState.collectAsState().value.selectedDate
        val datePicker = DatePickerDialog(
            LocalContext.current,
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
                selectedDate = LocalDate.of(selectedYear, selectedMonth +1, selectedDayOfMonth)
                if (selectedDate != null){
                    viewModel.setDate(selectedDate!!)
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
                    Text("Date")
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

@Composable
private fun formatDate(date: Date): String {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return dateFormat.format(date)
}



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

    Column(modifier = modifier
        .fillMaxHeight()
        .verticalScroll(scrollState)) {
    val statsState = viewModel.statsState.collectAsState().value
    val car = statsState.car

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ScheduleField(viewModel, Modifier
                .fillMaxWidth(0.5f)
                .padding(1.dp))
            ScheduleField(viewModel,Modifier
                .fillMaxWidth(1f)
                .padding(1.dp))
        }


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
                            .padding(30.dp), viewModel = viewModel)


                1 -> LineChartDiagram(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp)
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

