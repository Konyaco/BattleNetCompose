package component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BattlenetTextField(
    value: String,
    onValueChange: (String) -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var focusState by remember { mutableStateOf(FocusState.Inactive) }
    val focusInteractionSource = remember { MutableInteractionSource() }
    CompositionLocalProvider(LocalContentColor provides MaterialTheme.colors.onSurface) {
        HighlightArea {
            Row(
                modifier = modifier.defaultMinSize(120.dp, 40.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.White.copy(0.12f))
                    .focusable(true, focusInteractionSource)
                    .onFocusChanged {
                        focusInteractionSource.tryEmit(FocusInteraction.Focus())
                        focusState = it
                    }
                    .then(
                        if (focusState < FocusState.Inactive) {
                            Modifier.border(1.dp, color = Color.White.copy(0.12f), RoundedCornerShape(4.dp))
                        } else Modifier
                    )
            ) {
                Box(
                    Modifier.align(Alignment.CenterVertically).weight(1f).padding(8.dp),
                    Alignment.CenterStart
                ) {
                    BasicTextField(
                        modifier = Modifier.focusable(true, focusInteractionSource),
                        value = value,
                        onValueChange = onValueChange,
                        singleLine = true,
                        maxLines = 1,
                        textStyle = TextStyle(color = Color.White, fontSize = 14.sp),
                        cursorBrush = SolidColor(Color.White)
                    )
                }
                Box(Modifier.wrapContentSize().padding(end = 4.dp).align(Alignment.CenterVertically)) {
                    trailingIcon?.invoke()
                }
            }
        }
    }
}