package com.example.appfood.view.notification

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appfood.R
import com.example.appfood.viewModel.NotificationViewModel
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.insets.ui.TopAppBar
@Composable
fun NotificationScreen(navController: NavController,notificationViewModel: NotificationViewModel) {
    val notifications = notificationViewModel.notifications

    // Đánh dấu tất cả là đã đọc khi mở màn hình
    LaunchedEffect(Unit) {
        notificationViewModel.markAllAsRead()
    }


    Scaffold(
        modifier = Modifier.fillMaxSize().statusBarsPadding(),
        topBar = {TopAppBar(title = { Box(modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center){
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()) {
                Image(
                    painterResource(R.drawable.back_grey),
                    contentDescription = null,
                    modifier = Modifier.clickable { navController.popBackStack() }
                )
                Text(
                    text = "Notifications",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.orange),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(48.dp))
            }

        }
        },
            backgroundColor = colorResource(R.color.white)
        ) },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            items(notifications) { notif ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Text(
                        text = notif,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
