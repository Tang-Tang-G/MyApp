package com.example.myapp.compose

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.navigation.NavController
import com.example.myapp.model.Navigation


@Composable
fun BottomBar(
    navController: NavController,
    navigation: List<Navigation>,
    selectIndex: MutableIntState,
) {
    BottomAppBar {
        NavigationBar {
            navigation.forEachIndexed { index, navigation ->
                NavigationBarItem(
                    icon = navigation.icon,
                    label = { Text(navigation.label) },
                    selected = selectIndex.intValue == index,
                    onClick = {
                        selectIndex.intValue = index
                        navController.navigate(navigation.navigate)
                    }
                )
            }
        }
    }
}