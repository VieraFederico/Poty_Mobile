package hci.mobile.poty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
//Cosas viejas: Setcontent view, R-layout, XML no se pueden usar.
//Las funciones deben ser inedpendientes
//Las funciones no deben tener efecttos secundarios
//

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview(){
    WelcomeScreen()
}
@Composable
fun WelcomeScreen(){
    Column(
        modifier = Modifier.fillMaxSize()
    ){

    }
}

@Preview(showBackground = true)
@Composable
fun HelloUserPreview(){
    PotyTheme {
        HelloUser()
    }
}
@Composable
fun HelloUser(){
    Column(modifier = Modifier.padding(16.dp)){
        var name by remember { mutableStateOf("") }
        if(name.isNotEmpty()){
            Text(
                text ="Hello, $name!",
                modifier = Modifier.padding(bottom=6.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        OutlinedTextField(
            value = name,
            onValueChange = { name = it},
            label = {Text("Name")}
        )
    }
}

@Composable
fun CounterScreen(){
    var counter by rememberSaveable { mutableStateOf(1) }
    CounterDisplay(counter, {v -> counter = v})

}

@Preview(showBackground = true)
@Composable
fun CounterScreenPreview(){
    PotyTheme {
        CounterScreen()
    }
}
@Composable
fun CounterDisplay(
    counter: Int,
    onCounterUpdated: (v: Int) -> Unit) {
    Column{
        Text(text = "Counter value: $counter")
        CounterIncrement(
            counter,
            onCounterUpdated)
    }
}

@Composable
fun CounterIncrement(
    counter: Int,
    onCounterUpdated: (v: Int) -> Unit) {
    ElevatedButton(
        onClick = { onCounterUpdated(counter + 1) },
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Text("Increment")
    }
}

@Preview(showBackground = true)
@Composable
fun DataCardPReview(){
    PotyTheme{DataCard()}
}

@Composable
fun DataCard(){
    var expanded by remember {mutableStateOf(false)}
    val extraPadding = if (expanded) 48.dp else 0.dp
    Surface(
        color = MaterialTheme.colorScheme.tertiary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ){
        Row (modifier = Modifier.padding(24.dp)){
            Column(modifier = Modifier
                .weight(1f)
                .align(Alignment.Bottom)
                .padding(all = extraPadding)) {
                Text(text="Check, ")
                Text(text="Checking")

            }
            
            ElevatedButton(
                onClick = {expanded = !expanded}
            ){
                Text(if (expanded) "Don't Click Me!" else "Click Me!")
            }
        }
    }
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hola $name! Bienvenido al MI6.", //Parametros con nombnre
        style = MaterialTheme.typography.bodyLarge,

        )
}

@Preview(showBackground = true) //Para qe sea preview
@Composable
fun GreetingPreview() {
    PotyTheme {
        Greeting("James Bond")
    }
}

