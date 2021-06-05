package br.com.alura.aluraesporte.repository

import android.content.SharedPreferences
import androidx.core.content.edit

private const val LOGGED_KEY = "LOGGED_KEY"

class LoginRepository(private val preferences: SharedPreferences) {

    fun login() = editPreferences(true)

    fun logoff() = editPreferences(false)

    fun isLogged(): Boolean = preferences.getBoolean(LOGGED_KEY, false)

    private fun editPreferences(value: Boolean) {
        preferences.edit {
            putBoolean(LOGGED_KEY, value)
        }
    }
}
