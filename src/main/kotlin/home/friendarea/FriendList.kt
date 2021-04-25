package home.friendarea

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.*
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.svgResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import component.HighlightArea
import setSaturation

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun FriendList(modifier: Modifier = Modifier) {
    Box(modifier) {
        val state = rememberScrollState()
        var displayCloseFriends by remember { mutableStateOf(true) }
        var displayFriends by remember { mutableStateOf(true) }
        Row(Modifier.padding(end = 8.dp)) {
            Column(
                modifier = Modifier.verticalScroll(state).weight(1f),
            ) {
                CategoryItem(
                    "亲密好友 - 1/2",
                    displayCloseFriends
                ) { displayCloseFriends = it }
                Spacer(Modifier.height(8.dp))
                AnimatedVisibility(
                    displayCloseFriends,
                    enter = expandVertically(Alignment.Top),
                    exit = shrinkVertically(Alignment.Top)
                ) {
                    Column {
                        FriendItem(
                            "花丸晴琉",
                            Status.ONLINE,
                            "在线",
                            BitmapPainter(imageResource("images/avatar/hareru.jpg")),
                            true
                        )
                        FriendItem(
                            "鹿乃",
                            Status.OFFLINE,
                            "已离线1天",
                            BitmapPainter(imageResource("images/avatar/kano.jpg")),
                            true
                        )

                    }
                }
                Spacer(Modifier.height(16.dp))

                CategoryItem(
                    "好友 - 0/7",
                    displayFriends
                ) { displayFriends = it }
                Spacer(Modifier.height(8.dp))

                AnimatedVisibility(
                    displayFriends,
                    enter = expandVertically(Alignment.Top),
                    exit = shrinkVertically(Alignment.Top)
                ) {
                    Column {
                        repeat(7) {
                            FriendItem("好友 $it", Status.OFFLINE, "已离线")
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
            }
            Spacer(Modifier.width(8.dp))
            VerticalScrollbar(
                modifier = Modifier.fillMaxHeight(),
                style = defaultScrollbarStyle().copy(
                    shape = RoundedCornerShape(50),
                    hoverColor = Color.White.copy(0.34f),
                    unhoverColor = Color.White.copy(0.12f)
                ),
                adapter = rememberScrollbarAdapter(state)
            )
        }
    }
}

@Composable
private fun CategoryItem(
    text: String,
    expand: Boolean,
    onStateChange: (expand: Boolean) -> Unit
) {
    var hover by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .pointerMoveFilter(
                onEnter = { hover = true; true },
                onExit = { hover = false;true }
            )
            .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null, onClick = {
                onStateChange(!expand)
            })
            .clip(RoundedCornerShape(4.dp))
            .then(
                if (hover) Modifier.background(
                    color = Color.White.copy(0.12f),
                    shape = RoundedCornerShape(4.dp)
                ) else Modifier
            )
            .padding(2.dp).wrapContentHeight().fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val rotation by updateTransition(expand).animateFloat(transitionSpec = {
            tween()
        }, targetValueByState = {
            if (it) 0f else -90f
        })

        Image(
            modifier = Modifier.padding(start = 8.dp).size(12.dp).rotate(rotation),
            painter = svgResource("images/dark-theme/arrow-down.svg"),
            contentDescription = "Expand",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.White.copy(ContentAlpha.medium))
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = text,
            color = Color.White.copy(ContentAlpha.medium),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}


enum class Status {
    ONLINE, OFFLINE, BUSY, AWAY
}

@Composable
private fun FriendItem(
    name: String,
    status: Status,
    description: String,
    avatar: Painter? = null,
    star: Boolean = false
) {
    HighlightArea {
        Row(
            modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(Modifier.size(40.dp).graphicsLayer()) {
                // Avatar
                Image(
                    modifier = Modifier.matchParentSize().clip(CircleShape),
                    painter = avatar ?: BitmapPainter(imageResource("images/avatar/default.png")),
                    contentScale = ContentScale.Fit,
                    contentDescription = "avatar",
                    colorFilter = if (status == Status.OFFLINE) {
                        ColorFilter.colorMatrix(ColorMatrix().apply { setSaturation(0f) })
                    } else null
                )
                // Status
                Box(
                    Modifier.size(16.dp).align(Alignment.BottomEnd).clip(CircleShape)
                        .background(BattlenetColors.background),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier.size(10.dp),
                        painter = svgResource(
                            when (status) {
                                Status.ONLINE -> "images/dark-theme/status-online.svg"
                                Status.OFFLINE -> "images/dark-theme/status-offline.svg"
                                Status.BUSY -> "images/dark-theme/status-busy.svg"
                                Status.AWAY -> "images/dark-theme/status-away.svg"
                            }
                        ),
                        contentDescription = "status",
                        contentScale = ContentScale.Fit
                    )
                }
            }
            Spacer(Modifier.width(8.dp))
            Column {
                // Name
                Row(modifier = Modifier.wrapContentHeight(), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = name,
                        color = if (status == Status.OFFLINE) {
                            Color.White.copy(0.5f)
                        } else BattlenetColors.nameColor,
                        fontSize = 14.sp
                    )
                    if (star) {
                        Spacer(Modifier.width(4.dp))
                        Image(
                            modifier = Modifier.size(12.dp),
                            painter = svgResource("images/dark-theme/star-filled.svg"),
                            contentDescription = "Star",
                            contentScale = ContentScale.Fit,
                            colorFilter = ColorFilter.tint(Color(0xFFFFB400))
                        )
                    }
                }
                // Status
                Text(
                    text = description,
                    color = if (status == Status.OFFLINE) {
                        Color.White.copy(0.5f)
                    } else Color.White.copy(ContentAlpha.medium),
                    fontSize = 12.sp
                )
            }
        }
    }
}