package com.example.myapp.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapp.compose.BottomBar
import com.example.myapp.compose.DrawerContent
import com.example.myapp.compose.ContentTopBar
import com.example.myapp.model.NavigationModel
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun Content(screenNavController: NavController = rememberNavController()) {
    val bottomNavList = NavigationModel.bottomNavList

    // The current content page
    val pageSelection = remember { mutableIntStateOf(0) }
    // used for navigate different content page
    val bottomNavController = rememberNavController()

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { DrawerContent() }
    ) {
        Scaffold(
            topBar = {
                ContentTopBar(
                    title = {
                        Text(
                            text =  bottomNavList[pageSelection.intValue].label,

                        )
                    },
                    onMenuCLicked = {
                        scope.launch {
                            drawerState.apply {
                                if (isOpen) close() else open()
                            }
                        }
                    }
                )
            },
            bottomBar = {
                BottomBar(
                    navController = bottomNavController,
                    navigation = bottomNavList.toList(),
                    selectIndex = pageSelection
                )
            },
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            NavHost(
                navController = bottomNavController,
                startDestination = "overview",
                modifier = Modifier.padding(innerPadding)
            ) {
                bottomNavList.forEach { item ->
                    composable(item.navigate) {
                        item.composable()
                    }
                }
            }
        }
    }
}