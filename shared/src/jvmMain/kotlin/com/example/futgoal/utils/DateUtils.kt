package com.example.futgoal.utils

import kotlinx.datetime.LocalDate
import java.time.LocalDate as JLocalDate

actual fun getNowLocalDate(): LocalDate {
    val now = JLocalDate.now()
    return LocalDate(now.year, now.monthValue, now.dayOfMonth)
}
