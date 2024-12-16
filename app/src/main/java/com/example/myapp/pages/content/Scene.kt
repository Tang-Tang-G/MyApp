package com.example.myapp.pages.content

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class RecommendSceneInfo(val name: String, val imageResourceName: String)

@Composable
fun getRecommendedScenesFromFile(context: Context): List<RecommendSceneInfo> {
    // 模拟从文件中读取数据
    return listOf(
        RecommendSceneInfo("睡觉", "zzz_line"),
        RecommendSceneInfo("起床", "get_up"),
        RecommendSceneInfo("离家", "leave_home"),
        RecommendSceneInfo("洗澡", "bath"),
        RecommendSceneInfo("煮饭", "cook"),
        RecommendSceneInfo("运动", "exercise"),
        RecommendSceneInfo("电竞", "games"),
        RecommendSceneInfo("派对", "party"),
    )
}

@Preview
@Composable
fun SceneView(
    onRecommendSceneClick: () -> Unit = {},
) {
    rememberCoroutineScope()
    var selectedTabIndex by remember { mutableStateOf(0) }
    listOf("场景1", "场景2", "场景3")
    val context = LocalContext.current
    val recommendSceneList = listOf(
        RecommendSceneInfo("睡觉", "zzz_line"),
        RecommendSceneInfo("起床", "get_up"),
        RecommendSceneInfo("离家", "leave_home"),
        RecommendSceneInfo("洗澡", "bath"),
        RecommendSceneInfo("煮饭", "cook"),
        RecommendSceneInfo("运动", "exercise"),
        RecommendSceneInfo("电竞", "games"),
        RecommendSceneInfo("派对", "party"),
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            ListItem(
                headlineContent = { Text("推荐场景") },
                trailingContent = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null
                    )
                },
                modifier = Modifier.clickable { onRecommendSceneClick() }
            )
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                contentColor = MaterialTheme.colorScheme.secondary,
            ) {
                recommendSceneList.forEachIndexed { index, recommendInfo ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(recommendInfo.name) },
                        icon = {
                            val imageResourceId = context.resources.getIdentifier(
                                recommendInfo.imageResourceName,
                                "drawable",
                                context.packageName
                            )
                            if (imageResourceId != 0) {
                                Image(
                                    painter = painterResource(id = imageResourceId),
                                    contentDescription = recommendInfo.name,
                                    modifier = Modifier.size(36.dp),
                                    contentScale = ContentScale.FillBounds
                                )
                            } else {
                                Text("N/A")
                            }
                        }
                    )
                }
            }

        }
    }
}