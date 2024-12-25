package com.example.jumatexpress

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.*
import com.example.jumatexpress.ui.theme.JumatexpressTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.MaterialTheme
import com.example.jumatexpress.UiState.Success
import com.example.jumatexpress.screen.list.LogbookScreen
import com.example.jumatexpress.ui.components.BottomNav
import com.example.jumatexpress.ui.screens.seminar.SeminarScreen
import com.example.jumatexpress.ui.screens.submission.SubmissionDetailScreen
import com.example.jumatexpress.ui.screens.submission.SubmissionHistoryScreen
import com.example.jumatexpress.ui.screens.submission.UploadResponseScreen
import com.example.jumatexpress.ui.theme.MyApplicationTheme


sealed class UiState {
    object Loading : UiState()
    object Successfull : UiState()
    data class Success(val data: Any) : UiState() // Menggunakan tipe data 'Any' atau tipe lain yang spesifik
    data class Error(val message: String) : UiState()
}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JumatexpressTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier) {
    val navController = rememberNavController()
    var currentRoute by remember { mutableStateOf("home") }
    var currentScreen by remember { mutableStateOf("main") }

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            val viewModel: MainViewModel = viewModel()
            LoginScreen(navController = navController, viewModel = viewModel)
        }
        composable("register") {
            val viewModel: MainViewModel = viewModel()
            RegisterScreen(navController = navController, viewModel = viewModel)
        }
        composable("authenticated") {
            Scaffold(
                bottomBar = {
                    BottomNav(
                        currentRoute = currentRoute,
                        onNavigate = { route ->
                            currentRoute = route
                        }
                    )
                }
            ) { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    when (currentRoute) {
                        "pengajuan" -> {
                            when (currentScreen) {
                                "main" -> {
                                    SubmissionHistoryScreen(
                                        onDetailClick = {
                                            currentScreen = "detail"
                                        }
                                    )
                                }
                                "detail" -> {
                                    SubmissionDetailScreen(
                                        onBackClick = {
                                            currentScreen = "main"
                                        },
                                        onUploadClick = {
                                            currentScreen = "upload"
                                        }
                                    )
                                }
                                "upload" -> {
                                    UploadResponseScreen(
                                        onBackClick = {
                                            currentScreen = "detail"
                                        },
                                        onUpload = { uri ->
                                            // Handle file upload
                                        }
                                    )
                                }
                            }
                        }
                        "home" -> {
                            // Implement Home Screen
                        }
                        "laporan" -> {
                            // Implement Laporan Screen
                        }
                        "seminar" -> {
                            SeminarScreen(
                                onBackClick = {
                                    // Handle back navigation if needed
                                }
                            )
                        }
                        "profil" -> {
                            // Implement Profile Screen
                        }
                        "logbook" -> {
                            val viewModel: MainViewModel = viewModel()
                            LogbookScreen(
                                navController = navController,
                                mainViewModel = viewModel,
                                onItemClick = { entryId ->
                                    navController.navigate(Screen.LogbookDetail.createRoute(entryId.toString()))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, viewModel: MainViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Observasi status UI
    val uiState by viewModel.uiState.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header Section
        Text(
            text = "Welcome Back!",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "Please login to continue",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Email Input
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )

        // Password Input
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )

        // Login Button
        Button(
            onClick = {
                // Panggil fungsi login dari ViewModel
                viewModel.login(email, password)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
            enabled = email.isNotEmpty() && password.isNotEmpty()
        ) {
            Text("Login", color = MaterialTheme.colorScheme.onPrimary)
        }

        // Register Redirect
        TextButton(onClick = { navController.navigate("register") }) {
            Text(
                "Don't have an account? Register here",
                color = MaterialTheme.colorScheme.primary
            )
        }

        // Menampilkan pesan error jika login gagal
        if (uiState is UiState.Error) {
            val errorMessage = (uiState as UiState.Error).message
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }

//     Tangani UI State perubahan
    LaunchedEffect(uiState) {
        if (uiState is UiState.Successfull) {
            navController.navigate("authenticated") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

}


// Ganti function viewModel() dengan companion object ini
@Composable
fun viewModel(): MainViewModel {
    val factory = remember {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return MainViewModel() as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    return androidx.lifecycle.viewmodel.compose.viewModel(factory = factory)
}

@Composable
fun AddUserForm(onSubmit: (User) -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            singleLine = true
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            singleLine = true
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )

        Button(
            onClick = {
                if (name.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
                    // Tambahkan id saat membuat objek User
                    onSubmit(User(
                        id = "", // Bisa diisi dengan UUID atau string kosong
                        name = name,
                        email = email,
                        password = password
                    ))
                    // Clear form after submit
                    name = ""
                    email = ""
                    password = ""
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = name.isNotBlank() && email.isNotBlank() && password.isNotBlank()
        ) {
            Text("Submit")
        }
    }
}

@Composable
fun UserList(users: List<User>) {
    LazyColumn {
        items(users) { user ->
            Text(text = "${user.name} - ${user.email}")
        }
    }
}

class MainViewModel : ViewModel() {
    private val api = ApiClient.instance.create(ApiService::class.java)

    // UI state for general users and logbooks
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    // UI state for logbooks specifically
    private val _logbooks = MutableStateFlow<List<Logbook>>(emptyList())
    val logbooks: StateFlow<List<Logbook>> = _logbooks

    init {
        loadUsers()
    }

    // Load users
    fun loadUsers() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val response = api.getUsers()
                if (response.isSuccessful && response.body() != null) {
                    _uiState.value = Success(response.body()!!)
                } else {
                    _uiState.value = UiState.Error("Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Error: ${e.message}")
            }
        }
    }

    // Add user
    fun addUser(user: User) {
        viewModelScope.launch {
            try {
                val response = api.addUser(user)
                if (response.isSuccessful) {
                    loadUsers() // Refresh data after successful addition
                } else {
                    _uiState.value = UiState.Error("Error adding user: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Error adding user: ${e.message}")
            }
        }
    }

    // Login
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val response = ApiClient.instance.create(ApiService::class.java)
                    .loginUser(LoginRequest(email, password))

                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        // Save token to SharedPreferences
                        saveToken(loginResponse.token)

                        // Update UI status to Success
                        _uiState.value = UiState.Successfull
                    } else {
                        _uiState.value = UiState.Error("Login failed: Empty response body")
                    }
                } else {
                    _uiState.value = UiState.Error("Login failed: ${response.message()}")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Error: ${e.localizedMessage}")
            }
        }
    }

    private fun saveToken(token: String) {
    // Simpan token JWT ke SharedPreferences atau secure storage
    }

    fun saveToken(context: Context, token: String) {
    val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    sharedPreferences.edit().putString("jwt_token", token).apply()
    }

    // Fetch logbooks by student ID
    fun logbooks(id_Mahasiswa: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val response = api.getLogbooks(id_Mahasiswa)

                if (response.isSuccessful) {
                    response.body()?.let {
                        _logbooks.value = it
                        _uiState.value = UiState.Successfull
                    }
                } else {
                    _uiState.value = UiState.Error("Failed: ${response.message()}")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Error: ${e.localizedMessage}")
            }
        }
    }
}


//KEDUA KODINGAN INI DALAM BLOK MAINVIEWMODEL TADI YA
//private fun saveToken(token: String) {
//    // Simpan token JWT ke SharedPreferences atau secure storage
//}
//
//fun saveToken(context: Context, token: String) {
//    val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
//    sharedPreferences.edit().putString("jwt_token", token).apply()
//}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    JumatexpressTheme {
        // Gunakan Modifier.padding untuk mendefinisikan padding yang diinginkan
        MainScreen(modifier = Modifier.padding(16.dp))
    }
}


@Composable
fun LogbookScreenWrapper(
    navController: NavController,
    mainViewModel: MainViewModel,
    onItemClick: (Int) -> Unit
) {
    val idMahasiswa = 1 // Ambil ID mahasiswa dari shared preferences atau argument lainnya

    LaunchedEffect(Unit) {
        mainViewModel.logbooks(idMahasiswa)
    }

    LogbookScreen(
        mainViewModel,
        navController = navController,  // Meneruskan navController ke LogbookScreen
        onItemClick = onItemClick
    )
}

