package hci.mobile.poty.utils

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.window.layout.WindowMetricsCalculator





// Enum for window size classes with specified names
enum class WindowSizeClass {
    MediumPhone,
    MediumPhoneLandscape,
    MediumTablet,
    MediumTabletLandscape
}

@Composable
fun calculateWindowSizeClass(
    mockWidthDp: Float? = null,
    mockHeightDp: Float? = null
): WindowSizeClass {
    val context = LocalContext.current
    val density = context.resources.displayMetrics.density

    var widthDp: Float
    var heightDp: Float


    widthDp = mockWidthDp ?: 360f // Default / Fallback
    heightDp = mockHeightDp ?: 640f // Fallback

    if (mockWidthDp == null || mockHeightDp == null) {
        (context as? Activity)?.let { activity ->
            val windowMetrics = WindowMetricsCalculator.getOrCreate()
                .computeCurrentWindowMetrics(activity)
            widthDp = windowMetrics.bounds.width() / density
            heightDp = windowMetrics.bounds.height() / density
        }
    }


    val isLandscape = widthDp > heightDp

    return when {
        !isLandscape && widthDp in 400f..450f && heightDp in 850f..950f ->
            WindowSizeClass.MediumPhone

        isLandscape && widthDp in 850f..950f && heightDp in 400f..450f ->
            WindowSizeClass.MediumPhoneLandscape

        !isLandscape && widthDp in 750f..850f && heightDp in 1200f..1350f ->
            WindowSizeClass.MediumTablet

        isLandscape && widthDp in 1200f..1350f && heightDp in 750f..850f ->
            WindowSizeClass.MediumTabletLandscape

        else -> {
            if (isLandscape) WindowSizeClass.MediumPhoneLandscape
            else WindowSizeClass.MediumPhone
        }
    }
}

fun WindowSizeClass.isLandscape(): Boolean {
    return this == WindowSizeClass.MediumPhoneLandscape || this == WindowSizeClass.MediumTabletLandscape
}

fun WindowSizeClass.isTablet(): Boolean {
    return this == WindowSizeClass.MediumTablet || this == WindowSizeClass.MediumTabletLandscape
}