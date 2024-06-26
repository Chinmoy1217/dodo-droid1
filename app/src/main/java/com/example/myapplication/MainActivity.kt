package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.nav.NavGraph
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.util.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val sharedViewModel: SharedViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun BottomSheetContent(
    onHideClick: () -> Unit,
    onNavigateToMain: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "This is a bottom sheet",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            onHideClick()
            onNavigateToMain()
        }) {
            Text("Hide Sheet")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    var isDarkMode by remember { mutableStateOf(false) }
    var isLoggedIn by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    val scaffoldState = rememberBottomSheetScaffoldState()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    MyApplicationTheme(darkTheme = isDarkMode) {
        if (!isLoggedIn) {
            NavHost(
                navController = navController,
                startDestination = "login"
            ) {
                composable("login") {
                    LoginScreen(
                        onLoginSuccess = { isLoggedIn = true },
                        onForgotPassword = { navController.navigate("forgotPassword") },
                        onRegister = { navController.navigate("register") },
                        onPhoneSignIn = { navController.navigate("phoneSignIn") }  // Corrected the missing comma
                    )
                }
                composable("forgotPassword") {
                    ForgotPasswordScreen(onBack = { navController.popBackStack() })
                }
                composable("register") {
                    RegisterScreen(
                        onRegisterSuccess = { navController.navigate("login") },
                        onBack = { navController.popBackStack() })
                }
                composable("phoneSignIn") {  // Added this composable
                    PhoneSignInScreen(
                        onPhoneSignInSuccess = { navController.navigate("main") },
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        } else {
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet(
                        modifier = Modifier.width(280.dp)
                    ) {
                        DrawerHeader()
                        DrawerContent(navController, drawerState, scope)
                    }
                }
            ) {
                BottomSheetScaffold(
                    scaffoldState = scaffoldState,
                    sheetContent = {
                        BottomSheetContent(
                            onHideClick = {
                                scope.launch {
                                    try {
                                        scaffoldState.bottomSheetState.hide()
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                }
                            },
                            onNavigateToMain = {
                                navController.navigate("main") {
                                    popUpTo(navController.graph.startDestinationId) {
                                        inclusive = true
                                    }
                                }
                            }
                        )
                    },
                    sheetPeekHeight = 0.dp
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text(text = "Do-Droid") },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        scope.launch { drawerState.open() }
                                    }) {
                                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                                    }
                                },
                                actions = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text("Dark Mode")
                                        Switch(
                                            checked = isDarkMode,
                                            onCheckedChange = { isDarkMode = it }
                                        )
                                    }
                                }
                            )
                        },
                        floatingActionButton = {
                            FloatingActionButton(onClick = { showDialog = true }) {
                                Text("+")
                            }
                        }
                    ) { paddingValues ->
                        Surface(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            NavHost(
                                navController = navController,
                                startDestination = "main"
                            ) {
                                composable("main") {
                                    MainContent(
                                        name = "friend",
                                        onShowBottomSheetClick = { scope.launch { scaffoldState.bottomSheetState.expand() } },
                                        showDialog = showDialog,
                                        onDismissDialog = { showDialog = false }
                                    )
                                }
                                composable("home") { HomeScreen() }
                                composable("settings") { SettingsScreen() }
                                composable("about") { AboutScreen() }
                                composable("help") { HelpScreen() }
                                composable("products") { ProductListScreen() }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun DrawerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "App Logo",
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Do-Droid",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
fun DrawerContent(
    navController: NavHostController,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    Column(modifier = Modifier.fillMaxHeight()) {
        DrawerMenuItem(
            text = "Home",
            icon = Icons.Default.Home,
            onClick = {
                scope.launch {
                    drawerState.close()
                    navController.navigate("home")
                }
            }
        )
        DrawerMenuItem(
            text = "Settings",
            icon = Icons.Default.Settings,
            onClick = {
                scope.launch {
                    drawerState.close()
                    navController.navigate("settings")
                }
            }
        )
        DrawerMenuItem(
            text = "About",
            icon = Icons.Default.Info,
            onClick = {
                scope.launch {
                    drawerState.close()
                    navController.navigate("about")
                }
            }
        )
        DrawerMenuItem(
            text = "Help",
            icon = Icons.Default.Star,
            onClick = {
                scope.launch {
                    drawerState.close()
                    navController.navigate("help")
                }
            }
        )
        DrawerMenuItem(
            text = "Products",
            icon = Icons.Default.ShoppingCart,
            onClick = {
                scope.launch {
                    drawerState.close()
                    navController.navigate("products")
                }
            }
        )
        Spacer(modifier = Modifier.weight(1f))
        DrawerMenuItem(
            text = "Back to Main Menu",
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            onClick = {
                scope.launch {
                    drawerState.close()
                    navController.navigate("main") {
                        // Pop up to the start destination of the navigation graph
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun DrawerMenuItem(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = text)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text)
    }
}

@Composable
fun ProductListScreen() {
    ProductList()
}

@Composable
fun MainContent(
    name: String,
    onShowBottomSheetClick: () -> Unit,
    showDialog: Boolean,
    onDismissDialog: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Greeting(name = name)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onShowBottomSheetClick) {
            Text("Show Bottom Sheet")
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = onDismissDialog,
                title = { Text("Dialog Title", color = MaterialTheme.colorScheme.onSurface) },
                text = {
                    Text(
                        "This is a dialog message.",
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                confirmButton = {
                    Button(onClick = onDismissDialog) {
                        Text("OK")
                    }
                }
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Welcome to the Home Screen!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun SettingsScreen() {
    NavGraph(navController = rememberNavController(), sharedViewModel = SharedViewModel())
}

@Composable
fun AboutScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "About Screen",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun HelpScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Help Screen",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        MyApp()
    }
}