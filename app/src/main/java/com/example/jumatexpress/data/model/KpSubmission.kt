package com.example.jumatexpress.data.model

data class KpSubmission(
    val status: String,
    val date: String,
    val companyName: String,
    val teamMembers: String,
    val teamLead: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val proposalFileName: String = "Proposal pengajuan KP",
    val responseFileName: String = "Surat Balasan Instansi",
    val notes: String = ""
)