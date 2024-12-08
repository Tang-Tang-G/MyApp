package com.example.myapp.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DropdownSelectMenu(
    items: List<String>,
    defaultItemValue: String,
    onSelect: (Int) -> Unit = {},
) {
    val itemsWithDefault = items.plus(defaultItemValue)
    var expanded by remember { mutableStateOf(false) }  // 控制菜单展开/折叠
    var selectedIndex by remember { mutableIntStateOf(0) }

    Surface(
        color = MaterialTheme.colorScheme.surfaceContainerHigh, shape = ShapeDefaults.Small
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .height(60.dp)
                .width(200.dp)
                .fillMaxSize()
                .clickable { expanded = !expanded }) {
            // 显示当前选中的项
            if (selectedIndex >= itemsWithDefault.size) {
                selectedIndex = 0
            }
            Text(
                text = itemsWithDefault[selectedIndex],
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.width(20.dp))
            // 触发按钮
            Icon(
                imageVector = Icons.Default.ArrowDropDown, contentDescription = "Open Dropdown Menu"
            )
        }


        // 条形下拉菜单
        DropdownMenu(
            expanded = expanded, onDismissRequest = { expanded = false },  // 点击外部关闭菜单
            modifier = Modifier.padding(top = 8.dp)
        ) {
            // 水平排列菜单项
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),  // 菜单项之间的间距
                horizontalAlignment = Alignment.Start
            ) {
                for ((index, item) in itemsWithDefault.withIndex()) {
                    DropdownMenuItem(text = { Text(item) }, onClick = {
                        selectedIndex = index
                        expanded = false
                        onSelect(index)
                    }, modifier = Modifier.padding(horizontal = 8.dp)  // 水平内边距
                    )
                }
            }
        }
    }
}

