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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Divider
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Switch
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appfood.R
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.insets.ui.TopAppBar

@Composable
fun SettingsScreen(
    navController: NavController,
    currentLang: String,
    onLanguageToggle: () -> Unit,
    onAccountDeleteClick: () -> Unit
)
 {
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
                    text = stringResource(R.string.settings),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.orange),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(48.dp))
            }

        }
        },
            backgroundColor = colorResource(R.color.white)) },
    ) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {

            val langName = if (currentLang == "vi") {
                stringResource(R.string.language_vietnamese)
            } else {
                stringResource(R.string.language_english)
            }
            SettingItem(
                title = stringResource(R.string.change_language),
                value = langName,
                onClick = onLanguageToggle,
                showSwitch = true,
                switchChecked = currentLang == "vi",
                onSwitchChange = onLanguageToggle
            )
            Divider()
            SettingItem(title = stringResource(R.string.help), enabled = false, onClick = {})
            Divider()
            SettingItem(title = stringResource(R.string.request_account_deletion), onClick = onAccountDeleteClick)
        }
    }
}

@Composable
fun SettingItem(title: String, value: String? = null, enabled: Boolean = true, onClick: () -> Unit, showSwitch: Boolean = false, switchChecked: Boolean = false, onSwitchChange: (() -> Unit)? = null) {
    val textColor = if (enabled) Color.Black else Color.Gray
    val modifier = if (enabled) Modifier
        .fillMaxWidth()
        .clickable { onClick() }
        .padding(16.dp) else Modifier
        .fillMaxWidth()
        .padding(16.dp)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, color = textColor)
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (value != null) {
                Text(text = value, color = textColor)
            }
            if (showSwitch && onSwitchChange != null) {
                Switch(
                    checked = switchChecked,
                    onCheckedChange = { onSwitchChange() },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = colorResource(R.color.orange),
                        uncheckedThumbColor = colorResource(R.color.grey)
                    ),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}
