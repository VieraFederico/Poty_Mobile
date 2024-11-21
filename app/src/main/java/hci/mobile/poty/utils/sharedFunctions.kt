package hci.mobile.poty.utils
import androidx.compose.material3.LocalTextStyle
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import hci.mobile.poty.R
import hci.mobile.poty.ui.theme.Black
import hci.mobile.poty.ui.theme.GreenDark
import hci.mobile.poty.ui.theme.White

@Composable
fun ErrorMessage(message: String) {
    if (message.isNotEmpty()) {
        Text(
            text = message,
            color = Color.Red,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(10.dp)
        )
    }
}



@Composable
fun TextFieldWithLabel(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    maxLength: Int? = null,
    regex: Regex? = null,
) {
    Text(
        text = label,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier.padding(bottom = 8.dp)
    )
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            if ((regex == null || regex.matches(newValue)) && (maxLength == null || newValue.length <= maxLength)) {
                onValueChange(newValue)
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(50.dp))
            .height(50.dp)
            .border(
                BorderStroke(1.dp, Black),
                RoundedCornerShape(50.dp)
            ),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Black,
            unfocusedTextColor = Black,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            cursorColor = Black
        ),
        textStyle = LocalTextStyle.current.copy(color = Black),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
    )
}



fun isValidDate(input: String): Boolean {
    val regex = Regex("^\\d{0,2}(/\\d{0,2}(/\\d{0,4})?)?\$")
    return regex.matches(input)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompactDateFieldWithLabel(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: value

    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                if (isValidDate(newValue)) {
                    onValueChange(newValue)
                }
            },
            readOnly = false,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Select date"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(50.dp))
                .height(50.dp)
                .background(Color.Transparent)
                .border(
                    BorderStroke(1.dp, Black),
                    RoundedCornerShape(50.dp)
                ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Black,
                unfocusedTextColor = Black,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = Black
            ),
            singleLine = true
        )

        if (showDatePicker) {
            Dialog(onDismissRequest = { showDatePicker = false }) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CompositionLocalProvider(
                            LocalTextStyle provides TextStyle(color = Black)
                        ) {
                            DatePicker(
                                state = datePickerState,
                                showModeToggle = false,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(400.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(
                                onClick = { showDatePicker = false }
                            ) {
                                Text("Cancelar", color = GreenDark)
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Button(
                                onClick = {
                                    datePickerState.selectedDateMillis?.let {
                                        onValueChange(convertMillisToDate(it))

                                        showDatePicker = false
                                    }
                                }
                            ) {
                                Text("Confirmar", color = White)
                            }
                        }
                    }
                }
            }
        }
    }
}


private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

@Composable
fun PasswordFieldWithLabel(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = true
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = modifier.padding(start = 15.dp, bottom = 8.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(50.dp))
                .height(56.dp)
                .border(
                    BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                    RoundedCornerShape(50.dp)
                ),
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(color = Black),
            visualTransformation = if (isPassword && !isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            trailingIcon = {
                // Custom Icon using painterResource
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        painter = painterResource(id = if (isPasswordVisible) R.drawable.eye else R.drawable.eye_off),
                        contentDescription = if (isPasswordVisible) "Ocular Contraseña" else "Mostrar Contraseña",
                        tint = Black,
                        modifier = modifier.size(35.dp)
                    )
                }
            }
        )
    }
}

@Composable
fun NumberFieldWithLabel(
    label: String,
    value: Float = 0.0f,
    onValueChange: (Float) -> Unit,
    isPassword: Boolean = false
) {
    Text(
        text = label,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(bottom = 8.dp)
    )


    var textValue by remember { mutableStateOf(if (value == 0.0f) "" else value.toString()) }

    OutlinedTextField(
        value = textValue,
        onValueChange = { input ->
            if (input.isEmpty() || input.matches(Regex("^[0-9]*\\.?[0-9]*$"))) {
                textValue = input
                val floatValue = input.toFloatOrNull() ?: 0.0f
                onValueChange(floatValue)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .height(70.dp)
            .border(
                BorderStroke(1.dp, Color.Black),
                RoundedCornerShape(10.dp)
            ),
        singleLine = true,
        placeholder = {
            Text(
                text = "Por ejemplo: 100.15",
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray)
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            cursorColor = Color.Black
        ),
        textStyle = LocalTextStyle.current.copy(color = Color.Black, fontSize = 30.sp),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        leadingIcon = {
            Text(
                text = "$",
                style = MaterialTheme.typography.labelLarge,
                color = Color.Black
            )
        }
    )
}




@Composable
fun ThickTextFieldWithLabel(value: String, onValueChange: (String) -> Unit, isPassword: Boolean = false) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(50.dp))
            .height(90.dp)
            .border(
                BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                RoundedCornerShape(50.dp)
            ),
        singleLine = true,
        textStyle = LocalTextStyle.current.copy(color = Color.Black),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    )
}
