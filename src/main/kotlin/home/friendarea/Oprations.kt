package home.friendarea

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.ContentAlpha
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.svgResource
import androidx.compose.ui.unit.dp
import component.BattlenetIconButton
import component.BattlenetTextField

@Composable
fun Oprations() {
    Row(Modifier.fillMaxWidth().padding(start = 8.dp, end = 24.dp)) {
        BattlenetIconButton(onClick = {}) {
            Image(
                painter = svgResource("images/dark-theme/addfriend.svg"),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White.copy(ContentAlpha.medium))
            )
        }
        Spacer(Modifier.width(8.dp))
        BattlenetIconButton(onClick = {}) {
            Image(
                painter = svgResource("images/dark-theme/friend-list-setting.svg"),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White.copy(ContentAlpha.medium))
            )
        }
        Spacer(Modifier.width(8.dp))
        var value by remember { mutableStateOf("") }
        BattlenetTextField(
            modifier = Modifier.weight(1f),
            value = value,
            onValueChange = { value = it },
            trailingIcon = {
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = svgResource("images/dark-theme/search.svg"),
                    contentDescription = "Search",
                    colorFilter = ColorFilter.tint(Color.White.copy(ContentAlpha.medium))
                )
            }
        )
    }
}
