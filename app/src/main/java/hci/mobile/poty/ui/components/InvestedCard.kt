package hci.mobile.poty.ui.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hci.mobile.poty.ui.theme.White
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.res.painterResource
import hci.mobile.poty.R


@Composable
fun InvestedCard(invested: Float, isVisible: Boolean, onToggleVisibility: () -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)) {
        Text(
            text = "Capital Invertido",
            color = White,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 3.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (isVisible) {
                Text(
                    text = "$ ${String.format("%.2f", invested)}",
                    color = White,
                    style = MaterialTheme.typography.titleLarge
                )
            } else {
                Text(
                    text = "$ ****",
                    color = White,
                    style = MaterialTheme.typography.titleLarge
                )
            }
//            IconButton(onClick = onToggleVisibility, modifier = Modifier.padding(start = 10.dp)) {
//                Icon(
//                    painter = painterResource(id = if (isVisible) R.drawable.eye else R.drawable.eye_off),
//                    contentDescription = if (isVisible) "Ocultar Capital Invertido" else "Mostrar Capital Invertido",
//                    tint = White,
//                    modifier = Modifier.size(35.dp)
//                )
//            }
        }
    }
}
