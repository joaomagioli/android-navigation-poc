package br.com.alura.aluraesporte.repository

import android.content.SharedPreferences
import androidx.core.content.edit

private const val LOGGED_KEY = "CHAVE_LOGADO"

class LoginRepository(private val preferences: SharedPreferences) {

    fun login() {
        preferences.edit {
            putBoolean(LOGGED_KEY, true)
        }
    }

    fun isLogged(): Boolean = preferences.getBoolean(LOGGED_KEY, false)
}
