package com.example.appfood.view.helper

import java.text.SimpleDateFormat
import java.util.*

fun Long.formatAsString(pattern: String = "HH:mm:ss dd/MM/yyyy"): String {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(Date(this))
} 