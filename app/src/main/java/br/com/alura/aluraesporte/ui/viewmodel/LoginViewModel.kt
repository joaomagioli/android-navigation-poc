package br.com.alura.aluraesporte.ui.viewmodel

import androidx.lifecycle.ViewModel
import br.com.alura.aluraesporte.repository.LoginRepository

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    fun login() = loginRepository.login()

    fun isLogged(): Boolean = loginRepository.isLogged()
}
