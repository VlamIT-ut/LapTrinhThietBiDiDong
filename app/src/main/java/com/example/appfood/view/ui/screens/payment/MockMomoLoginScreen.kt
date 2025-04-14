package com.example.appfood.view.ui.screens.payment

import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appfood.R
import com.google.accompanist.pager.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MockMomoLoginScreen(navController: NavController) {
    val context = LocalContext.current
    var phoneNumber by remember { mutableStateOf("") }
    var selectedLang by remember { mutableStateOf("Việt") }

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val bannerImages = listOf(R.drawable.momo1, R.drawable.momo2, R.drawable.momo3)

    // Tự động chuyển banner
    LaunchedEffect(Unit) {
        while (true) {
            delay(2000L)
            val nextPage = (pagerState.currentPage + 1) % bannerImages.size
            coroutineScope.launch {
                pagerState.animateScrollToPage(
                    nextPage,
                    animationSpec = tween(durationMillis = 1000)
                )
            }
        }
    }

    val isPhoneValid = phoneNumber.length == 10 && phoneNumber.all { it.isDigit() }

    val texts = when (selectedLang) {
        "Việt" -> mapOf(
            "hint" to "Thông tin của bạn được bảo mật tuyệt đối",
            "placeholder" to "Số điện thoại",
            "continue" to "Tiếp tục",
            "success" to "Thanh toán thành công!"
        )
        else -> mapOf(
            "hint" to "Your information is absolutely secure",
            "placeholder" to "Phone number",
            "continue" to "Continue",
            "success" to "Payment successful!"
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .statusBarsPadding()
    ) {
        // Banner slider
        HorizontalPager(
            count = bannerImages.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) { page ->
            Image(
                painter = painterResource(id = bannerImages[page]),
                contentDescription = "Banner",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = texts["hint"] ?: "",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Phone input
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(1.dp, Color(0xFFDD1582), RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp)
        ) {
            Text(text = "🇻🇳", fontSize = 20.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "+84", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(8.dp))
            Divider(
                modifier = Modifier
                    .width(1.dp)
                    .height(24.dp)
                    .background(Color.Gray)
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                placeholder = { Text(texts["placeholder"] ?: "") },
                singleLine = true,
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                )
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Language switch
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            LanguageButton("Việt", selectedLang == "Việt") { selectedLang = "Việt" }
            Spacer(modifier = Modifier.width(8.dp))
            LanguageButton("Eng", selectedLang == "Eng") { selectedLang = "Eng" }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Continue button
        Button(
            onClick = {
                Toast.makeText(context, texts["success"] ?: "", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            },
            enabled = isPhoneValid,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = texts["continue"] ?: "")
        }
    }
}

@Composable
fun LanguageButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .height(36.dp)
            .border(
                1.dp,
                if (isSelected) Color(0xFFDD1582) else Color.Gray,
                RoundedCornerShape(8.dp)
            )
            .background(
                if (isSelected) Color(0x1ADD1582) else Color.Transparent,
                RoundedCornerShape(8.dp)
            )
    ) {
        Text(
            text = text,
            color = if (isSelected) Color(0xFFDD1582) else Color.Gray
        )
    }
}
