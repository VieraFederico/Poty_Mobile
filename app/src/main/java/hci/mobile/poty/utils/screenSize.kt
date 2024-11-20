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

// Function to calculate window size class based on device dimensions
@Composable
fun calculateWindowSizeClass(
    mockWidthDp: Float? = null,
    mockHeightDp: Float? = null
): WindowSizeClass {
    val context = LocalContext.current
    val density = context.resources.displayMetrics.density

    var widthDp: Float
    var heightDp: Float

//    if (mockWidthDp != null && mockHeightDp != null) {
//        widthDp = mockWidthDp
//        heightDp = mockHeightDp
//    } else {
//        val activity = context as? Activity
//        if (activity != null) {
//            val windowMetrics = WindowMetricsCalculator.getOrCreate()
//                .computeCurrentWindowMetrics(activity)
//            widthDp = windowMetrics.bounds.width() / density
//            heightDp = windowMetrics.bounds.height() / density
//        } else {
//            widthDp = 360f
//            heightDp = 640f
//        }
//    }

    widthDp = mockWidthDp ?: 360f // Default width for fallback
    heightDp = mockHeightDp ?: 640f // Default height for fallback

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
        // Medium Phone Portrait (approximate dp values)
        !isLandscape && widthDp in 400f..450f && heightDp in 850f..950f ->
            WindowSizeClass.MediumPhone

        // Medium Phone Landscape
        isLandscape && widthDp in 850f..950f && heightDp in 400f..450f ->
            WindowSizeClass.MediumPhoneLandscape

        // Medium Tablet Portrait
        !isLandscape && widthDp in 750f..850f && heightDp in 1200f..1350f ->
            WindowSizeClass.MediumTablet

        // Medium Tablet Landscape
        isLandscape && widthDp in 1200f..1350f && heightDp in 750f..850f ->
            WindowSizeClass.MediumTabletLandscape

        else -> {
            // Default to MediumPhone if size doesn't match
            if (isLandscape) WindowSizeClass.MediumPhoneLandscape
            else WindowSizeClass.MediumPhone
        }
    }
}