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
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.svgResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import component.BattleNetIndication
import component.HighlightArea
import component.hoverable

@Composable
fun Operation() {
    Row(Modifier.fillMaxWidth().padding(horizontal = 24.dp), verticalAlignment = Alignment.CenterVertically) {
        val library = svgResource("images/dark-theme/library.svg")
        val shop = svgResource("images/dark-theme/shop.svg")
        /*val new = svgResource("images/dark-theme/new.svg")
        val settings = svgResource("images/dark-theme/settings.svg")
        val shop = svgResource("images/dark-theme/shop.svg")
        val close = svgResource("images/dark-theme/close.svg")
        val externalLinkArrow = svgResource("images/dark-theme/external-link-arrow.svg")*/

        val interactionSource = remember { MutableInteractionSource() }
        val pressed by interactionSource.collectIsPressedAsState()
        val offset by updateTransition(pressed).animateDp {
            if (it) 2.dp else 0.dp
        }

        HighlightArea(Modifier.offset(y = offset)) {
            Surface(
                Modifier.height(64.dp),
                shape = RoundedCornerShape(4.dp),
                color = Color.White.copy(0.06f)
            ) {
                Row(
                    Modifier.clickable(interactionSource, null, onClick = {}).padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = library,
                        contentDescription = null
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "所有游戏",
                        color = Color.White.copy(ContentAlpha.medium),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Spacer(Modifier.width(16.dp))

        Surface(Modifier.height(64.dp).weight(1f), shape = RoundedCornerShape(4.dp), color = Color.White.copy(0.06f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                GameIconButton(onClick = {}) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        bitmap = imageResource("images/overwatch/favoritebar_logo.png"),
                        contentDescription = "Overwatch",
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    }
}

@Composable
private fun GameIconButton(
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit,
    icon: @Composable () -> Unit
) {
    Box(
        Modifier.size(64.dp)
            .hoverable(interactionSource)
            .clickable(interactionSource, remember { BattleNetIndication() }, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Box(Modifier.size(34.dp)) {
            icon()
        }
    }
}