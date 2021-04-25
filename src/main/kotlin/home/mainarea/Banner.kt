package home.mainarea

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.svgResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import component.BattleNetIndication2
import component.HighlightArea

@Composable
fun Banner() {
    Row(
        Modifier.fillMaxWidth().padding(top = 16.dp, start = 32.dp, end = 32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val overflow = svgResource("images/dark-theme/overflow.svg")
        val notification = svgResource("images/dark-theme/notification.svg")
        val support = svgResource("images/dark-theme/support.svg")

        Row(
            Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Logo
            LogoButton { }
            Spacer(Modifier.width(24.dp))
            var checkedButton by remember { mutableStateOf(0) }
            BattlenetTextToggleButton(
                "游戏",
                checkedButton == 0
            ) { checkedButton = 0 }
            Spacer(Modifier.width(32.dp))
            BattlenetTextToggleButton(
                "商城",
                checkedButton == 1
            ) { checkedButton = 1 }
            Spacer(Modifier.width(24.dp))
            BattlenetIconButton2(onClick = {}) {
                Image(
                    painter = overflow,
                    contentScale = ContentScale.Fit,
                    contentDescription = "Overflow",
                    colorFilter = ColorFilter.tint(Color.White.copy(ContentAlpha.medium))
                )
            }
        }


        Row(verticalAlignment = Alignment.CenterVertically) {
            BattlenetIconButton2(onClick = {}) {
                Image(
                    painter = support,
                    contentScale = ContentScale.Fit,
                    contentDescription = "Feedback",
                    colorFilter = ColorFilter.tint(Color.White.copy(ContentAlpha.medium))
                )
            }
            BattlenetIconButton2(onClick = {}) {
                Image(
                    painter = notification,
                    contentScale = ContentScale.Fit,
                    contentDescription = "Notification",
                    colorFilter = ColorFilter.tint(Color.White.copy(ContentAlpha.medium))
                )
            }
        }
    }
}

@Composable
private fun LogoButton(
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit
) {
    var hover by remember { mutableStateOf(false) }
    val pressed by interactionSource.collectIsPressedAsState()
    val offset by updateTransition(pressed).animateDp { if (it) 2.dp else 0.dp }
    Row(
        Modifier
            .pointerMoveFilter(
                onEnter = { hover = true; true },
                onExit = { hover = false; true }
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .offset(y = offset),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val logo = imageResource("images/battlenet_logo.png")
        Image(
            modifier = Modifier.size(38.dp),
            bitmap = logo,
            contentDescription = "Logo",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.colorMatrix(
                if (hover) ColorMatrix(
                    floatArrayOf(
                        1f, 0f, 0f, 0f, 0.1f,
                        0f, 1f, 0f, 0f, 0.1f,
                        0f, 0f, 1f, 0f, 0.1f,
                        0f, 0f, 0f, 1f, 0.1f
                    )
                )
                else ColorMatrix()
            )
        )
        Spacer(Modifier.width(6.dp))
        Image(
            modifier = Modifier.size(12.dp),
            painter = svgResource("images/dark-theme/caret-down.svg"),
            contentDescription = "expand",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.White.copy(if (hover) ContentAlpha.high else ContentAlpha.medium))
        )
    }
}

@Composable
private fun BattlenetTextToggleButton(
    text: String,
    checked: Boolean,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onCheckedChane: (Boolean) -> Unit
) {
    var hover by remember { mutableStateOf(false) }
    val pressed by interactionSource.collectIsPressedAsState()
    val offset by updateTransition(pressed).animateDp { if (it) 2.dp else 0.dp }
    Box(
        Modifier
            .pointerMoveFilter(
                onEnter = { hover = true; true },
                onExit = { hover = false; true }
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { onCheckedChane(!checked) }
            )
            .offset(y = offset)
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = if (hover || checked) Color.White.copy(ContentAlpha.high) else Color.White.copy(ContentAlpha.medium)
        )
    }
}

@Composable
private fun BattlenetIconButton2(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    HighlightArea {
        Box(
            modifier.then(Modifier.size(40.dp))
                .clip(RoundedCornerShape(4.dp))
                .clickable(
                    onClick = onClick,
                    enabled = enabled,
                    role = Role.Button,
                    interactionSource = interactionSource,
                    indication = remember { BattleNetIndication2() }
                ),
            contentAlignment = Alignment.Center
        ) {
            val contentAlpha = if (enabled) ContentAlpha.medium else ContentAlpha.disabled
            CompositionLocalProvider(LocalContentAlpha provides contentAlpha) {
                Box(Modifier.size(24.dp)) { content() }
            }
        }
    }
}
