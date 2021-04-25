package component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
fun BattlenetIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
//    HighlightArea {
        Box(
            modifier.then(Modifier.size(40.dp))
                .clip(RoundedCornerShape(4.dp))
                .background(Color.White.copy(0.12f))
                .hoverable(interactionSource)
                .clickable(
                    onClick = onClick,
                    enabled = enabled,
                    role = Role.Button,
                    interactionSource = interactionSource,
                    indication = remember { BattleNetIndication() }
                ),
            contentAlignment = Alignment.Center
        ) {
            val contentAlpha = if (enabled) ContentAlpha.medium else ContentAlpha.disabled
            CompositionLocalProvider(LocalContentAlpha provides contentAlpha) {
                Box(Modifier.size(22.dp)) { content() }
            }
        }
//    }
}
