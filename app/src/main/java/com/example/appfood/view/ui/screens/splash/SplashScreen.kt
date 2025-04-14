package com.example.appfood.view.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.appfood.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController){
    LaunchedEffect(Unit) {
        delay(2000) // 2 s
        navController.navigate("get_started_1")
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = colorResource(R.color.darkBrown)),
        horizontalAlignment = Alignment.CenterHorizontally,)
    {
        ConstraintLayout (modifier = Modifier.padding(top = 48.dp))
        {
            val(backgroundImg,loginImg)=createRefs()
            Image(
                painterResource(id = R.drawable.intro_pic),
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(backgroundImg){
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                    .fillMaxWidth()
            )
            Image(
                painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.run {
                    constrainAs(loginImg){
                        top.linkTo(backgroundImg.top)
                        bottom.linkTo(backgroundImg.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                }.size(300.dp),
                contentScale = ContentScale.Fit
            )
        }
        val styledText = buildAnnotatedString {
            append("Welcome to ")
            withStyle(style = SpanStyle(color = colorResource(R.color.orange))){
                append("\nFood Delivery App")
            }
            append("\nexprience food perfection delivered")
        }
        Text(
            text = styledText,
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .padding(top = 32.dp)
                .padding(horizontal = 16.dp)
        )

    }
}