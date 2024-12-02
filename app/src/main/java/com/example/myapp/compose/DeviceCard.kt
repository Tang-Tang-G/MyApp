package com.example.myapp.compose

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.myapp.model.AccountDevices
import com.example.myapp.network.executeDeviceService
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive


@Composable
fun DeviceList(accountDevices: AccountDevices) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(accountDevices.housesDevices) { item ->
            ExpandableNestedCards(
                title = { Text(text = item.houseInfo.houseName) }
            ) {
                for (area in item.areasDevices) {
                    composable(
                        title = { Text(text = area.areaInfo.areaName) }
                    ) {
                        LazyColumn {
                            items(area.devices) { item ->
                                Text(text = item.deviceName)
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun ExpandableCard() {
    var isExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .animateContentSize()
                .fillMaxWidth(if (isExpanded) 1f else 0.9f) // 展开时占满宽度
                .fillMaxHeight(if (isExpanded) 1f else 0.2f) // 展开时占满高度
                .zIndex(if (isExpanded) 1f else 0f), // 控制动画层级
            elevation = CardDefaults.cardElevation(),
            shape = RoundedCornerShape(if (isExpanded) 0.dp else 16.dp), // 展开时取消圆角
            onClick = { isExpanded = !isExpanded } // 点击切换状态
        ) {
            // 卡片内容
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.TopStart
            ) {
                if (isExpanded) {
                    // 展开后的内容
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Expanded Content",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Here is some more detailed information...")
                        ExpandableCard()
                    }
                } else {
                    // 收缩时的内容
                    Text(
                        text = "Click to Expand",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

class SubCardScope {
    class SubCardArgs(val title: @Composable () -> Unit, val content: @Composable () -> Unit)

    var contents: MutableList<SubCardArgs> = mutableListOf()

    val index: Int
        get() {
            return contents.size
        }
}

fun SubCardScope.composable(
    title: @Composable () -> Unit,
    content: @Composable () -> Unit = {
        Text(text = "Details of the sub card", style = MaterialTheme.typography.bodyMedium)
    }
) {
    this.contents.add(SubCardScope.SubCardArgs(title, content))
}

@Preview
@Composable
fun Test() {
    ExpandableNestedCards(
        title = {
            Text(
                text = "Parent Card",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    ) {
        composable(
            title = {
                Text(
                    text = "This is First SubCard",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        )
        composable(
            title = {
                Text(
                    text = "Sub Card $index",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        ) {
            Text(
                text = "I'm SubCard $index's details",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
fun ExpandableNestedCards(
    title: @Composable () -> Unit = {},
    subCardScope: SubCardScope.() -> Unit = {}
) {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedSubCard by remember { mutableStateOf<Int?>(null) }
    val subCard = SubCardScope()
    subCard.subCardScope()


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .animateContentSize(),
            elevation = CardDefaults.cardElevation(),
            shape = RoundedCornerShape(16.dp),
            onClick = { isExpanded = !isExpanded } // 点击展开父卡片
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // 父卡片内容
                title()
                Spacer(modifier = Modifier.height(8.dp))
                if (isExpanded) {
                    // 展开时显示嵌套的子卡片列表
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        subCard.contents.forEachIndexed { index, sub ->
                            SubCard(
                                isSelected = selectedSubCard == index,
                                title = sub.title,
                                onClick = {
                                    selectedSubCard = if (selectedSubCard == index) null else index
                                },
                                content = sub.content
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun SubCard(
    isSelected: Boolean,
    title: @Composable () -> Unit,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .padding(horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(),
        shape = RoundedCornerShape(8.dp),
        onClick = onClick // 点击切换展开/收缩状态
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            title()
            if (isSelected) {
                Spacer(modifier = Modifier.height(8.dp))
                content()
            }
        }
    }
}

@Composable
fun DeviceControl(services: List<JsonObject>) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize()
    ) {
        services.forEach { item ->
            ControlCompose(4, item.jsonObject)
        }
    }
}


@Composable
fun ControlCompose(deviceId: Int, service: JsonObject) {
    val type = service["type"]!!.jsonPrimitive.content
    val label = service["label"]!!.jsonPrimitive.content
    val callback = service["callback"]!!.jsonObject
    val scope = rememberCoroutineScope()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = label,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(2f),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium
        )
        when (type) {
            "boolean" -> {
                val onOpen = callback["onOpen"]!!.jsonObject
                val onClose = callback["onClose"]!!.jsonObject

                var open by remember { mutableStateOf(false) }
                Switch(
                    checked = open,
                    modifier = Modifier.weight(3f),
                    onCheckedChange = {
                        open = it
                        Log.d("Switch", "switched")
                        scope.launch {
                            executeDeviceService(
                                deviceId = deviceId,
                                serviceName = (if (it) onOpen else onClose)["service_name"]!!.jsonPrimitive.content,
                                method = (if (it) onOpen else onClose)["method"]!!.jsonPrimitive.content,
                                body = null,
                                contentType = null
                            )
                        }
                    },
                )
            }

            "range" -> {
                val onUpdate = callback["onUpdate"]!!.jsonObject

                var value by remember { mutableFloatStateOf(0.0f) }
                Slider(
                    value = value,
                    onValueChange = { value = it },
                    modifier = Modifier.weight(weight = 3f),
                    onValueChangeFinished = {
                        scope.launch {
                            executeDeviceService(
                                deviceId = deviceId,
                                serviceName = onUpdate["service_name"]!!.jsonPrimitive.content,
                                method = onUpdate["method"]!!.jsonPrimitive.content,
                                contentType = onUpdate["content_type"]!!.jsonPrimitive.content,
                                body = (value * 100).toInt().toString(),
                            )
                        }
                    }
                )
            }

            else -> {}
        }
    }
}