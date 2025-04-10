package com.example.appfood.view.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appfood.R

@Composable

fun GetStartedScreen1(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.darkBrown)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Indicator (3 dots)
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                for (i in 0..2) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(if (i == 0) colorResource(R.color.orange) else Color.LightGray)
                    )
                }
            }

            // Skip button
            Text(
                text = "skip",
                color = colorResource(R.color.orange),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable {navController.navigate("get_started_2") }
            )
        }
       Column (horizontalAlignment = Alignment.CenterHorizontally){
           Image(
               painter = painterResource(id = R.drawable.image1_1), // Thay bằng ảnh của bạn
               contentDescription = "Time Management",
               modifier = Modifier.size(250.dp)
           )

           Spacer(modifier = Modifier.height(16.dp))

           // Tiêu đề
           Text(
               text = "Order Easily, Anytime, Anywhere",
               color = colorResource(R.color.orange),
               fontSize = 20.sp,
               fontWeight = FontWeight.Bold,
               textAlign = TextAlign.Center
           )

           Spacer(modifier = Modifier.height(8.dp))

           // Nội dung mô tả
           Text(
               text = "Just a few taps, and your favorite food is on its way, " +
                       "no matter where you are or what time it is",
               fontSize = 14.sp,
               color = Color.White,
               textAlign = TextAlign.Center,
               modifier = Modifier.padding(horizontal = 16.dp)
           )
       }



        Button(onClick = {navController.navigate("get_started_2")},
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.orange),
                contentColor = Color.White),) {
            Text(text = "Next", fontSize = 16.sp, color = Color.White)
        }
    }

}