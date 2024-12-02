package com.example.myapp.screens.pair

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
import com.example.myapp.navigateTo
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
    val scope = rememberCoroutineScope()
    var ssid by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var host by remember { mutableStateOf("") }
    var pairMessage by remember { mutableStateOf("Start Pairing") }


    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
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
                            visualTransformation = PasswordVisualTransformation(),

                            )
                        TextField(
                            value = host,
                            placeholder = { Text("Host (default empty)") },
                            onValueChange = { v -> host = v },
                        )

                        Spacer(Modifier.fillMaxHeight(0.3f))

                        Button(
                            onClick = { navController.navigateTo("connecting") },
                        ) {
                            Text(text = "连接")
                        }
                        Spacer(Modifier.fillMaxHeight(0.3f))
                    }
                }

                composable("connecting") {
                    LaunchedEffect(Unit) {
                        scope.launch {
                            val ok = withContext(Dispatchers.IO) {
                                WiFiDevicePairing(Pair.Message(ssid, password, host),
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
                            }

                            if (ok) {
                                pairMessage = "Success"
                            }
                        }
                    }

                    Connecting(
                        onDismissRequest = {

                        }
                    ) {
                        Text(
                            text = pairMessage,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
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