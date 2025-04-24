package com.example.appfood.view.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appfood.viewModel.OrderViewModel
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.insets.ui.TopAppBar
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appfood.R

@Composable
fun CancelOrderReasonScreen(
    orderId: String,
    navController: NavController,
    viewModel: OrderViewModel = viewModel()
) {
    var selectedReason by remember { mutableStateOf<String?>(null) }
    val reasons = listOf("Đặt nhầm", "Thời gian giao hàng lâu", "Thay đổi ý định", "Giá cao", "Lý do khác")

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
                    text = "Cancel order",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.orange),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(48.dp))
            }

        }
        },
            backgroundColor = colorResource(R.color.grey)
        ) },
    ) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .padding(16.dp)) {

            Text("Vui lòng chọn lý do bạn huỷ đơn hàng:", fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))
            reasons.forEach { reason ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedReason = reason }
                        .padding(vertical = 4.dp)
                ) {
                    RadioButton(
                        selected = selectedReason == reason,
                        onClick = { selectedReason = reason }
                    )
                    Text(reason, modifier = Modifier.padding(start = 8.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (selectedReason != null) {
                        viewModel.deleteOrder(orderId)
                        navController.popBackStack() // Quay lại
                    }
                },
                enabled = selectedReason != null,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Xác nhận huỷ")
            }
        }
    }
}
