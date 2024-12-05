package com.example.myapp.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.DialogProperties

@Composable
fun AddDialog(
    onDismissRequest: () -> Unit,
    onCreate: () -> Unit,
    title: String="添加",
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
                Text("添加")
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
fun AddHouseDialog(goBack :()->Unit={})
{
    val houseName = remember { mutableStateOf("") }
    AddDialog(
        onCreate = {
            //加入家庭的具体操作
        },
        title = "加入家庭",
        onDismissRequest = {
            goBack()
        },
        content = {
            TextField(
                value = houseName.value,
                onValueChange = { houseName.value = it },
                label = { Text("加入家庭名称") },
            )
        }
    )
}