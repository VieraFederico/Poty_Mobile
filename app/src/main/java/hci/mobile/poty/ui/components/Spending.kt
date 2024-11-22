package hci.mobile.poty.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hci.mobile.poty.R
import hci.mobile.poty.ui.theme.GreenDark
import hci.mobile.poty.ui.theme.White

@Preview
@Composable
fun spendingCardPreview(){
    spendingCard(10F)
}

@Composable
fun spendingCard(spent: Float){
    Column(modifier = Modifier
        .fillMaxWidth()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp)),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = GreenDark,
            ),
        ) {
            Text(
                text = stringResource(R.string.spent),
                color = White,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 10.dp,start = 10.dp,bottom = 5.dp)
            )
            Text(
                text = "$ $spent",
                color = White,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(10.dp)
            )
        }
    }
}