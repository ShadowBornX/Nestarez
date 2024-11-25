package com.example.nestarez.componentesUI.fonts

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.nestarez.R

val fontFredoka = FontFamily(
    Font(resId = R.font.fredoka_regular, weight = FontWeight.Normal)
)

val fontInria = FontFamily(
    Font(resId = R.font.inriasans_regular, weight = FontWeight.Normal),
    Font(resId = R.font.inriasans_light, weight = FontWeight.Light),
    Font(resId = R.font.inriasans_bold, weight = FontWeight.Bold),
    Font(resId = R.font.inriasans_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(resId = R.font.inriasans_lightitalic, weight = FontWeight.Light, style = FontStyle.Italic),
    Font(resId = R.font.inriasans_bolditalic, weight = FontWeight.Bold, style = FontStyle.Italic)
)