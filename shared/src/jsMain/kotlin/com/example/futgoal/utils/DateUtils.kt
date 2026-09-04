package com.example.futgoal.utils

import kotlinx.datetime.LocalDate
import kotlin.js.Date

actual fun getNowLocalDate(): LocalDate {
    val date = Date()
    return LocalDate(
        date.getFullYear(),
        date.getMonth() + 1,
        date.getDate()
    )
}
