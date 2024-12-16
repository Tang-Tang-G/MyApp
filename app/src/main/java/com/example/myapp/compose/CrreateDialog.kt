package com.example.myapp.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.myapp.model.DataViewModel
import com.example.myapp.model.HouseCreate
import com.example.myapp.model.activityViewModel
import com.example.myapp.network.AccountManager
import com.example.myapp.network.apiWithToken
import com.example.myapp.network.crateHouse
import com.example.myapp.network.createArea
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CreateDialog(
    onDismissRequest: () -> Unit,
    onCreate: () -> Unit,
    title: String = "创建",
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
                }
            ) {
                Text("创建")
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
fun CreateHouseDialog(goBack: () -> Unit = {}) {
    val houseName = remember { mutableStateOf("") }
    var isDuplicate by remember { mutableStateOf(false) }
    val deviceModel: DataViewModel = activityViewModel()
    val data by deviceModel.accountInfo.observeAsState()
    val houses = data?.housesDevices
    var houseList = houses?.map { it.houseInfo.houseName } ?: listOf()
    val scope = rememberCoroutineScope()

    CreateDialog(
        onCreate = {
            //创建家庭的具体操作
            if (houseList.any { it == houseName.value }) {
                isDuplicate = true
            } else {
                isDuplicate = false
                scope.launch {
                    AccountManager.crateHouse(houseName.value)
                }
                goBack()
            }
        },
        title = "创建家庭",
        onDismissRequest = {
            goBack()
        },
        content = {
            TextField(
                value = houseName.value,
                onValueChange = { houseName.value = it },
                label = { Text("输入新建家庭名称") },
            )
            if (isDuplicate) {
                Text(
                    text = "家庭名称重复，请重新输入",
                    color = Color.Red
                )
            }
        }
    )
}

@Composable
fun CreateAreaDialog(goBack: () -> Unit = {}) {
    val deviceModel: DataViewModel = activityViewModel()
    val data by deviceModel.accountInfo.observeAsState()
    val houses = data?.housesDevices
    val houseNames = houses?.map { it.houseInfo.houseName } ?: listOf()
    val houseIds = houses?.map { it.houseInfo.houseId } ?: listOf()
    var houseIndex by remember { mutableIntStateOf(0) }
    var newHouseName by remember { mutableStateOf("") }
    val areaName = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    CreateDialog(
        onCreate = {
            scope.launch {
                var houseId = houseIds.getOrNull(houseIndex)
                if (houseIndex >= houseNames.size) {
                    val resp = apiWithToken.createHouse(HouseCreate(newHouseName))
                    if (resp.code != 200 || resp.data == null) {
                        delay(100)
                        throw Error("new house response error")
                    }
                    houseId = resp.data
                }
                if (areaName.value != "") {
                    houseId?.let { AccountManager.createArea(it, areaName.value) }
                }
            }
            goBack()
        },
        title = "创建区域",
        onDismissRequest = {
            goBack()
        },
        content = {
            DropdownSelectMenu(items = houseNames,
                defaultItemValue = "新建家庭",
                onSelect = { houseIndex = it })
            Spacer(modifier = Modifier.size(10.dp))
            if (houseIndex >= houseNames.size) {
                TextField(value = newHouseName,
                    onValueChange = { newHouseName = it },
                    placeholder = { Text("家庭名") })
                Spacer(modifier = Modifier.height(50.dp))
            }
            TextField(
                value = areaName.value,
                onValueChange = { areaName.value = it },
                label = { Text("新建区域名称") },
            )
        }
    )
}

//TODO(未完成)
@Composable
fun CreateSceneDialog(goBack: () -> Unit = {}) {
    val familyName = remember { mutableStateOf("") }
    val areaName = remember { mutableStateOf("") }
    CreateDialog(
        onCreate = {

        },
        title = "创建场景",
        onDismissRequest = {
            goBack()
        },
        content = {
            TextField(
                value = familyName.value,
                onValueChange = { familyName.value = it },
                label = { Text("家庭名称") },
            )
            Spacer(modifier = Modifier.size(20.dp))
            TextField(
                value = areaName.value,
                onValueChange = { areaName.value = it },
                label = { Text("新建场景名称") },
            )
            //需要设备列表来实现。

        }
    )
}
