package hci.mobile.poty.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import hci.mobile.poty.R
import hci.mobile.poty.utils.WindowSizeClass
import hci.mobile.poty.utils.isLandscape
import hci.mobile.poty.utils.isTablet


import androidx.compose.ui.unit.dp


@Composable
fun LoginRegisterImageSection(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass,
    title: String? = null,
) {
    val isLandscape = windowSizeClass.isLandscape()
    val shape = if (isLandscape) {
        RoundedCornerShape(topStart = 0.dp, topEnd = 16.dp, bottomStart = 0.dp, bottomEnd = 16.dp)
    } else {
        RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 16.dp, bottomEnd = 16.dp)
    }

    val textStyle = if (isLandscape) {
        MaterialTheme.typography.headlineMedium.copy(
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            shadow = Shadow(
                color = Color.White,
                offset = Offset(2f, 2f),
                blurRadius = 4f
            )
        )
    } else {
        MaterialTheme.typography.headlineLarge.copy(
            color = Color.White,
            fontWeight = FontWeight.Bold,
            shadow = Shadow(
                color = Color.Black,
                offset = Offset(2f, 2f),
                blurRadius = 4f
            )
        )
    }

    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primary)
            .clip(shape),
        contentAlignment = Alignment.Center
    ) {
        if (!isLandscape) {
            val height = if (windowSizeClass.isTablet()) 1f else 0.4f

            Image(
                painter = painterResource(id = R.drawable.login2),
                contentDescription = "Login Image",
                modifier = Modifier
                    .fillMaxWidth().fillMaxHeight(height),

            )
        } else {
            val height = if (windowSizeClass.isTablet()) 0.8f else 1f
            Image(
                painter = painterResource(id = R.drawable.login2),
                contentDescription = "Login Image",
                modifier = Modifier
                    .wrapContentSize(Alignment.Center)
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight(height),
                contentScale = ContentScale.Crop
            )
        }

        if (title != null) {
            Text(
                text = title,
                style = textStyle,
                modifier = Modifier.align(Alignment.TopEnd).padding(16.dp)

            )
        }
    }
}
