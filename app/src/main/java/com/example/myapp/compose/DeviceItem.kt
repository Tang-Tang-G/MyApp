package com.example.myapp.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapp.R
import com.example.myapp.model.DeviceInfo

@Composable
fun DeviceItem(device: DeviceInfo, open: Boolean) {
    if (open) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            val imageRes = when (device.deviceType.typeName) {
                "light" -> R.drawable.light
                "fan" -> R.drawable.fan
                "tv" -> R.drawable.tv
                else -> R.drawable.ic_launcher_background // TODO: change this picture
            }
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "${device.deviceType.typeName} 图标",
                modifier = Modifier
                    .width(64.dp)
                    .fillMaxHeight()
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Row {
                    DeviceText(
                        text = "设备名:"
                    )
                    DeviceText(
                        text = device.deviceName,
                    )
                }
                Row {
                    DeviceText(
                        text = "设备类型："

                    )
                    DeviceText(
                        text = device.deviceType.typeName,

                        )
                }
                Row {
                    DeviceText(
                        text = "模型名："

                    )
                    DeviceText(
                        text = device.modelName,

                        )
                }
                DeviceControl(device.service)
            }
        }
    } else {
        Text(
            device.deviceName,
            fontSize = 12.sp
        )
    }
}

@Composable
fun DeviceText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.secondary,
        fontSize = 12.sp
    )
}

