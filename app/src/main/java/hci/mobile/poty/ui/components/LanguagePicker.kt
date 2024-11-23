package hci.mobile.poty.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun LanguagePicker(
    currentLanguage: String,
    onLanguageSelected: (String) -> Unit,
    supportedLanguages: List<String>,
    languageLabels: Map<String, String>
) {
    var dropdownExpanded by remember { mutableStateOf(false) }

    IconButton(onClick = { dropdownExpanded = !dropdownExpanded }) {
        Icon(
            imageVector = Icons.Default.Language,
            contentDescription = "Select Language",
            tint = MaterialTheme.colorScheme.onSurface
        )
    }

    DropdownMenu(
        expanded = dropdownExpanded,
        onDismissRequest = { dropdownExpanded = false }
    ) {
        supportedLanguages.forEach { languageCode ->
            DropdownMenuItem(
                text = {
                    Text(
                        text = languageLabels[languageCode] ?: languageCode,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                onClick = {
                    dropdownExpanded = false
                    onLanguageSelected(languageCode)
                }
            )
        }
    }
}
