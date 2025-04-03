package com.example.appfood.view.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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

    Scaffold(modifier = Modifier.fillMaxSize().padding(16.dp)) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(24.dp))
            Text(
                "Login to Food Delivery",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.padding(24.dp))

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { viewModel.signInWithGoogle(navController.context) },
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
                Button(
                    onClick = { viewModel.signInWithApple(navController.context) },
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
                            painterResource(id = R.drawable.apple_logo),
                            contentDescription = null,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            "Continue with Apple",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black,
                            modifier = Modifier.weight(4f)
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    "or",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.padding(8.dp))
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
                        modifier = Modifier.fillMaxWidth().size(335.dp, 60.dp),
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) }
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        placeholder = { Text("Enter Password") },
                        modifier = Modifier.fillMaxWidth().size(335.dp, 60.dp),
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
                    Text(
                        "Forgot Password?",
                        textAlign = TextAlign.Right,
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Blue,
                        fontSize = 14.sp
                    )
                    Button(
                        onClick = { viewModel.signInWithEmail(email, password) },
                        modifier = Modifier.size(367.dp, 80.dp).padding(8.dp),
                        border = BorderStroke(2.dp, Color.Black),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(Color.Blue),
                        enabled = authState !is AuthState.Loading
                    ) {
                        Text("Continue")
                    }
                }
                Text("Don’t have a Food Delivery account?", textAlign = TextAlign.Right)
                Button(
                    onClick = { navController.navigate("sign_up") },
                    modifier = Modifier.size(367.dp, 80.dp).padding(8.dp).align(Alignment.CenterHorizontally),
                    border = BorderStroke(2.dp, Color.Black),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(Color.White)
                ) {
                    Text(
                        "Sign up",
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                when (authState) {
                    is AuthState.Loading -> CircularProgressIndicator()
                    is AuthState.Success -> {
                        LaunchedEffect(Unit) {
                            navController.navigate("profile") {
                                popUpTo("welcome") { inclusive = true }
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