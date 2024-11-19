package hci.mobile.poty.screens.landing

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hci.mobile.poty.R
import hci.mobile.poty.ui.theme.PotyTheme
import hci.mobile.poty.ui.theme.titleLargeItalic

@Preview
@Composable
fun LandingScreenPreview(){
    LandingScreen(
        onNavigateToLogin = {},
        onNavigateToRegister = {}
    )
}

@Composable
fun LandingScreen(
    modifier: Modifier = Modifier,
    onNavigateToLogin: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    PotyTheme(darkTheme = true, dynamicColor = false) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.background,

        ) { innerPadding ->
            Column (
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween

            ) {
                Image(
                    painter = painterResource(id = R.drawable.landing),
                    contentDescription = "Foto Landing",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .padding( end = 50.dp)
                )
                Spacer(modifier = Modifier.height(150.dp))
                Column (modifier = Modifier.padding(start= 20.dp)){
                    Row() {
                        Text(
                            text = "Con Poty",
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Image(
                            painter = painterResource(id = R.drawable.poty),
                            contentDescription = "Poty Logo"
                        )

                    }
                    Text(
                        text = "Ahora podés confiar,",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleLarge

                    )
                    Text(
                        text = "tu dinero está en",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Row() {
                        Text(
                            text = "la ",
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = "Urna",
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.titleLargeItalic
                        )
                    }
                }
                Column (modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 30.dp)){
                    Button(
                        onClick = { onNavigateToLogin() },
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = Color.White, // Fondo blanco
                        ),
                        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 4.dp),
                        shape = MaterialTheme.shapes.small.copy(all = CornerSize(10.dp)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Iniciar Sesión",
                            color = MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = { onNavigateToRegister() },
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                        ),
                        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 4.dp),
                        shape = MaterialTheme.shapes.small.copy(all = CornerSize(10.dp)),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = "Registrarse",
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }
    }

}