package hci.mobile.poty.ui.components


import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import hci.mobile.poty.R
import hci.mobile.poty.ui.theme.White

@Composable
fun BalanceCard(balance: Float, isVisible: Boolean, onToggleVisibility: () -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)) {
        Text(text = stringResource(R.string.balance), color = White, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(bottom = 3.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (isVisible) {
                Text(text = "$ ${String.format("%.2f", balance)}", color = White, style = MaterialTheme.typography.titleLarge)
            } else {
                Text(text = "$ ****", color = White, style = MaterialTheme.typography.titleLarge)
            }
            IconButton(onClick = onToggleVisibility, modifier = Modifier.padding(start = 10.dp)) {
                Icon(painter = painterResource(id = if (isVisible) R.drawable.eye else R.drawable.eye_off),
                    contentDescription = if (isVisible) "Ocular Balance" else "Mostrar Balance",
                    tint = White,
                    modifier = Modifier.size(35.dp))
            }
        }
    }
}


@SuppressLint("DefaultLocale")
@Composable
fun PaymentBalanceCard(balance: Float, smallerHeight: Boolean = false) {
    val height = if (smallerHeight) 0.5f else 1f

    Card(
        modifier = Modifier
            .aspectRatio(1.6f)
            .padding(10.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth().fillMaxHeight(height),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.balance),
                    color = White,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 3.dp)
                )
                Text(
                    text = "$ ${String.format("%.2f", balance)}",
                    color = White,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

