package com.example.appfood.view.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appfood.R


@Composable
fun DeliveryInfoBox(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .background(color = colorResource(R.color.grey), shape = RoundedCornerShape(10.dp))
            .padding(8.dp)
    ){
        InfoItem(
            title = "Your Delivery Address",
            content = "NY-downtown-no97",
            icon = painterResource(id = R.drawable.location),
            navController = navController
        )
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        InfoItem(
            title = "Payment Method",
            content = "Momo",
            icon = painterResource(id = R.drawable.momo),
            navController = navController

        )
    }
    Button(
        onClick = {navController.navigate("success")},
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.orange)
        ),
        modifier = Modifier
            .padding(vertical = 32.dp)
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text("Place Order", fontSize = 18.sp,
            color = Color.White)
    }
}
@Composable
fun InfoItem(title: String, content: String, icon: Painter,navController: NavController) {
    Column {
        Text(text = title, fontSize = 14.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().clickable { if(title == "Payment Method")
            {navController.navigate("payment")}else{navController.navigate("address")} }) {
            Image(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp)

            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = content,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        var selected by remember { mutableStateOf(false) }

        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()) {
            if(title == "Payment Method"){
                Text("Cash on Delivery",fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,modifier = Modifier.weight(1f))
                RadioButton(
                    selected = selected,
                    onClick = { selected = !selected }, // Toggle như checkbox
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color.Green,
                        unselectedColor = Color.Gray
                    )
                )
            }else{

            }
        }

    }
}