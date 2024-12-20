package com.example.myapp.compose

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.DialogProperties
import com.example.myapp.model.DataViewModel
import com.example.myapp.model.activityViewModel
import com.example.myapp.network.AccountManager
import com.example.myapp.network.joinHouse
import kotlinx.coroutines.launch

@Composable
fun AddDialog(
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
fun AddHouseDialog(goBack: () -> Unit = {}) {
    val accountViewModel: DataViewModel = activityViewModel()
    val data by accountViewModel.accountInfo.observeAsState()
    val accountId = data?.accountInfo?.accountId
    var houseIdInput by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    AddDialog(
        onCreate = {
            scope.launch {
                accountId?.let {
                    if (AccountManager.joinHouse(it, houseIdInput.toInt())) {
                        Toast.makeText(context, "加入家庭成功", Toast.LENGTH_SHORT)
                            .show()
                        goBack()
                    } else {
                        Toast.makeText(context, "加入家庭不存在", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        },
        title = "加入家庭",
        onDismissRequest = {
            goBack()
        },
        content = {

            TextField(
                value = houseIdInput,
                onValueChange = { houseIdInput = it },
                label = { Text("加入家庭ID") },
            )
        }
    )
}