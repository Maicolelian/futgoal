package com.example.futgoal.data

import com.example.futgoal.db.FutgoalDb
import com.example.futgoal.db.Reservation

class ReservationRepository(database: FutgoalDb) {
    private val queries = database.futgoalDbQueries

    fun saveReservation(userId: Long, canchaNombre: String, horario: String, fecha: String) {
        queries.insertReservation(userId, canchaNombre, horario, fecha)
    }

    fun deleteReservation(canchaNombre: String, horario: String, fecha: String) {
        queries.deleteReservation(canchaNombre, horario, fecha)
    }

    fun getAllReservations(): List<Reservation> {
        return queries.getAllReservations().executeAsList()
    }

    fun getAllReservationsWithNames() = queries.getAllReservationsWithNames().executeAsList()
}
