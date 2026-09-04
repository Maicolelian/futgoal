package com.example.futgoal

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.futgoal.data.DatabaseDriverFactory
import com.example.futgoal.data.ReservationRepository
import com.example.futgoal.data.UserRepository
import com.example.futgoal.db.FutgoalDb
import com.example.futgoal.ui.navigation.Screen
import com.example.futgoal.ui.screens.CanchaDetailScreen
import com.example.futgoal.ui.screens.CanchaListScreen
import com.example.futgoal.ui.components.ReservationDialog
import com.example.futgoal.ui.components.CancelReservationDialog
import com.example.futgoal.ui.theme.FutgoalTheme
import com.example.futgoal.ui.viewmodels.CanchaViewModel

@Composable
fun App(driverFactory: DatabaseDriverFactory) {
    val database = remember { FutgoalDb(driverFactory.createDriver()) }
    val userRepository = remember { UserRepository(database) }
    val reservationRepository = remember { ReservationRepository(database) }
    
    val viewModel: CanchaViewModel = viewModel { 
        CanchaViewModel(userRepository, reservationRepository) 
    }
    
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navController = rememberNavController()

    FutgoalTheme {
        if (uiState.showReservationDialog) {
            ReservationDialog(
                onConfirm = { name, email -> viewModel.confirmReservation(name, email) },
                onDismiss = { viewModel.dismissReservationDialog() }
            )
        }

        if (uiState.showCancelConfirmation) {
            CancelReservationDialog(
                ownerName = uiState.selectedHorarioOwner,
                onConfirm = { viewModel.confirmCancellation() },
                onDismiss = { viewModel.dismissCancelConfirmation() }
            )
        }

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { viewModel.toggleContent() }) {
                Text(if (uiState.showContent) "Ocultar mensaje" else "Mostrar mensaje")
            }

            AnimatedVisibility(uiState.showContent) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        "¡Hola ${uiState.userName}! ⚽ Bienvenido a Futgoal",
                        style = MaterialTheme.typography.headlineMedium,
                    )
                    Text("Queremos hacer de esta app un sitio ideal para reservas de canchas sintéticas 🚀")

                    Spacer(modifier = Modifier.height(24.dp))

                    NavHost(
                        navController = navController,
                        startDestination = Screen.CanchaList.route,
                        modifier = Modifier.weight(1f)
                    ) {
                        composable(Screen.CanchaList.route) {
                            CanchaListScreen(
                                canchas = uiState.canchas,
                                onVerDetalle = { cancha ->
                                    viewModel.selectCancha(cancha)
                                    navController.navigate(Screen.CanchaDetail.route)
                                }
                            )
                        }
                        composable(Screen.CanchaDetail.route) {
                            uiState.selectedCancha?.let { selectedCancha ->
                                // Filtrar reservas para esta cancha específica y fecha seleccionada
                                val currentReservedHorarios = uiState.allReservations
                                    .filter { 
                                        it.canchaNombre == selectedCancha.nombre && 
                                        it.fecha == uiState.selectedDate.toString() 
                                    }
                                    .map { it.horario }
                                    .toSet()

                                CanchaDetailScreen(
                                    cancha = selectedCancha,
                                    horarios = uiState.standardHorarios,
                                    reservedHorarios = currentReservedHorarios,
                                    selectedDate = uiState.selectedDate,
                                    onDateSelected = { viewModel.updateSelectedDate(it) },
                                    onHorarioToggle = { hora -> viewModel.toggleHorario(hora) },
                                    onRegresar = { 
                                        navController.popBackStack()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
