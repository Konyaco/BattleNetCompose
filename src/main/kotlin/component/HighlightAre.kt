package component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.unit.dp

@Composable
fun HighlightArea(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    var hover by remember { mutableStateOf(false) }
    Box(modifier.pointerMoveFilter(onEnter = {
        hover = true
        true
    }, onExit = {
        hover = false
        true
    })) {
        content()
        if (hover) {
            Box(
                Modifier.matchParentSize().background(color = Color.White.copy(0.06f), shape = RoundedCornerShape(4.dp))
            )
        }
    }

}
