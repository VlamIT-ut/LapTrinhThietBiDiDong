package com.example.appfood

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.credentials.CredentialManager
import com.example.appfood.view.navigation.AppNavigation
import com.example.appfood.viewModel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appfood.model.data.local.UserPreferences
import com.example.appfood.model.data.repository.LoginRepository

class MainActivity : ComponentActivity() {

    // Initialize userPreferences after calling super.onCreate()
    private lateinit var userPreferences: UserPreferences

    // ViewModel initialization, now userPreferences is safely initialized
    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(
            LoginRepository(
                FirebaseAuth.getInstance(),
                CredentialManager.create(this)
            ), userPreferences
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize userPreferences here to avoid null context issues
        userPreferences = UserPreferences(applicationContext)

        // Enable edge-to-edge display
        enableEdgeToEdge()

        // Set content with AppNavigation and ViewModel
        setContent {
            AppNavigation(authViewModel)
        }
    }

    override fun onStart() {
        super.onStart()
        // Check current user status
        authViewModel.checkCurrentUser()
    }
}

// ViewModelFactory to instantiate AuthViewModel
class AuthViewModelFactory(
    private val repository: LoginRepository,
    private val userPreferences: UserPreferences
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(repository, userPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
