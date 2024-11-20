package com.example.myapp.compose

import com.example.myapp.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapp.database.Device
import androidx.compose.foundation.lazy.items

@Composable
fun DeviceItem(device: Device) {
    var isSwitchOn by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .height(100.dp)
    ) {
        // 动态加载图片
        val imageRes = when (device.type) {
            "light" -> R.drawable.light
            "fan" -> R.drawable.fan
            else -> R.drawable.tv
        }
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "${device.type} 图标",
            modifier = Modifier
                .width(100.dp)
                .fillMaxHeight()
        )
        Column(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Row {
                Text(
                    text = "设备名："
                )
                Text(
                    text = device.name
                )
            }
            Row {
                Text(
                    text = "设备类型："
                )
                Text(
                    text = device.type
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "开/关")
                Switch(
                    checked = isSwitchOn,
                    onCheckedChange = { isChecked ->
                        isSwitchOn = isChecked
                    }
                )
            }

        }
    }
}

@Preview
@Composable
fun DeviceItemPreview() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        // 创建一个示例设备列表
        val devices = listOf(
            Device(100, "设备1", "light"),
            Device(101, "设备2", "fan"),
            Device(102, "设备3", "tv"),
            Device(103, "设备4", "light"),
            Device(104, "设备5", "fan"),
            Device(105, "设备1", "light"),
            Device(106, "设备2", "fan"),
            Device(107, "设备3", "tv"),
            Device(108, "设备4", "light"),
            Device(109, "设备5", "fan"),
            Device(110, "设备1", "light"),
            Device(111, "设备2", "fan"),
            Device(112, "设备3", "tv"),
            Device(113, "设备4", "light"),
            Device(114, "设备5", "fan"),
            Device(109, "设备5", "fan"),
            Device(110, "设备1", "light"),
            Device(111, "设备2", "fan"),
            Device(112, "设备3", "tv"),
            Device(113, "设备4", "light"),
            Device(114, "设备5", "fan")
        )

        // 使用 LazyColumn 显示设备列表
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(devices) { device ->
                DeviceItem(device = device)
            }
        }

    }

}