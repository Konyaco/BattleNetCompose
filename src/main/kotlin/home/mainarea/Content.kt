package home.mainarea

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.svgResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import component.BattleNetIndication
import component.collectIsHoveredAsState
import component.hoverable

@Composable
fun Content() {
    Row {
        Column(Modifier.padding(start = 24.dp, top = 8.dp).width(150.dp)) {
            var selected by remember { mutableStateOf(0) }
            CategoryItem(selected == 0, "所有游戏", 9) { selected = 0 }
            Spacer(Modifier.height(4.dp))
            CategoryItem(selected == 1, "偏好", 1) { selected = 1 }
        }
        Box(Modifier.weight(1f).fillMaxHeight()) {
            ContentArea(Modifier.fillMaxSize())
        }
    }
}

private val TestCards = listOf(
    CardData("守望先锋", "团队动作游戏", "images/overwatch/game_card.png"),
    CardData("魔兽世界", "大型多人角色扮演游戏", "images/wow/game_card.png"),
    CardData("炉石传说", "策略类卡牌游戏", "images/hearthstone/game_card.png"),
    CardData("风暴英雄", "多人在线战术竞技游戏", "images/heros/game_card.png"),
)

private data class CardData(
    val name: String,
    val description: String,
    val imagePath: String
)

@Composable
fun ContentArea(
    modifier: Modifier = Modifier
) {
    Column(modifier.verticalScroll(rememberScrollState())) {
        Spacer(Modifier.height(16.dp))
        Row(modifier = Modifier.padding(horizontal = 32.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "排序：",
                color = Color.White.copy(0.7f),
                fontSize = 14.sp
            )
            Spacer(Modifier.width(8.dp))

            val sortInteractionSource = remember { MutableInteractionSource() }
            val sortHovered by sortInteractionSource.collectIsHoveredAsState()
            Row(modifier = Modifier.hoverable(sortInteractionSource), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "推荐",
                    color = Color.White.copy(if (sortHovered) 0.9f else 0.7f),
                    fontSize = 14.sp
                )
                Spacer(Modifier.width(8.dp))
                Icon(
                    modifier = Modifier.size(14.dp),
                    painter = svgResource("images/dark-theme/caret-down.svg"),
                    contentDescription = "Dropdown",
                    tint = Color.White.copy(if (sortHovered) 0.9f else 0.7f)
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        Row(modifier = Modifier.padding(horizontal = 32.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            TestCards.forEach {
                val image = imageResource(it.imagePath)
                GameCard(
                    remember(image) { BitmapPainter(image) },
                    it.name,
                    it.description,
                    Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun CategoryItem(
    selected: Boolean,
    text: String,
    count: Int,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .height(30.dp)
            .clip(RoundedCornerShape(4.dp))
            .hoverable(interactionSource)
            .clickable(interactionSource, remember { BattleNetIndication() }, onClick = onClick)
            .then(if (selected) Modifier.background(Color.White.copy(0.06f)) else Modifier),
        contentAlignment = Alignment.Center
    ) {
        // Indicator
        if (selected) Box(
            Modifier.fillMaxHeight().width(2.dp).background(Color(0xFF148EFF)).align(Alignment.CenterStart)
        )
        Row(modifier = Modifier.padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.weight(1f),
                text = text,
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
            Box(
                Modifier.clip(RoundedCornerShape(50))
                    .background(Color(0xFF1B1C2B))
                    .padding(horizontal = 10.dp)
                    .wrapContentHeight(),
                Alignment.Center
            ) {
                Text(
                    text = count.toString(), color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun GameCard(
    image: Painter,
    name: String,
    description: String,
    modifier: Modifier = Modifier
) {
    val starFilled = svgResource("images/dark-theme/star-filled.svg")
    val starOutlined = svgResource("images/dark-theme/star-outlined.svg")

    Column(modifier) {
        val interactionSource = remember { MutableInteractionSource() }
        val hovered by interactionSource.collectIsHoveredAsState()
        val scale by updateTransition(hovered).animateFloat {
            if (it) 1.06f else 1f
        }
        Card(
            Modifier.aspectRatio(3f / 4f)
                .zIndex(1f)
                .hoverable(interactionSource)
                .clickable(interactionSource, indication = null, onClick = {})
                .graphicsLayer(scaleX = scale, scaleY = scale),
            elevation = 16.dp
        ) {
            Box(Modifier.fillMaxSize()) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = image,
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
                Surface(
                    Modifier.size(28.dp).align(Alignment.TopStart), color = Color(0xFF12131A),
                    shape = RoundedCornerShape(bottomEnd = 4.dp)
                ) {
                    Box(Modifier.fillMaxSize(), Alignment.Center) {
                        Image(
                            modifier = Modifier.size(16.dp),
                            painter = starOutlined,
                            contentScale = ContentScale.Fit,
                            contentDescription = "Star"
                        )
                    }
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        Text(
            text = name,
            color = Color.White.copy(if (hovered) 0.9f else 0.7f),
            fontSize = 14.sp
        )
        Text(
            text = description,
            color = Color.White.copy(0.5f),
            fontSize = 12.sp
        )
    }
}