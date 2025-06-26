package com.example.vault.ui.auth

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.vault.data.AppDatabase
import com.example.vault.data.AuthRepository
import com.example.vault.data.entities.User
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AuthRepository
    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

    private val _registrationStatus = MutableLiveData<Boolean>()
    val registrationStatus: LiveData<Boolean> = _registrationStatus
    private val _loginStatus = MutableLiveData<User?>()
    val loginStatus: LiveData<User?> = _loginStatus


    init {
        val userDao = AppDatabase.Companion.getDatabase(application).userDao()
        repository = AuthRepository(userDao)
    }

    fun register(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            _registrationStatus.postValue(false)
            return
        }
        viewModelScope.launch {
            try {
                val hashedPassword = repository.hashPassword(password)
                val user = User(
                    username = username,
                    passwordHash = hashedPassword,
                    monthlyGoalMin = null,
                    monthlyGoalMax = null
                )
                repository.registerUser(user)
                _registrationStatus.postValue(true)
            } catch (e: Exception) {
                _registrationStatus.postValue(false)
            }
        }
    }

    fun login(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            _loginStatus.postValue(null)
            return
        }
        viewModelScope.launch {
            val user = repository.loginUser(username, password)
            _loginStatus.postValue(user)
        }
    }
    fun loadUser(userId: Long) {
        viewModelScope.launch {
            val user = repository.getUser(userId)
            Log.d("AuthViewModel", "Loaded user for ID $userId: $user")
            _currentUser.postValue(user)
        }
    }

    fun updateUserGoals(userId: Long, minGoal: Double, maxGoal: Double) {
        viewModelScope.launch {
            val user = repository.getUser(userId)
            user?.let {
                val updatedUser = it.copy(monthlyGoalMin = minGoal, monthlyGoalMax = maxGoal)
                repository.updateUser(updatedUser)
            }
        }
    }

}