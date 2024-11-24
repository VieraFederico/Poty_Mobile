package hci.mobile.poty.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = GreenDark,
    secondary = GreenMedium,
    tertiary = GreyDark,
    background=GreenMedium,
    onBackground = White
    )

private val LightColorScheme = lightColorScheme(
    primary = GreenLight,
    secondary = GreenMedium,
    tertiary = GreenDark,
    background=GreenMedium,
    onBackground = White
)

@Composable
fun PotyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography
    ) {
        // Asegúrate de envolver el contenido en un Surface
        androidx.compose.material3.Surface(
            color = MaterialTheme.colorScheme.background // Fondo del tema
        ) {
            content()
        }
    }
}