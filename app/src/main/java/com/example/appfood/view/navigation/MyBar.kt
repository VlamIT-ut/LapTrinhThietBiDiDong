package com.example.appfood.view.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.appfood.R
import com.google.accompanist.insets.ui.BottomNavigation

@Composable
fun MyBottomBar() {

    BottomNavigation(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(), // Đẩy lên khỏi thanh điều hướng của hệ thống
        elevation = 3.dp,
        backgroundColor = colorResource(id = R.color.grey)
    ){
            Icon(
                painter = painterResource(id = R.drawable.btn_1),
                contentDescription = "Home",
                modifier = Modifier.size(32.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.btn_2),
                contentDescription = "Shopping Cart",
                modifier = Modifier.size(32.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.btn_3),
                contentDescription = "Favorite",
                modifier = Modifier.size(32.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.btn_4),
                contentDescription = "Order",
                modifier = Modifier.size(32.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.btn_5),
                contentDescription = "Profile",
                modifier = Modifier.size(32.dp),
            )


    }

}