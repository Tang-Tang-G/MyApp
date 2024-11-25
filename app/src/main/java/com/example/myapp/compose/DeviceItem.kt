package com.example.myapp.compose

import com.example.myapp.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.sp
import com.example.myapp.model.DeviceInfo

@Composable
fun DeviceItem(device: DeviceInfo, open: Boolean) {
    var isSwitchOn by remember { mutableStateOf(false) }
    if (open) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            // 动态加载图片
            val imageRes = when (device.deviceType.typeName) {
                "light" -> R.drawable.light
                "fan" -> R.drawable.fan
                "tv" -> R.drawable.tv
                else -> R.drawable.ic_launcher_background
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DeviceText(text = "开/关")
                    Switch(
                        checked = isSwitchOn,
                        onCheckedChange = { isChecked ->
                            isSwitchOn = isChecked
                        },
                        modifier = Modifier.scale(0.5f)
                    )
                }

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

