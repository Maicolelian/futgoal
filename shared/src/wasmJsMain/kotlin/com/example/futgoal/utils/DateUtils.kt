package com.example.futgoal.utils

import kotlinx.datetime.LocalDate

// Simple fallback for WasmJs if Date is not easily available
actual fun getNowLocalDate(): LocalDate {
    return LocalDate(2026, 9, 4)
}
