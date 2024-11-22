package hci.mobile.poty.screens.payment.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hci.mobile.poty.ui.theme.PotyTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.viewmodel.compose.viewModel
import hci.mobile.poty.MyApplication
import hci.mobile.poty.R
import hci.mobile.poty.screens.payment.PaymentHistory
import hci.mobile.poty.screens.payment.PaymentScreenState
import hci.mobile.poty.screens.payment.PaymentScreenViewModel
import hci.mobile.poty.screens.register.RegistrationViewModel
import hci.mobile.poty.ui.components.BackButton
import hci.mobile.poty.ui.components.ResponsiveNavBar
import hci.mobile.poty.ui.theme.GreenDark
import hci.mobile.poty.ui.theme.GreyDark
import hci.mobile.poty.ui.theme.White
import hci.mobile.poty.ui.theme.labelLargeLite
import hci.mobile.poty.utils.WindowSizeClass
import hci.mobile.poty.utils.calculateWindowSizeClass

//
//
//@Composable
//fun PaymentScreen(
//    viewModel: PaymentScreenViewModel = viewModel(factory = PaymentScreenViewModel.provideFactory(
//        LocalContext.current.applicationContext as MyApplication
//    )),
//    mockWindowSizeClass: WindowSizeClass? = null,
//    onNavigateToDashboard: () -> Unit = {},
//    onNavigateToEmail: () -> Unit = {},
//    onNavigateToLink: () -> Unit = {}
//) {
//    val state by viewModel.state.collectAsState()
//    val windowSizeClass = mockWindowSizeClass ?: calculateWindowSizeClass()
//
//    val contentPadding = when (windowSizeClass) {
//        WindowSizeClass.MediumTablet, WindowSizeClass.MediumTabletLandscape -> 32.dp
//        else -> 16.dp
//    }
//
//    val isLandscape = windowSizeClass == WindowSizeClass.MediumPhoneLandscape ||
//            windowSizeClass == WindowSizeClass.MediumTabletLandscape
//
//    PotyTheme(darkTheme = true, dynamicColor = false) {
//        ResponsiveNavBar(
//            onNavigate = { /* Handle navigation */ },
//            mockWindowSizeClass = mockWindowSizeClass
//        ) {
//            Scaffold(
//                modifier = Modifier.fillMaxSize(),
//                containerColor = MaterialTheme.colorScheme.secondary,
//            ) { innerPadding ->
//                if (isLandscape) {
//                    Row(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(innerPadding),
//                    ) {
//                        HeaderSection(
//                            modifier = Modifier
//                                .fillMaxHeight()
//                                .weight(1f),
//                            contentPadding = contentPadding,
//                            state = state,
//                            windowSizeClass = windowSizeClass
//                        )
//                        ContentSection(
//                            modifier = Modifier
//                                .fillMaxHeight()
//                                .weight(2f),
//                            contentPadding = contentPadding,
//                            state = state,
//                            viewModel = viewModel,
//                            windowSizeClass = windowSizeClass,
//                            onNavigateToLink = onNavigateToLink,
//                            onNavigateToEmail = onNavigateToEmail
//                        )
//                    }
//                } else {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(innerPadding),
//                        verticalArrangement = Arrangement.Top
//                    ) {
//                        HeaderSection(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(200.dp), // Adjusted for portrait mode
//                            contentPadding = contentPadding,
//                            state = state,
//                            windowSizeClass = windowSizeClass
//                        )
//                        Spacer(modifier = Modifier.height(16.dp))
//                        ContentSection(
//                            modifier = Modifier.fillMaxWidth(),
//                            contentPadding = contentPadding,
//                            state = state,
//                            viewModel = viewModel,
//                            windowSizeClass = windowSizeClass,
//                            onNavigateToLink = onNavigateToLink,
//                            onNavigateToEmail = onNavigateToEmail
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun HeaderSection(
//    modifier: Modifier,
//    contentPadding: Dp,
//    state: PaymentScreenState,
//    isPhonePortrait: Boolean = false,
//    windowSizeClass: WindowSizeClass = WindowSizeClass.MediumPhone
//) {
//    Box(
//        modifier = modifier
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.background_scaffolding),
//            contentDescription = null,
//            modifier = Modifier.fillMaxSize(),
//            contentScale = ContentScale.Crop
//        )
//
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Top
//        ) {
//           BackButton()
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(contentPadding),
//                colors = CardDefaults.cardColors(
//                    containerColor = Color.Transparent
//                ),
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.Center,
//                ) {
//                    Text(
//                        text = stringResource(R.string.pay_money),
//                        style = MaterialTheme.typography.titleMedium,
//                        color = White
//                    )
//                }
//            }
//        }
//    }
//}
///*
//@Composable
//fun ContentSection(
//    modifier: Modifier,
//    contentPadding: Dp,
//    state: PaymentScreenState,
//    viewModel: PaymentScreenViewModel,
//    windowSizeClass: WindowSizeClass,
//    onNavigateToLink: () -> Unit = {},
//    onNavigateToEmail: () -> Unit = {},
//    topStart: Dp = 0.dp,
//    topEnd: Dp = 0.dp,
//    bottomStart: Dp = 0.dp,
//    bottomEnd: Dp = 0.dp
//) {
//    Card(
//        modifier = modifier.fillMaxWidth(),
//        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.onBackground,
//        ),
//        shape = RoundedCornerShape(
//            topStart = 30.dp,
//            topEnd = 30.dp,
//            bottomEnd = 0.dp,
//            bottomStart = 0.dp
//        ),
//        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(15.dp)
//        ) {
//            Spacer(modifier = Modifier.height(
//                when (windowSizeClass) {
//                    WindowSizeClass.MediumPhoneLandscape -> 0.dp
//                    else -> 16.dp
//                }
//            ))
//
//            Button(
//                onClick = { onNavigateToLink() },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(50.dp),
//                shape = RoundedCornerShape(15.dp)
//            ) {
//                Row(
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Text(
//                        text = stringResource(R.string.pay_using),
//                        style = MaterialTheme.typography.labelLargeLite,
//                        color = MaterialTheme.colorScheme.onBackground
//                    )
//                    Text(
//                        text = stringResource(R.string.payment_link),
//                        style = MaterialTheme.typography.labelLarge,
//                        color = MaterialTheme.colorScheme.onBackground
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Button(
//                onClick = { onNavigateToEmail() },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(50.dp),
//                shape = RoundedCornerShape(15.dp)
//            ) {
//                Row(
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Text(
//                        text = stringResource(R.string.pay_using),
//                        style = MaterialTheme.typography.labelLargeLite,
//                        color = MaterialTheme.colorScheme.onBackground
//                    )
//                    Text(
//                        text = stringResource(R.string.email),
//                        style = MaterialTheme.typography.labelLarge,
//                        color = MaterialTheme.colorScheme.onBackground
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.height(
//                when (windowSizeClass) {
//                    WindowSizeClass.MediumTablet, WindowSizeClass.MediumTabletLandscape -> 60.dp
//                    WindowSizeClass.MediumPhoneLandscape -> 5.dp
//                    else -> 40.dp
//                }
//            ))
//
//            Text(
//                text = stringResource(R.string.payments_history),
//                color = GreyDark
//            )
//
//            Spacer(modifier = Modifier.height(
//                when (windowSizeClass) {
//                    WindowSizeClass.MediumPhoneLandscape -> 5.dp
//                    else -> 16.dp
//                }
//            ))
//            PaymentHistory(state.paymentHistory)
//        }
//    }
//}*/
//
//@Composable
//fun ContentSection(
//    modifier: Modifier,
//    contentPadding: Dp,
//    state: PaymentScreenState,
//    viewModel: PaymentScreenViewModel,
//    windowSizeClass: WindowSizeClass,
//    onNavigateToLink: () -> Unit = {},
//    onNavigateToEmail: () -> Unit = {}
//) {
//    val isLandscape = windowSizeClass == WindowSizeClass.MediumPhoneLandscape ||
//            windowSizeClass == WindowSizeClass.MediumTabletLandscape
//
//    Card(
//        modifier = modifier.fillMaxWidth(),
//        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.onBackground,
//        ),
//        shape = RoundedCornerShape(
//            topStart = if (isLandscape) 30.dp else 0.dp,
//            topEnd = if (isLandscape) 0.dp else 30.dp,
//            bottomStart = if (isLandscape) 30.dp else 0.dp,
//            bottomEnd = 0.dp
//        ),
//        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(15.dp)
//        ) {
//            Spacer(modifier = Modifier.height(
//                when (windowSizeClass) {
//                    WindowSizeClass.MediumPhoneLandscape -> 0.dp
//                    else -> 16.dp
//                }
//            ))
//
//            Button(
//                onClick = { onNavigateToLink() },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(50.dp),
//                shape = RoundedCornerShape(15.dp)
//            ) {
//                Row(
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Text(
//                        text = "Enviar por ",
//                        style = MaterialTheme.typography.labelLargeLite,
//                        color = MaterialTheme.colorScheme.onBackground
//                    )
//                    Text(
//                        text = "Link de Pago",
//                        style = MaterialTheme.typography.labelLarge,
//                        color = MaterialTheme.colorScheme.onBackground
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Button(
//                onClick = { onNavigateToEmail() },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(50.dp),
//                shape = RoundedCornerShape(15.dp)
//            ) {
//                Row(
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Text(
//                        text = "Enviar por ",
//                        style = MaterialTheme.typography.labelLargeLite,
//                        color = MaterialTheme.colorScheme.onBackground
//                    )
//                    Text(
//                        text = "Correo Electronico",
//                        style = MaterialTheme.typography.labelLarge,
//                        color = MaterialTheme.colorScheme.onBackground
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.height(
//                when (windowSizeClass) {
//                    WindowSizeClass.MediumTablet, WindowSizeClass.MediumTabletLandscape -> 60.dp
//                    WindowSizeClass.MediumPhoneLandscape -> 5.dp
//                    else -> 40.dp
//                }
//            ))
//
//            Text(
//                text = "Envios Recientes",
//                color = GreyDark
//            )
//
//            Spacer(modifier = Modifier.height(
//                when (windowSizeClass) {
//                    WindowSizeClass.MediumPhoneLandscape -> 5.dp
//                    else -> 16.dp
//                }
//            ))
//            PaymentHistory(state.paymentHistory)
//        }
//    }
//}
//
//

@Composable
fun PaymentScreen(
    viewModel: PaymentScreenViewModel = viewModel(factory = PaymentScreenViewModel.provideFactory(
        LocalContext.current.applicationContext as MyApplication
    )),
    mockWindowSizeClass: WindowSizeClass? = null,
    onNavigateToDashboard: () -> Unit = {},
    onNavigateToEmail: () -> Unit = {},
    onNavigateToLink: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val windowSizeClass = mockWindowSizeClass ?: calculateWindowSizeClass()

    val contentPadding = when (windowSizeClass) {
        WindowSizeClass.MediumTablet, WindowSizeClass.MediumTabletLandscape -> 32.dp
        else -> 16.dp
    }

    val isLandscape = windowSizeClass == WindowSizeClass.MediumPhoneLandscape ||
            windowSizeClass == WindowSizeClass.MediumTabletLandscape

    PotyTheme(darkTheme = true, dynamicColor = false) {
        ResponsiveNavBar(
            onNavigate = { /* Handle navigation */ },
            mockWindowSizeClass = mockWindowSizeClass
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                containerColor = MaterialTheme.colorScheme.secondary,
            ) { innerPadding ->
                if (isLandscape) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                    ) {
                        HeaderSection(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f),
                            contentPadding = contentPadding,
                            state = state,
                            windowSizeClass = windowSizeClass
                        )
                        ContentSection(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(2f),
                            contentPadding = contentPadding,
                            state = state,
                            viewModel = viewModel,
                            windowSizeClass = windowSizeClass,
                            onNavigateToLink = onNavigateToLink,
                            onNavigateToEmail = onNavigateToEmail
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.Top
                    ) {
                        HeaderSection(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(if (windowSizeClass == WindowSizeClass.MediumPhone) 180.dp else 200.dp),
                            contentPadding = contentPadding,
                            state = state,
                            windowSizeClass = windowSizeClass
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        ContentSection(
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = contentPadding,
                            state = state,
                            viewModel = viewModel,
                            windowSizeClass = windowSizeClass,
                            onNavigateToLink = onNavigateToLink,
                            onNavigateToEmail = onNavigateToEmail
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HeaderSection(
    modifier: Modifier,
    contentPadding: Dp,
    state: PaymentScreenState,
    windowSizeClass: WindowSizeClass
) {
    Box(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_scaffolding),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            verticalArrangement = Arrangement.Top
        ) {
            BackButton()
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = stringResource(R.string.pay_money),
                        style = MaterialTheme.typography.titleMedium,
                        color = White
                    )
                }
            }
        }
    }
}

@Composable
fun ContentSection(
    modifier: Modifier,
    contentPadding: Dp,
    state: PaymentScreenState,
    viewModel: PaymentScreenViewModel,
    windowSizeClass: WindowSizeClass,
    onNavigateToLink: () -> Unit,
    onNavigateToEmail: () -> Unit
) {
    val isLandscape = windowSizeClass == WindowSizeClass.MediumPhoneLandscape ||
            windowSizeClass == WindowSizeClass.MediumTabletLandscape

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onBackground),
        shape = RoundedCornerShape(
            topStart = if (isLandscape) 30.dp else 0.dp,
            topEnd = if (isLandscape) 0.dp else 30.dp,
            bottomStart = if (isLandscape) 30.dp else 0.dp,
            bottomEnd = 0.dp
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            Spacer(modifier = Modifier.height(if (isLandscape) 5.dp else 16.dp))

            PaymentOptionButton(
                label = stringResource(R.string.payment_link),
                description = stringResource(R.string.pay_using),
                onClick = onNavigateToLink
            )

            Spacer(modifier = Modifier.height(16.dp))

            PaymentOptionButton(
                label = stringResource(R.string.email),
                description = stringResource(R.string.pay_using),
                onClick = onNavigateToEmail
            )

            Spacer(modifier = Modifier.height(
                if (isLandscape) 10.dp else 40.dp
            ))

            Text(
                text = stringResource(R.string.payments_history),
                color = GreyDark,
                style = MaterialTheme.typography.labelLargeLite
            )

            Spacer(modifier = Modifier.height(16.dp))

            PaymentHistory(state.paymentHistory)
        }
    }
}

@Composable
fun PaymentOptionButton(
    label: String,
    description: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = description,
                style = MaterialTheme.typography.labelLargeLite,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

