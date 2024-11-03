package hci.mobile.poty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import hci.mobile.poty.ui.theme.PotyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PotyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "James Bond",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
//Aca vamos a escribir una funcion, en vez de componentes. Necesitan los @Composable
//Declaramos funciones usando fun y sus argumentso
// El = del Greeting con modifier es el valor default
//Los big 3 de Koitling; named parameters, defaiult parameters y tralinglambdas
//By es delegado
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hola $name! Bienvenido al MI6.", //Parametros con nombnre
        modifier = modifier
    )
}

@Preview(showBackground = true) //Para qe sea preview
@Composable
fun GreetingPreview() {
    PotyTheme {
        Greeting("James Bond")
    }
}