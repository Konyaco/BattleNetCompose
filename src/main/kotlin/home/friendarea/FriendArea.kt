package home.friendarea

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import component.BattleNetIndication

@Composable
fun FriendArea() {
    Column(Modifier.fillMaxSize().padding(top = 16.dp, bottom = 24.dp)) {
        Banner()
        Spacer(Modifier.height(18.dp))
        Oprations()
        Spacer(Modifier.height(18.dp))
        FriendList(Modifier.weight(1f))
        ChatButton()
    }
}




@Composable
private fun ChatButton() {
    var hover by remember { mutableStateOf(false) }
    Box(
        Modifier.padding(start = 8.dp, end = 32.dp)
            .fillMaxWidth().height(40.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(Color.White.copy(0.12f))
            .pointerMoveFilter(onEnter = {
                hover = true
                true
            }, onExit = {
                hover = false
                true
            })
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = remember { BattleNetIndication() },
                enabled = true,
                onClick = {}
            )
            .then(
                if (hover) Modifier.border(
                    2.dp,
                    color = Color.White.copy(0.12f),
                    shape = RoundedCornerShape(4.dp)
                ) else Modifier
            ),
        Alignment.Center
    ) {
        Text(
            text = "聊天与群组",
            color = Color.White.copy(ContentAlpha.high),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}