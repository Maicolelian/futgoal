package com.example.futgoal.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.futgoal.data.ReservationRepository
import com.example.futgoal.data.UserRepository
import com.example.futgoal.models.Cancha
import com.example.futgoal.db.GetAllReservationsWithNames
import com.example.futgoal.utils.getNowLocalDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

data class CanchaUiState(
    val showContent: Boolean = false,
    val selectedCancha: Cancha? = null,
    val allReservations: List<GetAllReservationsWithNames> = emptyList(),
    val canchas: List<Cancha> = getSampleCanchas(),
    val standardHorarios: List<String> = STANDARD_HORARIOS,
    val userName: String = "",
    val showReservationDialog: Boolean = false,
    val showCancelConfirmation: Boolean = false,
    val selectedHorario: String? = null,
    val selectedHorarioOwner: String = "",
    val selectedDate: LocalDate = getNowLocalDate()
)

private val STANDARD_HORARIOS = listOf(
    "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", 
    "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"
)

class CanchaViewModel(
    private val userRepository: UserRepository,
    private val reservationRepository: ReservationRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CanchaUiState())
    val uiState: StateFlow<CanchaUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val user = userRepository.getCurrentUser()
            val reservations = reservationRepository.getAllReservationsWithNames()
            
            _uiState.update { it.copy(
                userName = user?.name ?: "Invitado",
                allReservations = reservations
            ) }
        }
    }

    fun toggleContent() {
        _uiState.update { it.copy(showContent = !it.showContent) }
    }

    fun selectCancha(cancha: Cancha?) {
        _uiState.update { it.copy(selectedCancha = cancha) }
    }

    fun updateSelectedDate(date: LocalDate) {
        _uiState.update { it.copy(selectedDate = date) }
    }

    fun toggleHorario(horario: String) {
        val cancha = _uiState.value.selectedCancha ?: return
        val date = _uiState.value.selectedDate.toString()
        
        val reservation = _uiState.value.allReservations.find { 
            it.canchaNombre == cancha.nombre && it.horario == horario && it.fecha == date
        }
        
        if (reservation != null) {
            _uiState.update { it.copy(
                showCancelConfirmation = true, 
                selectedHorario = horario,
                selectedHorarioOwner = reservation.userName
            ) }
        } else {
            _uiState.update { it.copy(showReservationDialog = true, selectedHorario = horario) }
        }
    }

    fun confirmReservation(name: String, email: String) {
        val horario = _uiState.value.selectedHorario ?: return
        val cancha = _uiState.value.selectedCancha ?: return
        val date = _uiState.value.selectedDate.toString()
        
        viewModelScope.launch {
            var user = userRepository.findUserByEmail(email)
            if (user == null) {
                userRepository.registerUser(name, email)
                user = userRepository.findUserByEmail(email)
            }
            
            if (user != null) {
                reservationRepository.saveReservation(user.id, cancha.nombre, horario, date)
                val updatedReservations = reservationRepository.getAllReservationsWithNames()
                
                _uiState.update { it.copy(
                    allReservations = updatedReservations,
                    showReservationDialog = false,
                    selectedHorario = null,
                    userName = user.name
                ) }
            }
        }
    }

    fun confirmCancellation() {
        val horario = _uiState.value.selectedHorario ?: return
        val cancha = _uiState.value.selectedCancha ?: return
        val date = _uiState.value.selectedDate.toString()

        viewModelScope.launch {
            reservationRepository.deleteReservation(cancha.nombre, horario, date)
            val updatedReservations = reservationRepository.getAllReservationsWithNames()
            
            _uiState.update { it.copy(
                allReservations = updatedReservations,
                showCancelConfirmation = false,
                selectedHorario = null,
                selectedHorarioOwner = ""
            ) }
        }
    }

    fun dismissReservationDialog() {
        _uiState.update { it.copy(showReservationDialog = false, selectedHorario = null) }
    }

    fun dismissCancelConfirmation() {
        _uiState.update { it.copy(showCancelConfirmation = false, selectedHorario = null, selectedHorarioOwner = "") }
    }

    fun getCanchaByName(nombre: String): Cancha? {
        return _uiState.value.canchas.find { it.nombre == nombre }
    }
}

private fun getSampleCanchas() = listOf(
    Cancha(
        nombre = "Sintetica pizarro",
        ubicacion = "Barrio pizarro",
        tipo = "FUT-5",
        precio = 40000,
        atributos = listOf("Marcador Digital", "Cubierta", "Iluminación")
    ),
    Cancha(
        nombre = "Sintetica cabildo",
        ubicacion = "Barrio los prados",
        tipo = "FUT-5",
        precio = 30000,
        atributos = listOf("Cubierta", "Iluminación")
    ),
    Cancha(
        nombre = "Sintetica casa de la cultura",
        ubicacion = "Barrio los prados",
        tipo = "FUT-5",
        precio = 25000,
        atributos = listOf("Iluminación")
    )
)
