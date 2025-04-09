package com.example.trabalho_final.screens.registerUser

import androidx.lifecycle.ViewModel
import com.example.trabalho_final.dao.UserDao

class RegisterUserViewModelFactory(
    private val userDao: UserDao
) : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegisterUserViewModel(userDao) as T
    }
}