package hci.mobile.poty


import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import hci.mobile.poty.navigation.AppNavGraph
import hci.mobile.poty.ui.theme.PotyTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAppLanguage(this)
        setContent {
            PotyApp()
        }
    }
}

private fun setAppLanguage(context: Context) {
    val deviceLanguage = context.resources.configuration.locales.get(0).language
    val languageToSet = when (deviceLanguage) {
        "es" -> "es"
        "fr" -> "fr"
        "de" -> "de"
        "it" -> "it"
        else -> "en"
    }
    val locale = Locale(languageToSet)
    Locale.setDefault(locale)
    val configuration = Configuration(context.resources.configuration)
    configuration.setLocale(locale)
    context.createConfigurationContext(configuration)
    context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
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
