package com.example.myapp.screens.pair

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapp.compose.DropdownSelectMenu
import com.example.myapp.compose.TopBarWithBack
import com.example.myapp.model.AreaAdd
import com.example.myapp.model.DataViewModel
import com.example.myapp.model.DeviceAdd
import com.example.myapp.model.HouseCreate
import com.example.myapp.model.activityViewModel
import com.example.myapp.navigateTo
import com.example.myapp.network.apiWithToken
import com.example.myapp.screens.pair.Pair.PairCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Preview(showBackground = true)
@Composable
fun PairDevice(
    goBack: () -> Unit = {},
) {
    val navController = rememberNavController()
    val title by remember { mutableStateOf("设备绑定") }
    val scope = rememberCoroutineScope()

    var ssid by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var host by remember { mutableStateOf("") }

    var eFuseMac by remember { mutableStateOf("") }
    var modelId: Int? by remember { mutableStateOf(null) }

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBarWithBack(
                title = title,
                goBack = goBack,
            )
            NavHost(navController = navController, startDestination = "start", enterTransition = {
                slideInHorizontally { fullWidth -> fullWidth }
            }, exitTransition = {
                slideOutHorizontally { fullWidth -> -fullWidth } // 页面向左侧退出
            }, popEnterTransition = {
                slideInHorizontally { fullWidth -> -fullWidth } // 返回时从左侧进入
            }, popExitTransition = {
                slideOutHorizontally { fullWidth -> fullWidth } // 返回时向右侧退出
            }, modifier = Modifier.fillMaxWidth()
            ) {
                composable("start") {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "请扫描设备背后的二维码来连接至设备WiFi",
                            fontSize = 25.sp,
                            modifier = Modifier.fillMaxWidth(0.7f),
                            textAlign = TextAlign.Center
                        )
                        Button(onClick = {
                            navController.navigate("input")
                        }) {
                            Text("下一步")
                        }
                    }
                }

                composable("input") {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        Spacer(Modifier.fillMaxHeight(0.1f))

                        TextField(
                            value = ssid,
                            onValueChange = { v -> ssid = v },
                            placeholder = { Text("SSID") },
                        )
                        TextField(
                            value = password,
                            onValueChange = { v -> password = v },
                            placeholder = { Text("Password") },
                            visualTransformation = PasswordVisualTransformation()
                        )
                        TextField(
                            value = host,
                            placeholder = { Text("Host (default empty)") },
                            onValueChange = { v -> host = v },
                        )

                        Spacer(Modifier.fillMaxHeight(0.3f))

                        Button(
                            onClick = {
                                navController.navigateTo("connecting")
                            },
                        ) {
                            Text(text = "连接")
                        }
                        Spacer(Modifier.fillMaxHeight(0.3f))
                    }
                }

                composable("connecting") {
                    var pairMessage by remember { mutableStateOf("Start Pairing") }
                    var job: Job? = null
                    LaunchedEffect(Unit) {
                        job = scope.launch {
                            val msg = withContext(Dispatchers.IO) {
                                val pair = WiFiDevicePairing(
                                    Pair.Message(ssid, password, host),
                                    object : PairCallback {
                                        override fun onMessageOk() {
                                            pairMessage = "success send message"
                                        }

                                        override fun onAskConfig() {
                                            pairMessage = "ready to send message"
                                        }

                                        override fun onMessageErr() {
                                            pairMessage = "message error"
                                        }

                                        override fun onWiFiConnected() {
                                            pairMessage = "WiFi connected"
                                        }

                                        override fun onHostConnected() {
                                            pairMessage = "host connected"
                                        }

                                        override fun onOtherError() {
                                            pairMessage = "unknown error"
                                        }
                                    })
                                if (pair.pair()) {
                                    pair.getDeviceInfo()
                                } else {
                                    null
                                }
                            }

                            var ok = false
                            if (msg != null) {
                                for (item in msg.split(",")) {
                                    val l = item.split(":")
                                    if (l.size == 2) {
                                        try {
                                            when (l[0]) {
                                                "efuse_mac" -> eFuseMac = l[1]
                                                "model_id" -> modelId = l[1].toInt()
                                            }
                                        } catch (e: Exception) {
                                            continue
                                        }
                                    }
                                }
                                if (eFuseMac.isNotEmpty() && modelId != null) {
                                    ok = true
                                    Log.d("DevicePairInfo", "mac: $eFuseMac, modelId: $modelId")
                                }
                            }

                            if (ok) {
                                pairMessage = "Success"
                                delay(100)
                                navController.navigateTo("success")
                            } else {
                                pairMessage = "设备绑定失败"
                            }
                        }
                    }

                    Connecting(
                        onDismissRequest = {
                            job?.cancel()
                            navController.navigateTo("input")
                        }
                    ) {
                        Text(
                            text = pairMessage,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                composable("success") {
                    val deviceModel: DataViewModel = activityViewModel()
                    var name by remember { mutableStateOf("") }
                    val data by deviceModel.accountInfo.observeAsState()

                    val houses = data?.housesDevices
                    val houseNames = houses?.map { it.houseInfo.houseName } ?: listOf()
                    val houseIds = houses?.map { it.houseInfo.houseId } ?: listOf()
                    var houseIndex by remember { mutableIntStateOf(0) }

                    val areas = houses?.let {
                        if (houseIndex >= it.size) {
                            null
                        } else {
                            it[houseIndex].areasDevices
                        }
                    }

                    val areaNames = areas?.map { it.areaInfo.areaName } ?: listOf()
                    val areaIds = areas?.map { it.areaInfo.areaId } ?: listOf()
                    var areaIndex by remember { mutableIntStateOf(0) }

                    var newHouseName by remember { mutableStateOf("") }
                    var newAreaName by remember { mutableStateOf("") }

                    val context = LocalContext.current

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "设备名称",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary,
                        )
                        Spacer(modifier = Modifier.height(50.dp))
                        TextField(
                            value = name,
                            onValueChange = { name = it },
                            placeholder = { Text("设备名") },
                        )
                        Spacer(modifier = Modifier.height(50.dp))
                        Text(
                            text = "选择家庭",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(50.dp))

                        DropdownSelectMenu(items = houseNames,
                            defaultItemValue = "添加家庭",
                            onSelect = { houseIndex = it })

                        Spacer(modifier = Modifier.height(10.dp))

                        if (houseIndex >= houseNames.size) {
                            TextField(value = newHouseName,
                                onValueChange = { newHouseName = it },
                                placeholder = { Text("家庭名") })
                            Spacer(modifier = Modifier.height(50.dp))
                            Text(
                                text = "选择区域",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(50.dp))
                            TextField(value = newAreaName,
                                onValueChange = { newAreaName = it },
                                placeholder = { Text("区域名") })
                        } else {
                            Text(
                                text = "选择区域",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(50.dp))
                            DropdownSelectMenu(items = areaNames,
                                defaultItemValue = "添加区域",
                                onSelect = { areaIndex = it })
                            Spacer(modifier = Modifier.height(10.dp))
                            if (areaIndex >= areaNames.size) {
                                TextField(value = newAreaName,
                                    onValueChange = { newAreaName = it },
                                    placeholder = { Text("区域名") })
                            }
                        }
                        Spacer(modifier = Modifier.height(50.dp))
                        Button(onClick = {
                            scope.launch {
                                var areaId = areaIds.getOrNull(areaIndex)
                                var houseId = houseIds.getOrNull(houseIndex)

                                try {
                                    // New House
                                    if (houseIndex >= houseNames.size) {
                                        val resp =
                                            apiWithToken.createHouse(HouseCreate(newHouseName))
                                        if (resp.code != 200 || resp.data == null) {
                                            delay(100)
                                            throw Error("new house response error")
                                        }
                                        houseId = resp.data
                                    }

                                    // New Area
                                    if (areas == null || areaIndex >= areaNames.size) {
                                        val resp = apiWithToken.addArea(
                                            AreaAdd(
                                                houseId = houseId!!,
                                                areaName = newAreaName,
                                            )
                                        )
                                        if (resp.code != 200 || resp.data == null) {
                                            throw Error("new area response error")
                                        }
                                        areaId = resp.data
                                    }

                                    val resp = apiWithToken.addDevice(
                                        DeviceAdd(
                                            eFuseMac = eFuseMac,
                                            deviceName = name,
                                            modelId = modelId!!,
                                            areaId = areaId!!,
                                        )
                                    )
                                    if (resp.code != 200 || resp.data == null) {
                                        throw Error("add device response error")
                                    }
                                    scope.launch {
                                        Toast.makeText(
                                            context,
                                            "绑定成功",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        goBack()
                                    }
                                } catch (e: Exception) {
                                    Log.d("PairDevice", e.message, e)
                                    scope.launch {
                                        Toast.makeText(
                                            context,
                                            e.message ?: "网络绑定失败",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            }
                        }) {
                            Text(
                                text = "上传绑定", style = MaterialTheme.typography.titleSmall
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Connecting(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxSize()
            ) {
                content()
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
        }
    }
}
