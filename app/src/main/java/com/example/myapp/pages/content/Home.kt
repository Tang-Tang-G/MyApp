package com.example.myapp.pages.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.example.myapp.compose.DeleteDialog
import com.example.myapp.compose.ExpandableNestedCards
import com.example.myapp.compose.composable
import com.example.myapp.model.AreaInfo
import com.example.myapp.model.HouseInfo
import com.example.myapp.model.Member
import com.example.myapp.network.AccountManager
import com.example.myapp.network.deleteArea
import com.example.myapp.network.deleteHouse
import com.example.myapp.network.fetchAreasInfo
import com.example.myapp.network.fetchMemberInfo
import kotlinx.coroutines.launch

@Composable
fun HomeView() {
    val scope = rememberCoroutineScope()
    var areasList by remember { mutableStateOf<List<AreaInfo>?>(null) }
    var showDeleteHouseDialog by remember { mutableStateOf(false) }
    var showDeleteAreaDialog by remember { mutableStateOf(false) }
    var areaToDelete by remember { mutableStateOf<AreaInfo?>(null) }
    var houseToDelete by remember { mutableStateOf<HouseInfo?>(null) }
    var update by remember { mutableStateOf(false) }
    var memberList by remember { mutableStateOf<Member?>(null) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)
        ) {
            LaunchedEffect(update) {
                scope.launch {
                    val members = AccountManager.fetchMemberInfo()
                    memberList = members
                }
            }
            Text(
                text = "家庭成员",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                fontStyle = FontStyle.Italic
            )
            memberList?.let {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(it.houseMember) { item ->
                        ExpandableNestedCards(
                            title = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ){
                                    Text(
                                        text = item.houseInfo.houseName,
                                        style = MaterialTheme.typography.titleLarge,
                                        color = MaterialTheme.colorScheme.primary,
                                    )
                                    Spacer(Modifier.weight(0.6f))
                                    Button(
                                        onClick = {
                                            showDeleteHouseDialog = true
                                            houseToDelete = item.houseInfo
                                        }
                                        ) {
                                        Text("删除", color = Color.White)
                                    }
                                }
                                if (showDeleteHouseDialog) {
                                    DeleteDialog(name = houseToDelete!!.houseName, onDismissRequest = {showDeleteHouseDialog = false}) {
                                        scope.launch{
                                            AccountManager.deleteHouse(houseToDelete!!.houseId)
                                        }
                                        showDeleteHouseDialog = false
                                        update = !update
                                    }
                                }
                            }
                        ) {
                            for (member in item.memberInfo) {
                                composable(
                                    title = {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = member.username,
                                                style = MaterialTheme.typography.titleMedium,
                                                color = MaterialTheme.colorScheme.secondary
                                            )
                                        }
                                    }
                                ) {
                                    Text(
                                        text = "accountId:" + member.accountId.toString(),
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                }
                            }
                        }

                    }

                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)
        ) {
            LaunchedEffect(update) {
                scope.launch {
                    val areas = AccountManager.fetchAreasInfo()
                    areasList = areas
                }
            }
            Text(
                text = "区域管理",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                fontStyle = FontStyle.Italic
            )
            areasList?.let { list ->
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ){
                    items(list) { item ->
                        ExpandableNestedCards(
                            title = {
                                Text(
                                    text = item.areaName,
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.primary,
                                )
                            }
                        ) {
                            composable(
                                title = {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Button(
                                            onClick = {
                                            }
                                        ) {
                                            Text(
                                                text = "重命名"
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Button(
                                            onClick = {
                                                showDeleteAreaDialog = true
                                                areaToDelete = item
                                            }
                                        ) {
                                            Text(
                                                text = "删除"
                                            )
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
                if(showDeleteAreaDialog)
                {
                    DeleteDialog(
                        name = areaToDelete!!.areaName,
                        onDismissRequest = {
                            showDeleteAreaDialog = false
                        },
                        onDeleteConfirmed = {
                            scope.launch {
                                AccountManager.deleteArea(areaToDelete!!.areaId)
                                showDeleteAreaDialog = false
                                update = !update
                            }
                        }
                    )}
                }
        }
        }
    }
