package com.example.myapp.pages.content

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapp.compose.DeviceItem
import com.example.myapp.compose.ExpandableNestedCards
import com.example.myapp.compose.composable
import com.example.myapp.model.DataViewModel
import com.example.myapp.model.activityViewModel
import com.example.myapp.network.AccountManager
import com.example.myapp.network.fetchData
import kotlinx.coroutines.launch

@Composable
fun OverView() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        val scope = rememberCoroutineScope()
        val deviceModel: DataViewModel = activityViewModel()
        val deviceList by deviceModel.accountInfo.observeAsState()

        LaunchedEffect(Unit) {
            scope.launch {
                val devices = AccountManager.fetchData()
                devices?.also { deviceModel.setData(it) }
            }
        }

        deviceList?.let {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(it.housesDevices) { item ->
                    ExpandableNestedCards(
                        title = {
                            Text(
                                text = item.houseInfo.houseName,
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }
                    ) {
                        for (area in item.areasDevices) {
                            composable(
                                title = {
                                    Text(
                                        text = area.areaInfo.areaName,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.secondary,
                                    )
                                }
                            ) {
                                for (device in area.devices) {
                                    var open by remember { mutableStateOf(false) }
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .animateContentSize()
                                            .padding(horizontal = 8.dp),
                                        elevation = CardDefaults.cardElevation(),
                                        shape = RoundedCornerShape(8.dp),
                                        onClick = { open = !open } // 点击切换展开/收缩状态
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp)
                                        ) {
                                            Text(
                                                text = device.deviceName,
                                                style = MaterialTheme.typography.titleSmall,
                                                color = MaterialTheme.colorScheme.secondary,
                                            )
                                            if (open) {
                                                Spacer(modifier = Modifier.height(8.dp))
                                                DeviceItem(device, open)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}