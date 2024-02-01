package com.example.egd.data

import com.example.egd.data.entities.Car
import com.example.egd.data.entities.Drive
import java.time.LocalDate

data class StatisticsScreenState (
    val selectedDate: LocalDate = LocalDate.now(),
    val car: Car? = null,
    val driveStatistics: Array<Drive>? = null,
    val popupDrives: Array<Drive>? = null,
    val costsStatistics: Array<Drive>? = null,
    val popupCosts: Array<Drive>? = null,
    val fromDate: LocalDate = LocalDate.now().minusYears(50),
    val toDate: LocalDate = LocalDate.now().plusYears(50)

)
