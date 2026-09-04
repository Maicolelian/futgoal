package com.example.futgoal.ui.navigation

sealed class Screen(val route: String) {
    data object CanchaList : Screen("list")
    data object CanchaDetail : Screen("detail")
}
