package hci.mobile.poty


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import hci.mobile.poty.navigation.AppNavGraph
import hci.mobile.poty.ui.theme.PotyTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PotyApp()
        }
    }

}

@Composable
fun PotyApp() {
    PotyTheme {
        val navController = rememberNavController()
        AppNavGraph(navController = navController)
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
