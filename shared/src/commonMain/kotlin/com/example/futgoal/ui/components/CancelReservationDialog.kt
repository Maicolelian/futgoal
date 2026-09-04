package com.example.futgoal.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

@Composable
fun CancelReservationDialog(
    ownerName: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Cancelar Reserva") },
        text = { 
            Text(
                text = buildAnnotatedString {
                    append("Este horario fue reservado por: ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(ownerName)
                    }
                    append(".\n\n¿Estás seguro de que deseas cancelar esta reserva?")
                }
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Sí, cancelar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("No, mantener")
            }
        }
    )
}
