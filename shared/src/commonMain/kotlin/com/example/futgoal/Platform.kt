package com.example.futgoal

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform