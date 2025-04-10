package com.example.appfood

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.credentials.CredentialManager
import com.example.appfood.view.navigation.AppNavigation
import com.example.appfood.view.ui.theme.AppFoodTheme
import com.example.appfood.viewModel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appfood.model.data.repository.LoginRepository


class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(
            LoginRepository(
                FirebaseAuth.getInstance(),
                CredentialManager.create(this)
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppFoodTheme {
           AppNavigation(authViewModel)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        authViewModel.checkCurrentUser()
    }
}
@Composable
@Preview(showBackground = true)
fun GreetingPreview() {

}

class AuthViewModelFactory(private val repository: LoginRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}