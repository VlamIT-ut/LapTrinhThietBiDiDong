package com.example.appfood.view.detailEachFood

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appfood.R
import com.example.appfood.model.domain.FoodModel

@Composable
fun RowDetail(item: FoodModel, modifier: Modifier = Modifier){
    Row(
        modifier = modifier.padding(top = 32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.time_color),
            contentDescription = null
        )
        Text(
            text = "${item.TimeValue} min",
            modifier = Modifier.padding(start = 8.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = colorResource(R.color.darkPurple)
        )
        Spacer(modifier = Modifier.width(32.dp))
        Image(
            painter = painterResource(R.drawable.star),
            contentDescription = null
        )
        Text(
            text = "${item.Star}",
            modifier = Modifier.padding(start = 8.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = colorResource(R.color.darkPurple)
        )
        Spacer(modifier = Modifier.width(32.dp))
        Image(
            painter = painterResource(R.drawable.flame),
            contentDescription = null
        )
        Text(
            text = "${item.Calorie}",
            modifier = Modifier.padding(start = 8.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = colorResource(R.color.darkPurple)
        )

    }


}