package com.example.jumatexpress.ui.screens.submission

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jumatexpress.data.model.KpSubmission
import com.example.jumatexpress.ui.components.KpSubmissionItem

@Composable
fun SubmissionHistoryScreen(
    modifier: Modifier = Modifier,
    onDetailClick: (KpSubmission) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = "Riwayat Persetujuan Pengajuan KP",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        val sampleSubmissions = listOf(
            KpSubmission(
                status = "Diterima",
                date = "2024-12-11",
                companyName = "BPS Padang",
                teamMembers = "Agif"
            )
        )

        LazyColumn {
            items(sampleSubmissions) { submission ->
                KpSubmissionItem(
                    submission = submission,
                    onDetailClick = { onDetailClick(submission) }
                )
            }
        }
    }
}