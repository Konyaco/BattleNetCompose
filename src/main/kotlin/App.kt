import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import home.friendarea.FriendArea
import home.mainarea.MainArea

@Composable
fun App() {
    MaterialTheme(colors = darkColors()) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = BattlenetColors.background
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                bitmap = imageResource("images/background.png"),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            Row(Modifier.padding(top = 16.dp)) {
                Box(Modifier.weight(1f).fillMaxHeight()) {
                    MainArea()
                }
                Box(Modifier.width(320.dp)) {
                    FriendArea()
                }
            }
        }
    }
}