package home.mainarea

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import home.mainarea.Banner
import home.mainarea.Content
import home.mainarea.Operation

@Composable
fun MainArea() {
    Column(Modifier.fillMaxSize()) {
        Banner()
        Spacer(Modifier.height(16.dp))
        Operation()
        Spacer(Modifier.height(32.dp))
        Box(Modifier.weight(1f)) {
            Content()
        }
    }
}


