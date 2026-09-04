package com.example.futgoal.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.futgoal.models.Cancha

@Composable
fun CanchaListScreen(canchas: List<Cancha>, onVerDetalle: (Cancha) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(canchas) { cancha ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(cancha.nombre, style = MaterialTheme.typography.titleLarge)
                    Text(cancha.ubicacion)
                    Text("Tipo: ${cancha.tipo}")
                    Text("Valor por hora: $${cancha.precio}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { onVerDetalle(cancha) }) {
                        Text("Ver Detalle")
                    }
                }
            }
        }
    }
}
