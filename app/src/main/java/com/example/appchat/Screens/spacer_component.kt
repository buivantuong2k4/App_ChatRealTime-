package com.example.appchat.Screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SpacerWith(
    width: Dp= 10.dp
){
    Spacer(modifier = Modifier.width(width))
}

@Composable
fun SpacerHeight(
    height: Dp= 10.dp
){
    Spacer(modifier = Modifier.height(height))
}