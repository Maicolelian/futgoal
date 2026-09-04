package com.example.futgoal.data

import com.example.futgoal.db.FutgoalDb

class UserRepository(database: FutgoalDb) {
    private val queries = database.futgoalDbQueries

    fun registerUser(name: String, email: String) {
        queries.insertUser(name, email)
    }

    fun findUserByEmail(email: String) = queries.findUserByEmail(email).executeAsOneOrNull()

    fun getCurrentUser() = queries.getCurrentUser().executeAsOneOrNull()
}
