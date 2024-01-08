package com.example.egd.data

import com.example.egd.data.entities.Car
import java.time.LocalDate

data class StatisticsScreenState (
    val selectedDate: LocalDate = LocalDate.now(),
    val car: Car? = null

)
