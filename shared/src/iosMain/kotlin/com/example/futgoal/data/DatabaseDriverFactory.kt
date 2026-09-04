package com.example.futgoal.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.example.futgoal.db.FutgoalDb

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(FutgoalDb.Schema, "futgoal.db")
    }
}
