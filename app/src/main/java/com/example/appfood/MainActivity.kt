package com.example.appfood

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.appfood.view.navigation.AppNavigation
import com.example.appfood.view.ui.theme.AppFoodTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppFoodTheme {
                AppNavigation()
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppFoodTheme {
        AppNavigation()
    }
}