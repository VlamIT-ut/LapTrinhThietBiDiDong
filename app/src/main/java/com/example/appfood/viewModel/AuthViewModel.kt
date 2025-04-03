package com.example.appfood.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfood.model.data.repository.LoginRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: LoginRepository) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun signInWithEmail(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val user = repository.signInWithEmail(email, password)
            _authState.value = if (user != null) {
                AuthState.Success(user)
            } else {
                AuthState.Error("Authentication failed or email not verified.")
            }
        }
    }

    fun createAccount(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val user = repository.createAccount(email, password)
            _authState.value = if (user != null) {
                AuthState.Success(user)
            } else {
                AuthState.Error("Registration failed.")
            }
        }
    }

    fun signInWithGoogle(context: Context) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val user = repository.signInWithGoogle(context)
            _authState.value = if (user != null) {
                AuthState.Success(user)
            } else {
                AuthState.Error("Google Sign-In failed.")
            }
        }
    }

    fun signInWithApple(context: Context) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val user = repository.signInWithApple(context)
            _authState.value = if (user != null) {
                AuthState.Success(user)
            } else {
                AuthState.Error("Apple Sign-In failed.")
            }
        }
    }

    fun checkCurrentUser() {
        val user = repository.getCurrentUser()
        if (user != null) {
            _authState.value = AuthState.Success(user)
        } else {
            _authState.value = AuthState.Idle
        }
    }

    fun signOut() {
        repository.signOut()
        _authState.value = AuthState.Idle
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }
}

sealed class AuthState {
    data object Idle : AuthState()
    data object Loading : AuthState()
    data class Success(val user: FirebaseUser) : AuthState()
    data class Error(val message: String) : AuthState()
}