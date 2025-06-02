package com.example.qotd.data.preferences

import android.content.Context

class PreferencesManager(private val context: Context) {
    companion object {
        private const val PREF_NAME = "qotd_preferences"
        private const val KEY_WALLPAPER_ENABLED = "wallpaper_enabled"
        private const val KEY_SELECTED_GAMES = "selected_games"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_SETUP_COMPLETED = "setup_completed"
    }

    private val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveWallpaperEnabled(enabled: Boolean) {
        sharedPreferences.edit()
            .putBoolean(KEY_WALLPAPER_ENABLED,enabled)
            .apply()
    }

    fun isWallpaperEnabled(): Boolean {
        // Return the value of wallpaper enabled preference, default is false
        return sharedPreferences.getBoolean(KEY_WALLPAPER_ENABLED, false)
    }

    fun saveSelectedGames(games: Set<String>) {
        sharedPreferences.edit()
            .putStringSet(KEY_SELECTED_GAMES, games)
            .apply()
    }

    fun getSelectedGames(): Set<String> {
        // Return the set of selected games, default is an empty set
        return sharedPreferences.getStringSet(KEY_SELECTED_GAMES, emptySet()) ?: emptySet()
    }

    fun saveUserName(name: String) {
        sharedPreferences.edit()
            .putString(KEY_USER_NAME, name)
            .apply()
    }

    fun getUserName(): String? {
        // Return the user name, default is null
        return sharedPreferences.getString(KEY_USER_NAME, null)
    }

    fun markSetupCompleted() {
        sharedPreferences.edit()
            .putBoolean(KEY_SETUP_COMPLETED, true)
            .apply()
    }

    fun isSetupCompleted(): Boolean {
        // Return whether the setup is completed, default is false
        return sharedPreferences.getBoolean(KEY_SETUP_COMPLETED, false)
    }
}