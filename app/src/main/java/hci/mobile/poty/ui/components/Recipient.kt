package hci.mobile.poty.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.test.services.storage.file.PropertyFile
import hci.mobile.poty.R
import hci.mobile.poty.ui.theme.PotyTheme
import hci.mobile.poty.ui.theme.White
import hci.mobile.poty.ui.theme.titleSmallSemiBold

@Composable
fun RecipientCard(name: String, email: String){
    Card(
        modifier = Modifier
            .aspectRatio(2.5f)
            .padding(10.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
    ){
        Column(
            modifier = Modifier
                .padding(20.dp)
        ){
            Image(
                painter = painterResource(id = R.drawable.user),
                contentDescription = null,
                modifier = Modifier.size(25.dp),

            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = name,
                style = MaterialTheme.typography.titleSmallSemiBold,
                color = White
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = email,
                style = MaterialTheme.typography.bodyLarge,
                color = White
            )
        }
    }
}

@Preview
@Composable
fun RecipientCardPreview(){
    PotyTheme {
        RecipientCard("James Bond", "vesper007@gmail.com")
    }
}