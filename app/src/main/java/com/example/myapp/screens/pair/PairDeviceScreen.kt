package com.example.myapp.screens.pair

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapp.compose.TopBarWithBack
import com.example.myapp.screens.pair.Pair.PairCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Preview(showBackground = true)
@Composable
fun PairDevice(
    goBack: () -> Unit = {},
) {
    val navController = rememberNavController()
    val title by remember { mutableStateOf("设备绑定") }
    Column(modifier = Modifier.fillMaxSize()) {
        TopBarWithBack(
            title = title,
            goBack = goBack,
        )
        NavHost(
            navController = navController,
            startDestination = "start",
            enterTransition = {
                slideInHorizontally { fullWidth -> fullWidth }
            },
            exitTransition = {
                slideOutHorizontally { fullWidth -> -fullWidth } // 页面向左侧退出
            },
            popEnterTransition = {
                slideInHorizontally { fullWidth -> -fullWidth } // 返回时从左侧进入
            },
            popExitTransition = {
                slideOutHorizontally { fullWidth -> fullWidth } // 返回时向右侧退出
            }
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
                var ssid by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }
                var host by remember { mutableStateOf("") }
                var showDialog by remember { mutableStateOf(false) }
                val scope = rememberCoroutineScope()
                var pairMessage by remember { mutableStateOf("Start Pairing") }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    TextField(
                        value = ssid,
                        onValueChange = { v -> ssid = v },
                        placeholder = { Text("SSID") }
                    )
                    TextField(
                        value = password,
                        onValueChange = { v -> password = v },
                        placeholder = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation(),
                    )

                    TextField(
                        value = host,
                        placeholder = { Text("Host (default empty)") },
                        onValueChange = { v -> host = v }
                    )

                    Button(
                        onClick = {
                            showDialog = true
                            scope.launch {
                                withContext(Dispatchers.IO) {
                                    val ok = WiFiDevicePairing(Pair.Message(ssid, password, host),
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
                                        .pair()

                                    // TODO: based on the `ok` to update the dialog compose
                                }
                            }
                        }
                    ) {
                        Text(text = "连接")
                    }

                    if (showDialog) {
                        Connecting(onDismissRequest = { showDialog = false })
                    }
                }
            }
        }
    }
}

@Composable
fun Connecting(onDismissRequest: () -> Unit) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(0.8f),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    }
}