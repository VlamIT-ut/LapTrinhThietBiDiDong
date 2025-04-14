package com.example.appfood.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfood.model.data.local.UserPreferences
import com.example.appfood.model.data.repository.LoginRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: LoginRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState
    val isLoggedIn: Flow<Boolean> = userPreferences.isLoggedIn

    // Function to set login state in shared preferences
    private fun setLoginState(loggedIn: Boolean) {
        viewModelScope.launch {
            userPreferences.saveLoginState(loggedIn)
        }
    }

    // Sign in with email and password
    fun signInWithEmail(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val user = repository.signInWithEmail(email, password)
            _authState.value = if (user != null) {
                setLoginState(true)  // Save login state
                AuthState.Success(user)
            } else {
                AuthState.Error("Authentication failed or email not verified.")
            }
        }
    }

    // Create an account with email and password
    fun createAccount(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val user = repository.createAccount(email, password)
            _authState.value = if (user != null) {
                setLoginState(true)  // Save login state
                AuthState.Success(user)
            } else {
                AuthState.Error("Registration failed.")
            }
        }
    }

    // Sign in with Google
    fun signInWithGoogle(context: Context) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val user = repository.signInWithGoogle(context)
            _authState.value = if (user != null) {
                setLoginState(true)  // Save login state
                AuthState.Success(user)
            } else {
                AuthState.Error("Google Sign-In failed.")
            }
        }
    }

    // Check if a user is already logged in
    fun checkCurrentUser() {
        viewModelScope.launch {
            val user = repository.getCurrentUser()
            if (user != null) {
                _authState.value = AuthState.Success(user)
                setLoginState(true)  // Update login state
            } else {
                _authState.value = AuthState.Idle
                setLoginState(false) // Update login state
            }
        }
    }

    // Sign out the user
    fun signOut() {
        repository.signOut()
        _authState.value = AuthState.Idle
        setLoginState(false) // Update login state
    }

    val isFirstLaunch: Flow<Boolean> = userPreferences.isFirstLaunch

    fun completeFirstLaunch() {
        viewModelScope.launch {
            userPreferences.setFirstLaunchCompleted()
        }
    }
}

// Sealed class for authentication state
sealed class AuthState {
    data object Idle : AuthState()
    data object Loading : AuthState()
    data class Success(val user: FirebaseUser) : AuthState()
    data class Error(val message: String) : AuthState()
}
