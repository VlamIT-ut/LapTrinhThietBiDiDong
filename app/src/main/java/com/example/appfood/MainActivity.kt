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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.appfood.model.data.factory.AuthViewModelFactory
import com.example.appfood.model.data.factory.MainViewModelFactory
import com.example.appfood.model.data.local.UserPreferences
import com.example.appfood.model.data.repository.LoginRepository
import com.example.appfood.view.ui.language.AppLanguageProvider
import com.example.appfood.viewModel.MainViewModel
import kotlinx.coroutines.launch

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
    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(
            this,
            MainViewModelFactory(userPreferences)
        )[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize userPreferences here to avoid null context issues
        userPreferences = UserPreferences(applicationContext)

        // Enable edge-to-edge display
        enableEdgeToEdge()

        // Cần observe ngôn ngữ trước khi setContent
        lifecycleScope.launch {
            userPreferences.appLanguage.collect { lang ->
                setContent {
                    AppLanguageProvider(language = lang) {
                        AppNavigation(authViewModel,mainViewModel)
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // Check current user status
        authViewModel.checkCurrentUser()
    }
}



