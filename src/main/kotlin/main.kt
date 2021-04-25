import androidx.compose.desktop.Window
import androidx.compose.ui.unit.IntSize
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

private fun loadImageResource(path: String): BufferedImage {
    val resource = Thread.currentThread().contextClassLoader.getResource(path)
    requireNotNull(resource) { "Resource $path not found" }
    return resource.openStream().use(ImageIO::read)
}

private val appIcon = loadImageResource("icon.png")

fun main() {
    Window("Battle Net", icon = appIcon, size = IntSize(1150, 770)) {
        App()
    }
}




