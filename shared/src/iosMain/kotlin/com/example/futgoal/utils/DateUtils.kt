package com.example.futgoal.utils

import kotlinx.datetime.LocalDate
import platform.Foundation.NSCalendar
import platform.Foundation.NSDate
import platform.Foundation.NSCalendarUnitYear
import platform.Foundation.NSCalendarUnitMonth
import platform.Foundation.NSCalendarUnitDay

actual fun getNowLocalDate(): LocalDate {
    val calendar = NSCalendar.currentCalendar
    val components = calendar.components(
        NSCalendarUnitYear or NSCalendarUnitMonth or NSCalendarUnitDay,
        NSDate()
    )
    return LocalDate(
        components.year.toInt(),
        components.month.toInt(),
        components.day.toInt()
    )
}
