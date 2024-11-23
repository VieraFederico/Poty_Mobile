package hci.mobile.poty.screens.investment


import android.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import hci.mobile.poty.ui.components.BalanceCard
import hci.mobile.poty.ui.components.BottomNavBar
import hci.mobile.poty.ui.components.InvestedCard
import hci.mobile.poty.ui.theme.PotyTheme
import hci.mobile.poty.utils.NumberFieldWithLabel

@Composable
fun InvestmentScreenWithChart(viewModel: InvestmentScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val state by viewModel.state.collectAsState()

    PotyTheme {
        Scaffold(
            bottomBar = { BottomNavBar() },
            containerColor = MaterialTheme.colorScheme.secondary
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                // Encabezado y balance
                BalanceCard(
                    balance = state.balance,
                    isVisible = true,
                    onToggleVisibility = { /* Lógica para alternar visibilidad */ }
                )

                InvestedCard(
                    invested = state.invested,
                    isVisible = true,
                    onToggleVisibility = { /* Lógica para alternar visibilidad */ }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Mostrar mensaje de error (si existe)
                state.errorMessage?.let { errorMessage ->
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                // Inputs para invertir y rescatar
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    NumberFieldWithLabel(
                        label = "Monto a Invertir",
                        value = state.investAmount,
                        onValueChange = { viewModel.updateInvestAmount(it) }
                    )
                    Button(
                        onClick = { viewModel.invest(state.investAmount) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Invertir")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    NumberFieldWithLabel(
                        label = "Monto a Rescatar",
                        value = state.withdrawAmount,
                        onValueChange = { viewModel.updateWithdrawAmount(it) }
                    )
                    Button(
                        onClick = { viewModel.withdraw(state.withdrawAmount) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Retirar")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Gráfico de ganancias proyectadas
                LineChartComponent(
                    entries = List(6) { index ->
                        Entry(index.toFloat(), (state.invested * 0.05 * (index + 1)).toFloat())
                    }
                )
            }
        }
    }
}



@Composable
fun LineChartComponent(entries: List<Entry>) {
    AndroidView(
        factory = { context ->
            LineChart(context).apply {
                val dataSet = LineDataSet(entries, "Proyección de Ganancias").apply {
                    colors = listOf(ColorTemplate.COLORFUL_COLORS[0])
                    valueTextColor = Color.WHITE
                    valueTextSize = 12f
                }

                val lineData = LineData(dataSet)
                data = lineData

                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    textColor = Color.WHITE
                }

                axisLeft.textColor = Color.WHITE
                axisRight.isEnabled = false

                legend.textColor = Color.WHITE
                description.isEnabled = false
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewInvestmentScreenWithChart() {
    PotyTheme {
        InvestmentScreenWithChart()
    }
}

