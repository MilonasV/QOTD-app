// Create: ui/setup/SetupViewModel.kt
package com.example.qotd.ui.setup

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qotd.data.model.Game
import com.example.qotd.data.preferences.PreferencesManager
import com.example.qotd.data.repository.QuoteRepository
import com.example.qotd.data.repository.Result
import kotlinx.coroutines.launch

class SetupViewModel(private val application: Application) : AndroidViewModel(application) {

    private val preferencesManager = PreferencesManager(application.applicationContext)
    private val repository = QuoteRepository()

    private val _selectedGames = mutableStateOf(setOf<String>())
    val selectedGames get() = _selectedGames.value

    private val _userName = mutableStateOf("")
    val userName get() = _userName.value

    private val _availableGames = mutableStateOf<List<Game>>(emptyList())
    val availableGames get() = _availableGames.value

    private val _isLoading = mutableStateOf(false)
    val isLoading get() = _isLoading.value


    private val _wallpaperEnabled = mutableStateOf(false)
    val wallpaperEnabled get() = _wallpaperEnabled.value

    fun updateSelectedGames(games: Set<String>) {
        _selectedGames.value = games
        preferencesManager.saveSelectedGames(games)
    }


    init {
        loadAvailableGames()
        loadWallpaperSetting()
        loadSelectedGames()
        loadUserName()
    }

    fun updateUserName(name: String) {
        _userName.value = name
        preferencesManager.saveUserName(name)
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
        val currentSelection = _selectedGames.value.toMutableSet()
        if (currentSelection.contains(gameName)) {
            currentSelection.remove(gameName)
        } else {
            currentSelection.add(gameName)
        }
        updateSelectedGames(currentSelection)

    }

    fun toggleWallpaperEnabled() {
        val newValue = !_wallpaperEnabled.value
        _wallpaperEnabled.value = newValue
        preferencesManager.saveWallpaperEnabled(newValue)
    }

    fun loadWallpaperSetting(){
        _wallpaperEnabled.value = preferencesManager.isWallpaperEnabled()
    }

    fun loadSelectedGames() {
        _selectedGames.value = preferencesManager.getSelectedGames()
    }

    fun loadUserName(): String {
        return preferencesManager.getUserName() ?: ""
    }

    fun isSetupCompleted(): Boolean {
        return preferencesManager.isSetupCompleted()
    }
    fun markSetupCompleted() {
        preferencesManager.markSetupCompleted()
    }
}