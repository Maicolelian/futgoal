package com.example.futgoal.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HorariosDisponibles(
    horarios: List<String>,
    reservedHorarios: Set<String>,
    onHorarioToggle: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Horarios disponibles:", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            horarios.forEach { hora ->
                val reservado = reservedHorarios.contains(hora)
                Button(
                    onClick = { onHorarioToggle(hora) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (reservado)
                            MaterialTheme.colorScheme.errorContainer
                        else
                            MaterialTheme.colorScheme.primaryContainer
                    ),
                    modifier = Modifier.width(120.dp)
                ) {
                    Text(if (reservado) "$hora - Reservado" else "$hora - Disponible")
                }
            }
        }
    }
}
