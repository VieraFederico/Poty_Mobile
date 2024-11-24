package hci.mobile.poty.screens.alias
/*
import hci.mobile.poty.ui.theme.PotyTheme
import hci.mobile.poty.utils.TextFieldWithLabel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import hci.mobile.poty.R
import hci.mobile.poty.utils.ErrorMessage
import hci.mobile.poty.ui.components.BackButton
import hci.mobile.poty.ui.theme.White

@Composable
fun ChangeAliasScreen(
    onNavigateBack: () -> Unit = {},
    viewModel: ChangeAliasViewModel = viewModel()
) {
    val state by viewModel.state.observeAsState(ChangeAliasState())

    PotyTheme(darkTheme = true, dynamicColor = false) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.secondary,
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                ChangeAliasHeaderSection(
                    onNavigateBack = onNavigateBack
                )

                ChangeAliasContentSection(
                    alias = state.alias,
                    errorMessage = state.errorMessage,
                    onAliasChange = { newAlias -> viewModel.updateAlias(newAlias) },
                    onChangeAlias = { viewModel.changeAlias() }
                )
            }
        }
    }
}

@Composable
fun ChangeAliasHeaderSection(
    onNavigateBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        BackButton(
            modifier = Modifier.padding(16.dp),
            onClick = onNavigateBack
        )
        Text(
            text = stringResource(R.string.change_alias),
            style = MaterialTheme.typography.titleLarge,
            color = White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun ChangeAliasContentSection(
    alias: String,
    errorMessage: String,
    onAliasChange: (String) -> Unit,
    onChangeAlias: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextFieldWithLabel(
                label = stringResource(R.string.new_alias),
                value = alias,
                onValueChange = onAliasChange
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onChangeAlias,
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text(text = stringResource(R.string.change_alias_button))
            }

            if (errorMessage.isNotEmpty()) {
                ErrorMessage(message = errorMessage)
            }
        }
    }
}*/
