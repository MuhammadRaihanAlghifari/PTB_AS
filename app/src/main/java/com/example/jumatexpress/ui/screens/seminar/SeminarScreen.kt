package com.example.jumatexpress.ui.screens.seminar

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jumatexpress.R
import java.time.LocalDate
import java.time.LocalTime
import androidx.compose.material.icons.filled.KeyboardArrowLeft

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeminarScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    var tanggal by remember { mutableStateOf("") }
    var waktu by remember { mutableStateOf("") }
    var peserta1 by remember { mutableStateOf("") }
    var peserta2 by remember { mutableStateOf("") }
    var peserta3 by remember { mutableStateOf("") }
    var peserta4 by remember { mutableStateOf("") }
    var peserta5 by remember { mutableStateOf("") }
    var peserta6 by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Seminar KP",
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            // Tanggal Field
            OutlinedTextField(
                value = tanggal,
                onValueChange = { tanggal = it },
                label = { Text("Tanggal") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Waktu Field
            OutlinedTextField(
                value = waktu,
                onValueChange = { waktu = it },
                label = { Text("Jam dan menit") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Peserta Fields
            OutlinedTextField(
                value = peserta1,
                onValueChange = { peserta1 = it },
                label = { Text("Peserta 1") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = peserta2,
                onValueChange = { peserta2 = it },
                label = { Text("Peserta 2") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = peserta3,
                onValueChange = { peserta3 = it },
                label = { Text("Peserta 3") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = peserta4,
                onValueChange = { peserta4 = it },
                label = { Text("Peserta 4") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = peserta5,
                onValueChange = { peserta5 = it },
                label = { Text("Peserta 5") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )


            Spacer(modifier = Modifier.weight(1f))

            // Submit Button
            Button(
                onClick = { /* Implement submission logic */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Ajukan")
            }
        }
    }
}