package component

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.input.pointer.pointerMoveFilter
import kotlinx.coroutines.flow.collect

@Composable
fun Modifier.hoverable(
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onEnter: () -> Boolean = { false },
    onExit: () -> Boolean = { false },
    onMove: () -> Boolean = { false }
) = composed {
    var interaction: HoverInteraction.Enter = HoverInteraction.Enter()
    Modifier.pointerMoveFilter(
        onEnter = {
            interaction = HoverInteraction.Enter()
            interactionSource.tryEmit(interaction)
            onEnter()
        },
        onMove = {
            onMove()
        },
        onExit = {
            interactionSource.tryEmit(HoverInteraction.Exit(interaction))
            onExit()
        }
    )
}

@Composable
fun InteractionSource.collectIsHoveredAsState(): State<Boolean> {
    val isHovered = remember { mutableStateOf(false) }
    LaunchedEffect(this) {
        var entered = false
        interactions.collect { interaction ->
            when (interaction) {
                is HoverInteraction.Enter -> entered = true
                is HoverInteraction.Exit -> entered = false
            }
            isHovered.value = entered
        }
    }
    return isHovered
}


interface HoverInteraction : Interaction {
    class Enter : HoverInteraction
    class Exit(val enter: Enter) : HoverInteraction
}

class BattleNetIndication : Indication {
    private class BattleNetIndicationInstance(
        private val isPressed: State<Boolean>,
        private val isHovered: State<Boolean>
    ) : IndicationInstance {
        override fun ContentDrawScope.drawIndication() {
            when {
                isHovered.value && !isPressed.value -> {
                    drawContent()
                    drawRect(Color.White.copy(0.12f), topLeft = Offset.Zero, size = size)
                }
                isPressed.value && isHovered.value -> {
                    drawContent()
                    drawRect(Color.White.copy(0.06f), topLeft = Offset.Zero, size = size)
                }
                else -> {
                    drawContent()
                }
            }
        }
    }

    @Composable
    override fun rememberUpdatedInstance(interactionSource: InteractionSource): IndicationInstance {
        val isPressed = interactionSource.collectIsPressedAsState()
        val isHovered = interactionSource.collectIsHoveredAsState()

        return remember(isPressed, isHovered) {
            BattleNetIndicationInstance(isPressed, isHovered)
        }
    }
}

class BattleNetIndication2 : Indication {
    private class BattleNetIndicationInstance(private val scale: State<Float>) : IndicationInstance {
        override fun ContentDrawScope.drawIndication() {
            if (scale.value != 1f) {
                val scope = this
                scale(scale.value) {
                    scope.drawContent()
                }
            } else {
                drawContent()
            }
        }
    }

    @Composable
    override fun rememberUpdatedInstance(interactionSource: InteractionSource): IndicationInstance {
        val pressed by interactionSource.collectIsPressedAsState()
        val scale = updateTransition(pressed).animateFloat { if (it) 0.90f else 1f }
        return remember(scale) {
            BattleNetIndicationInstance(scale)
        }
    }
}

