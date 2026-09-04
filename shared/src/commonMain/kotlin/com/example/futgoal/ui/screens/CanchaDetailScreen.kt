package com.example.futgoal.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.futgoal.models.Cancha
import com.example.futgoal.ui.components.HorariosDisponibles
import kotlinx.datetime.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CanchaDetailScreen(
    cancha: Cancha,
    horarios: List<String>,
    reservedHorarios: Set<String>,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    onHorarioToggle: (String) -> Unit,
    onRegresar: () -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    
    val datePickerState = rememberDatePickerState()

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val date = Instant.fromEpochMilliseconds(millis)
                            .toLocalDateTime(TimeZone.UTC).date
                        onDateSelected(date)
                    }
                    showDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = onRegresar, modifier = Modifier.align(Alignment.Start)) {
            Text("< Regresar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(cancha.nombre, style = MaterialTheme.typography.headlineMedium)
        Text(cancha.ubicacion)
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Card(
            onClick = { showDatePicker = true },
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Fecha de reserva:", style = MaterialTheme.typography.labelLarge)
                Text(
                    text = "${selectedDate.day}/${selectedDate.monthNumber}/${selectedDate.year}",
                    style = MaterialTheme.typography.titleLarge
                )
                Text("(Toca para cambiar)", style = MaterialTheme.typography.bodySmall)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Atributos de la cancha:", style = MaterialTheme.typography.titleMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            cancha.atributos.forEach { atributo ->
                AssistChip(onClick = {}, label = { Text(atributo) })
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        HorariosDisponibles(
            horarios = horarios,
            reservedHorarios = reservedHorarios,
            onHorarioToggle = onHorarioToggle
        )
    }
}
