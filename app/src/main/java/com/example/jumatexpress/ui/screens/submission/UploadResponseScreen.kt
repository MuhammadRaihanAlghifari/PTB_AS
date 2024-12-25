package com.example.jumatexpress.ui.screens.submission

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadResponseScreen(
    onBackClick: () -> Unit,
    onUpload: (Uri?) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedFile by remember { mutableStateOf<Uri?>(null) }
    var status by remember { mutableStateOf("Ditolak") }
    var responseText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Surat Balasan Instansi",
                        fontSize = 18.sp
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
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF5F5F5)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // File Upload Section
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add, // Changed to Upload icon
                            contentDescription = "PDF",
                            modifier = Modifier.size(40.dp)
                        )
                        Button(
                            onClick = { /* Implement file picker */ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFF9800)
                            )
                        ) {
                            Text("Unggah", color = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Status Section
                    Text(
                        text = "Status :",
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            StatusButton(
                                text = "Ditolak",
                                isSelected = status == "Ditolak",
                                onClick = { status = "Ditolak" }
                            )
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            StatusButton(
                                text = "Diterima",
                                isSelected = status == "Diterima",
                                onClick = { status = "Diterima" }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Response Text Section
                    Text(
                        text = "Isi surat balasan :",
                        fontWeight = FontWeight.Bold
                    )
                    OutlinedTextField(
                        value = responseText,
                        onValueChange = { responseText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Submit Button
                    Button(
                        onClick = { /* Implement submission logic */ },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF9800)
                        )
                    ) {
                        Text("Kirim", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
private fun StatusButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0xFFFF9800) else Color.LightGray
        ),
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text, color = if (isSelected) Color.White else Color.Black)
    }
}