package com.example.myapp.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.myapp.database.Device

@Composable
fun BaseDeviceItem(device:Device) {
    Column {
        Text(
            text = device.name
        )
        Text(
            text = device.type
        )

    }
}

