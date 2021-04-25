import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.svgResource

val Icons.BattleNet: BattleNetIcons
    get() = BattleNetIcons

object BattleNetIcons {
    val Account: Painter
        @Composable get() = svgResource("images/dark-theme/account.svg")
}