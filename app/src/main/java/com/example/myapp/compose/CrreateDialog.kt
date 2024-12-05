package com.example.myapp.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

@Composable
fun CreateDialog(
    onDismissRequest: () -> Unit,
    onCreate: () -> Unit,
    title: String="创建",
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
fun CreateHouseDialog(goBack :()->Unit={}) {
    val houseName = remember { mutableStateOf("") }
    CreateDialog(
        onCreate = {
            //创建家庭的具体操作
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
        }
    )
}
@Composable
fun CreateAreaDialog(goBack :()->Unit={}) {
    val houseName = remember { mutableStateOf("") }
    val areaName = remember { mutableStateOf("") }
    CreateDialog(
        onCreate = {
            //创建区域的具体操作

        },
        title = "创建区域",
        onDismissRequest = {
            goBack()
        },
        content = {
            TextField(
                value = houseName.value,
                onValueChange = { houseName.value = it },
                label = { Text("家庭名称") },
            )
                Spacer(modifier = Modifier.size(20.dp))
            TextField(
                value = areaName.value,
                onValueChange = { areaName.value = it },
                label = { Text("新建区域名称") },
            )
        }
    )
}
@Composable
fun CreateSceneDialog(goBack :()->Unit={})
{
    val familyName = remember { mutableStateOf("") }
    val areaName = remember { mutableStateOf("") }
    CreateDialog(
        onCreate = {
            //创建区域的具体操作
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
@Preview
@Composable
 fun Preview()
{
    CreateAreaDialog()
 }
