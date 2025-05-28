// Create: ui/setup/SetupViewModel.kt
package com.example.qotd.ui.setup

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qotd.data.model.Game
import com.example.qotd.data.repository.QuoteRepository
import com.example.qotd.data.repository.Result
import kotlinx.coroutines.launch

class SetupViewModel : ViewModel() {
    private val repository = QuoteRepository()

    private val _selectedGames = mutableStateOf(setOf<String>())
    val selectedGames get() = _selectedGames.value

    private val _userName = mutableStateOf("")
    val userName get() = _userName.value

    private val _availableGames = mutableStateOf<List<Game>>(emptyList())
    val availableGames get() = _availableGames.value

    private val _isLoading = mutableStateOf(false)
    val isLoading get() = _isLoading.value

    fun updateSelectedGames(games: Set<String>) {
        _selectedGames.value = games
    }

    fun updateUserName(name: String) {
        _userName.value = name
    }

    init {
        loadAvailableGames()
    }

    fun loadAvailableGames() {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = repository.getSelectableGames()) {
                is Result.Success -> {
                    _availableGames.value = result.data
                    _isLoading.value = false
                }
                is Result.Failure -> {
                    android.util.Log.e("SetupViewModel", "Error loading games: ${result.exception.message}")
                    _isLoading.value = false
                }
            }
        }
    }

    fun isGameSelected(gameName: String): Boolean {
        return _selectedGames.value.contains(gameName)
    }

    fun toggleGameSelection(gameName: String) {
        val currentGames = _selectedGames.value
        _selectedGames.value = if (currentGames.contains(gameName)) {
            currentGames - gameName
        } else {
            currentGames + gameName
        }
    }
}