package home.friendarea

import BattlenetColors
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.svgResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Banner() {
    Row(
        Modifier.fillMaxWidth().padding(start = 8.dp, end = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var avatarHover by remember { mutableStateOf(false) }
        Box(Modifier.size(48.dp).pointerMoveFilter(onEnter = {
            avatarHover = true
            true
        }, onExit = {
            avatarHover = false
            true
        })) {
            // Avatar
            Image(
                modifier = Modifier.fillMaxSize().clip(CircleShape),
                bitmap = imageResource("images/avatar/hitona.jpg"),
                contentDescription = "Avatar"
            )
            androidx.compose.animation.AnimatedVisibility(
                modifier = Modifier.matchParentSize(),
                visible = avatarHover,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(
                    Modifier.matchParentSize()
                        .border(width = 2.dp, color = Color.White.copy(ContentAlpha.medium), shape = CircleShape)
                        .clip(CircleShape)
                        .background(Color.Black.copy(ContentAlpha.medium))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {}
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = svgResource("images/dark-theme/edit.svg"),
                        contentDescription = "edit",
                        colorFilter = ColorFilter.tint(Color.White.copy(ContentAlpha.high))
                    )
                }
            }
            // Status
            Box(
                Modifier.size(16.dp).align(Alignment.BottomEnd).clip(CircleShape)
                    .background(BattlenetColors.background),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.size(10.dp),
                    painter = svgResource("images/dark-theme/status-online.svg"),
                    contentDescription = "status",
                    contentScale = ContentScale.Fit
                )
            }
        }
        Spacer(Modifier.width(8.dp))
        var nameHover by remember { mutableStateOf(false) }
        Column(
            Modifier
                .fillMaxWidth()
                .then(
                    if (nameHover) {
                        Modifier.background(color = Color.White.copy(0.06f), shape = RoundedCornerShape(4.dp))
                    } else Modifier
                )
                .pointerMoveFilter(onEnter = {
                    nameHover = true
                    true
                }, onExit = {
                    nameHover = false
                    true
                })
                .padding(horizontal = 4.dp, vertical = 4.dp)
        ) {
            // Name
            Text(text = "小东人鱼", color = BattlenetColors.nameColor)
            // Status
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "在线",
                    color = Color.White.copy(if (nameHover) ContentAlpha.high else ContentAlpha.medium),
                    fontSize = 14.sp
                )
                Spacer(Modifier.width(4.dp))
                Image(
                    modifier = Modifier.size(12.dp),
                    painter = svgResource("images/dark-theme/caret-down.svg"),
                    contentDescription = "expand",
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(Color.White.copy(if (nameHover) ContentAlpha.high else ContentAlpha.medium))
                )
            }
        }
    }
}
