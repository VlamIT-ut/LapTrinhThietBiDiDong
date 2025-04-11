package com.example.appfood.view.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appfood.R

@Composable
@Preview
fun Search(){
    var text by rememberSaveable { mutableStateOf("") }
    TextField(value = text,
        onValueChange = {text=it},
        label = {
            Text(text = "What would you like to eat",
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray
            )
        },
        trailingIcon = {
            Image(painter = painterResource(R.drawable.search),
                contentDescription = null,
                modifier = Modifier.size(22.dp)
                )
        },
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.colors(cursorColor = Color.Blue,
            focusedIndicatorColor = Color.Green,
            unfocusedIndicatorColor = Color.Black,
            disabledIndicatorColor = Color.Gray,
            focusedLabelColor = Color.Red,
            focusedContainerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(50.dp)
            .background(colorResource(R.color.grey), CircleShape)

    )
}