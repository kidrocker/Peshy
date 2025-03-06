package ke.kiura.peshy.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import ke.kiura.peshy.R

@Composable
fun PeshyLoader() {
    var currentRotation by remember { mutableFloatStateOf(0f) }
    val rotation = remember { androidx.compose.animation.core.Animatable(currentRotation) }

    LaunchedEffect(true) {
        rotation.animateTo(
            targetValue = currentRotation + 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(3000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        ) {
            currentRotation = value
        }
    }

    Icon(
        modifier = Modifier.rotate(currentRotation),
        painter = painterResource(id = R.drawable.ic_loading_icon),
        contentDescription = ""
    )
}
