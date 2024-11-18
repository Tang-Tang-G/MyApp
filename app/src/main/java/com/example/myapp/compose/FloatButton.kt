package com.example.myapp.compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

@Composable
fun ContentFloatButton(
    onClick: () -> Unit,
    show: Boolean,
) {
    if (show) {
        FloatingActionButton(
            onClick,
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "add device")
        }
    }
}