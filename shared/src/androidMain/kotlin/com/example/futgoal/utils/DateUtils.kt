package com.example.futgoal.utils

import kotlinx.datetime.LocalDate
import java.util.Calendar

actual fun getNowLocalDate(): LocalDate {
    val calendar = Calendar.getInstance()
    return LocalDate(
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH) + 1,
        calendar.get(Calendar.DAY_OF_MONTH)
    )
}
