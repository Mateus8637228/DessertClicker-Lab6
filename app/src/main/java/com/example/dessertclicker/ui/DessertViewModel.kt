package com.example.dessertclicker.ui

import androidx.lifecycle.ViewModel
import com.example.dessertclicker.data.Datasource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DessertViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DessertUiState())
    val uiState: StateFlow<DessertUiState> = _uiState.asStateFlow()

    fun sellDessert() {
        _uiState.update { state ->
            val sold = state.dessertsSold + 1
            val nextDessert = Datasource.dessertList.last { sold >= it.startProductionAmount }
            state.copy(
                revenue = state.revenue + state.currentDessert.price,
                dessertsSold = sold,
                currentDessert = nextDessert
            )
        }
    }
}
