import androidx.compose.ui.graphics.ColorMatrix

fun ColorMatrix.setSaturation(sat: Float) {
    reset()
    val m = this.values
    val invSat = 1 - sat
    val R = 0.213f * invSat
    val G = 0.715f * invSat
    val B = 0.072f * invSat
    m[0] = R + sat; m[1] = G; m[2] = B
    m[5] = R; m[6] = G + sat; m[7] = B
    m[10] = R; m[11] = G; m[12] = B + sat
}