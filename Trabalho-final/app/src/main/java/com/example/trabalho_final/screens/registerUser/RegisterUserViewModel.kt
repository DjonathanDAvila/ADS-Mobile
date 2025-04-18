package com.example.trabalho_final.screens.registerUser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trabalho_final.dao.UserDao
import com.example.trabalho_final.entity.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class RegisterUser(
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val errorMessage: String = "",
    val isSaved: Boolean = false
) {
    fun validatePassord(): String {
        if (password.isBlank()) {
            return "Password is required"
        }
        return ""
    }

    fun validateConfirmPassword(): String {
        if (confirmPassword != password) {
            return "The confirm password is different"
        }
        return ""
    }

    fun validateAllField() {
        if (fullName.isBlank()) {
            throw Exception("User is required")
        }
        if (email.isBlank()) {
            throw Exception("Email is required")
        }
        if (validatePassord().isNotBlank()) {
            throw Exception(validatePassord())
        }
        if (validateConfirmPassword().isNotBlank()) {
            throw Exception(validateConfirmPassword())
        }
    }

    fun toUser(): User {
        return User(
            fullName = fullName,
            email = email,
            password = password
        )
    }
}

class RegisterUserViewModel(
    private val userDao: UserDao
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUser())
    val uiState : StateFlow<RegisterUser> = _uiState.asStateFlow()

    fun onUserChange(user: String) {
        _uiState.value = _uiState.value.copy(fullName = user)
    }

    fun onEmailChange(email : String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun onConfirmPassword(confirm : String) {
        _uiState.value = _uiState.value.copy(confirmPassword = confirm)
    }

    fun register() {
        try {
            _uiState.value.validateAllField()
            viewModelScope.launch {
                userDao.insert(_uiState.value.toUser())
                _uiState.value = _uiState.value.copy(isSaved = true)
            }
        }
        catch (e: Exception) {
            _uiState.value = _uiState.value.copy(errorMessage = e.message ?: "Unknow error")
        }
    }

    fun cleanDisplayValues() {
        _uiState.value = _uiState.value.copy(
            isSaved = false,
            errorMessage = "")
    }
}