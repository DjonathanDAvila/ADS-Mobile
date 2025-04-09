package com.example.trabalho_final.screens.login

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trabalho_final.dao.UserDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLogged: Boolean = false
)

class LoginViewModel(private val userDao: UserDao) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun login(context: Context, onSuccess: () -> Unit) {
        val email = _uiState.value.email
        val password = _uiState.value.password

        viewModelScope.launch {
            val user = userDao.getUserByEmailAndPassword(email, password)
            if (user != null) {
                Toast.makeText(context, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show()
                _uiState.value = _uiState.value.copy(isLogged = true)
                onSuccess()
            } else {
                Toast.makeText(context, "E-mail ou senha incorretos!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}