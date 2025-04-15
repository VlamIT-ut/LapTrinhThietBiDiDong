package com.example.appfood.view.ui.screens.payment

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import com.example.appfood.R // thay bằng tên package thật
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack


@Composable
fun MockVnpayPayment(navController: NavController) {
    var phoneNumber by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(
                    containerColor = Color.White,
                    contentColor = Color.Unspecified
                ) {
                    Text(
                        text = data.visuals.message,
                        color = Color(0xFFFF9800), // Màu cam
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // 🔙 Nút back ở góc trên bên trái
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF2979FF), // Màu xanh nổi bật
                    modifier = Modifier.size(28.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(48.dp)) // Dưới nút back

                // Banner
                Image(
                    painter = painterResource(id = R.drawable.banner_vnpay),
                    contentDescription = "VNPAY Banner",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Đăng nhập/Đăng ký",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Bạn hãy nhập số điện thoại để tiếp tục nhé",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 4.dp)
                )

                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = {
                        if (it.length <= 10 && it.all { c -> c.isDigit() }) {
                            phoneNumber = it
                        }
                    },
                    label = { Text("Số điện thoại") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )

                Button(
                    onClick = {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Thanh toán thành công!")
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp)
                        .height(50.dp),
                    shape = MaterialTheme.shapes.medium,
                    enabled = phoneNumber.length == 10
                ) {
                    Text(text = "Tiếp tục", fontSize = 16.sp)
                }
            }
        }
    }
}


