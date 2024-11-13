package com.example.myapp.compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController

// TODO:
class Navigation(val label: String, val navigate: String)


@Composable
fun BottomBar(navController: NavController) {
    var selectIndex by remember { mutableIntStateOf(0) }

    val items = listOf("首页", "家庭", "场景", "我的")
    val inner = listOf("overview", "home", "scene", "my")
    val icons = listOf(
        Icons.Filled.Home,
        Icons.Filled.Favorite,
        Icons.Filled.Build,
        Icons.Filled.AccountCircle
    )

    // TODO:
    val navigation = items.zip(inner).map { (l, n) -> Navigation(l, n) }

    BottomAppBar {
        NavigationBar {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = { Icon(icons[index], contentDescription = item) },
                    label = { Text(item) },
                    selected = selectIndex == index,
                    onClick = {
                        selectIndex = index
                        navController.navigate(inner[index])
                    },
                )
            }
        }
    }
}