package com.example.myapp.compose

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties

@Composable
fun DeleteDialog(
    name: String,
    onDismissRequest: () -> Unit,
    onDeleteConfirmed: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = "确认删除")
        },
        text = {
            Text("您确定要删除区域 “${name}” 吗？")
        },
        confirmButton = {
            Button(
                onClick = {
                    onDeleteConfirmed()
                }
            ) {
                Text("确认")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismissRequest
            ) {
                Text("取消")
            }
        },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    )
}
