package com.example.appfood.view.ui.screens.login_signup

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.appfood.R
import com.example.appfood.viewModel.AuthState
import com.example.appfood.viewModel.AuthViewModel

@Composable
fun LoginScreen(navController: NavController, viewModel: AuthViewModel) {
    val authState by viewModel.authState.collectAsStateWithLifecycle()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isShowPassword by remember { mutableStateOf(false) }
    val view = LocalView.current
    val activityContext = remember(view) {
        view.context as? Activity
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
                .background(colorResource(R.color.darkBrown)),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(24.dp))
            Text(
                "Login to Food Delivery",
                fontSize = 24.sp,
                color = colorResource(R.color.orange),
                fontWeight = FontWeight.ExtraBold,

                )

            Spacer(modifier = Modifier.padding(24.dp))
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    placeholder = { Text("Enter email") },
                    modifier = Modifier.size(350.dp, 60.dp),
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) }
                )
                Spacer(modifier = Modifier.padding(8.dp))
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    placeholder = { Text("Enter Password") },
                    modifier = Modifier.size(350.dp, 60.dp),
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                    trailingIcon = {
                        IconButton(onClick = { isShowPassword = !isShowPassword }) {
                            Icon(
                                if (isShowPassword) Icons.Filled.AccountCircle else Icons.Filled.Person,
                                contentDescription = null
                            )
                        }
                    },
                    visualTransformation = if (isShowPassword) VisualTransformation.None else PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    "Forgot Password?",
                    textAlign = TextAlign.Right,
                    modifier = Modifier.fillMaxWidth(),
                    color = colorResource(R.color.orange),
                    fontSize = 14.sp
                )
                Button(
                    onClick = { viewModel.signInWithEmail(email, password) },
                    modifier = Modifier.size(367.dp, 80.dp).padding(8.dp),
                    border = BorderStroke(2.dp, Color.Black),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.orange)),
                    enabled = authState !is AuthState.Loading
                ) {
                    Text("Continue")
                }
            }
            Text(
                "or",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        if (activityContext != null) {
                            viewModel.signInWithGoogle(activityContext)
                        } else {
                            Log.e("LoginScreen", "Activity context is null (from LocalView). Google Sign-In will fail.")
                            // Optionally show an error message to the user
                        }
                    },
                    colors = ButtonDefaults.buttonColors(Color.White),
                    modifier = Modifier.size(367.dp, 80.dp).padding(8.dp),
                    border = BorderStroke(2.dp, Color.Black),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painterResource(id = R.drawable.logo_google),
                            contentDescription = null,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            "Continue with Google",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black,
                            modifier = Modifier.weight(4f)
                        )
                    }
                }


                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    "Don’t have a Food Delivery account?",
                    textAlign = TextAlign.Right, color = Color.White,
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Button(
                    onClick = { navController.navigate("sign_up") },
                    modifier = Modifier.size(367.dp, 80.dp).padding(8.dp).align(Alignment.CenterHorizontally),
                    border = BorderStroke(2.dp, Color.Black),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.orange))
                ) {
                    Text(
                        "Sign up",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                when (authState) {
                    is AuthState.Loading -> CircularProgressIndicator()
                    is AuthState.Success -> {
                        LaunchedEffect(Unit) {
                            navController.navigate("home") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    }
                    is AuthState.Error -> Text(
                        text = (authState as AuthState.Error).message,
                        color = Color.Red
                    )
                    else -> {}
                }
            }
        }
    }
}