package com.example.myapp.compose

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapp.R
import com.example.myapp.model.DeviceInfo
import com.example.myapp.network.apiWithToken
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

@Composable
fun DeviceItem(device: DeviceInfo, open: Boolean) {
    if (open) {
        Column {
            Row {
                val imageRes = when (device.deviceType.typeName) {
                    "light" -> R.drawable.light
                    "fan" -> R.drawable.fan
                    "tv" -> R.drawable.tv
                    "air-condition" -> R.drawable.air_condition
                    else -> R.drawable.default_device // TODO: change this picture
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
                        DeviceText(text = "设备名:")
                        DeviceText(text = device.deviceName)
                    }
                    Row {
                        DeviceText(text = "设备类型：")
                        DeviceText(text = device.deviceType.typeName)
                    }
                    Row {
                        DeviceText(text = "模型名：")
                        DeviceText(text = device.modelName)
                    }
                }
            }
            HorizontalDivider(modifier = Modifier.padding(20.dp))
            DeviceStatus(device.deviceId)
            HorizontalDivider(modifier = Modifier.padding(20.dp))
            DeviceControl(device.deviceId, device.service)
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

@Composable
fun DeviceStatus(deviceId: Int) {
    val scope = rememberCoroutineScope()
    var status: JsonObject? by remember { mutableStateOf(null) }
    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val response = apiWithToken.getDeviceStatus(deviceId)
                if (response.code != 200) {
                    throw Error("code != 200")
                }
                Log.i("FetchDeviceStatus", response.data.toString())
                status = response.data
            } catch (e: Exception) {
                Log.e("FetchDeviceStatus", e.message, e)
            }
        }
    }
    status?.let {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            for ((label, value) in it) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(50.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    when (value) {
                        is JsonObject -> {}
                        is JsonArray -> {}
                        is JsonNull -> {}
                        is JsonPrimitive -> {
                            Text(
                                text = value.content,
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}