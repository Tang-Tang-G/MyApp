package com.example.myapp.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapp.R

@Composable
fun TabLayout() {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("场景1", "场景2", "场景3", "场景2", "场景3")

    Column {
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            contentColor = Color.Blue
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) },
                    icon = {
                        Image(
                            painter = painterResource(R.drawable.house_icon),
                            contentDescription = "1",
                            modifier = Modifier.size(36.dp),
                            contentScale = ContentScale.FillBounds
                        )
                    }
                )
            }
        }
    }
}

