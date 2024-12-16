package com.example.myapp.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.myapp.model.DataViewModel
import com.example.myapp.model.activityViewModel
import com.example.myapp.network.AccountManager
import com.example.myapp.network.deleteHouse
import kotlinx.coroutines.launch

@Composable
fun ExitDialog(
    onDismissRequest: () -> Unit,
    onCreate: () -> Unit,
    title: String = "添加",
    content: @Composable () -> Unit = {}
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(title) },
        text = {
            Column {
                content()
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onCreate()
                    onDismissRequest()
                }
            ) {
                Text("退出")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("取消")
            }
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        )
    )
}

@Composable
fun ExitHouseDialog(goBack: () -> Unit = {}) {
    val deviceModel: DataViewModel = activityViewModel()
    val data by deviceModel.accountInfo.observeAsState()
    val houses = data?.housesDevices
    val houseNames = houses?.map { it.houseInfo.houseName } ?: listOf()
    val houseIds = houses?.map { it.houseInfo.houseId } ?: listOf()
    var houseIndex by remember { mutableIntStateOf(0) }
    val scope = rememberCoroutineScope()
    LocalContext.current
    ExitDialog(
        onCreate = {
            scope.launch {
                var houseId = houseIds.getOrNull(houseIndex)
                houseId?.let { AccountManager.deleteHouse(it) }
            }
        },
        title = "退出家庭",
        onDismissRequest = {
            goBack()
        },
        content = {
            DropdownSelectMenu(items = houseNames,
                defaultItemValue = "",
                onSelect = { houseIndex = it })
            Spacer(modifier = Modifier.size(10.dp))
        }
    )
}